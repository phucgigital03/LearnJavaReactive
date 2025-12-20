package com.phucnguyen.section4;

import com.phucnguyen.common.Util;
import com.phucnguyen.section4.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

//Việc sử dụng nhiều luồng để gọi fluxSink.next() trong demo2
// thực sự là một cách triển khai không tối ưu và có thể làm
// giảm hiệu suất (hoặc ít nhất là không cải thiện hiệu suất)
// so với một luồng duy nhất.Dưới đây là giải thích chi tiết:
// 1. Cơ chế Đồng bộ hóa của FluxSinkNhư đã giải thích trước đó, FluxSink trong
// Reactor được thiết kế để đảm bảo an toàn luồng (thread-safe). Điều này có
// nghĩa là khi nhiều luồng cùng lúc gọi fluxSink.next(),
// Reactor phải thực hiện cơ chế khóa (locking) hoặc đồng bộ hóa (synchronization) nội bộ.
// Hiệu ứng giảm hiệu suất: Cơ chế khóa này buộc các luồng phải chờ đợi (wait)
// để lần lượt được truy cập và phát hành dữ liệu.Mặc dù bạn có 10 luồng,
// nhưng về cơ bản, chúng đang hoạt động gần như tuần tự (sequentially) tại điểm fluxSink.next().
// Tức là, thay vì tận dụng được 10 luồng để làm việc song song,
// bạn đang tạo ra 10 luồng cùng tranh chấp một tài nguyên bị khóa.
// 2. Sự lãng phí của Đa luồng (Overhead)Trong tình huống này, việc tạo
// và quản lý 10 luồng còn mang lại thêm chi phí không cần thiết:
// Chi phí Chuyển đổi ngữ cảnh (Context Switching):
// Hệ điều hành phải dành thời gian để chuyển đổi giữa 10 luồng,
// ngay cả khi chúng chỉ chờ đợi lẫn nhau. Điều này tiêu tốn tài nguyên CPU
// và làm chậm tổng thể quá trình.Chi phí Khóa/Mở khóa (Locking/Unlocking):
// Chi phí thực hiện các thao tác đồng bộ hóa nội bộ của FluxSink là đáng kể hơn
// so với việc chỉ cần gọi next() từ một luồng duy nhất.

public class Lec03FluxSinkThreadSafety {
    private static final Logger log = LoggerFactory.getLogger(Lec03FluxSinkThreadSafety.class);

    public static void main(String[] args) {
        log.info("Lec03FluxSinkThreadSafety start");
        demo1();
    }

    private static void demo1(){
        var list = new ArrayList<>();

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                log.info("thread name: " + Thread.currentThread().getName() + " value: " + i);
                list.add(i);
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

        Util.sleepSecondDuration(3);
        log.info("list size: {}", list.size());
    }

    private static void demo2(){
        var list = new ArrayList<>();
        var generator = new NameGenerator();
        var flux = Flux.create(generator);
        flux.subscribe(name -> list.add(name));

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
//                log.info("thread name: " + Thread.currentThread().getName() + " value: " + i);
                generator.generateName();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable)
                    .start();
        }

        Util.sleepSecondDuration(3);
        log.info("list size: {}", list.size());

    }

}
