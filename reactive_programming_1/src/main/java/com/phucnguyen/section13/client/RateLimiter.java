package com.phucnguyen.section13.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// just for demo - could be a bean in real life
public class RateLimiter {
    private static final Map<String, Integer> categoryAttempts =
            Collections.synchronizedMap(new HashMap<>());

    //    Chạy duy nhất 1 lần: Nó chỉ chạy đúng một lần duy nhất trong suốt vòng đời của chương trình.
//    Chạy trước tất cả: Nó chạy trước cả khi bạn kịp tạo đối tượng (new RateLimiter()) hay gọi hàm static nào khác.
    static {
        refresh();
    }

    /*
        Không, trong Java nếu bạn viết static mà không có public, private hay protected đứng trước,
        thì nó KHÔNG PHẢI LÀ PUBLIC.
        Nó thuộc về một quyền truy cập gọi là Package-Private (Mặc định).
     */
    static <T> Mono<T> limitCalls() {
        return Mono.deferContextual(ctx -> {
            var allowCall = ctx.<String>getOrEmpty("category")
                    .map(RateLimiter::canAllow)
                    .orElse(false);
            /*
            .orElse(false) hoạt động trong 2 trường hợp
            1. Trường hợp A: Context KHÔNG có "category" (Lỗi ở getOrEmpty)
                getOrEmpty trả về Optional.empty().
                .map thấy đầu vào rỗng -> Nó bỏ qua, không chạy hàm canAllow, và trả về tiếp Optional.empty().
                .orElse(false) thấy đầu vào rỗng -> Nó trả về giá trị mặc định là false.
                Ý nghĩa: "Không tìm thấy category nào cả, nên tôi coi như không cho phép (false)."
            2. Trường hợp B: Context CÓ "category", nhưng hàm canAllow trả về false
                getOrEmpty trả về Optional["standard"].
                .map chạy hàm canAllow("standard"). Giả sử hết vé, hàm này trả về false.
                Kết quả của map là một cái hộp chứa chữ false: Optional[false]. Lưu ý: Hộp này KHÔNG RỖNG, nó chứa giá trị false.
                .orElse(false) mở hộp ra. Thấy có giá trị (là false) -> Nó lấy giá trị đó dùng luôn. (Nó không dùng cái false trong ngoặc đơn của orElse).
             */
            return allowCall ? Mono.empty() : Mono.error(new RuntimeException("exceeded the given limit"));
        });
    }

    private static synchronized boolean canAllow(String category) {
        var attempts = categoryAttempts.getOrDefault(category, 0);
        if (attempts > 0) {
            categoryAttempts.put(category, attempts - 1);
            return true;
        }
        return false;
    }

    /*
    1. Không có startWith: [Start] --------(đợi 5s)-------- [Nạp vé] --------(đợi 5s)-------- [Nạp vé]
    (Trong khoảng chờ đầu tiên, khách hàng không có vé)
    2. Có startWith: [Start] -> [Nạp vé ngay] --------(đợi 5s)-------- [Nạp vé] --------(đợi 5s)-------- [Nạp vé]
    (Khách hàng có vé ngay giây đầu tiên)
     */
    private static void refresh() {
        Flux.interval(Duration.ofSeconds(5))
                .startWith(0L)
                .subscribe(i -> {
                    categoryAttempts.put("standard", 2);
                    categoryAttempts.put("prime", 3);
                });
    }

}
