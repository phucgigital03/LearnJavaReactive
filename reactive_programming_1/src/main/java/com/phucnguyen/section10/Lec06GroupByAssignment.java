package com.phucnguyen.section10;

import com.phucnguyen.common.Util;
import com.phucnguyen.section10.assignment.groupby.OrderProcessingService;
import com.phucnguyen.section10.assignment.groupby.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec06GroupByAssignment {
    private static final Logger log = LoggerFactory.getLogger(Lec06GroupByAssignment.class);

    public static void main(String[] args) {
        log.info("start Lec06GroupByAssignment");
        // Automotive, Kids
        eventStream()
                .filter(OrderProcessingService.canProcess())
                .groupBy(i -> i.category())
                .flatMap(groupFlux ->
                    groupFlux.transform(OrderProcessingService.getProcessor(groupFlux.key()))
                )
                .subscribe(Util.subscriber());

        Util.sleepSecondDuration(60);
    }

    private static Flux<PurchaseOrder> eventStream() {
        return Flux.interval(Duration.ofMillis(100))
                .map(i -> PurchaseOrder.create());
    }
}

//Tóm tắt luồng dữ liệu (Data Flow)
//1. groupBy: Chia dòng sông lớn thành nhiều dòng sông nhỏ. Mỗi dòng sông nhỏ này gọi là một GroupedFlux.
//2. flatMap: Nhận từng dòng sông nhỏ đó (biến tên là groupFlux).
//3. transform: Cần một công cụ để "xào nấu" dòng sông này.
//4. getProcessor: Trả về công cụ xào nấu (UnaryOperator). Công cụ này bảo: "Đưa cho tao một cái Flux, tao xào cho".
//5. Kết quả: Bạn đưa groupFlux cho nó. Vì groupFlux cũng là Flux, nên nó nhận và xử lý bình thường (cộng thêm 100 vào giá).

//***Note:
//eventStream()
//                .filter(OrderProcessingService.canProcess())
// Các function có lamda(tự động tạo đối tượng với implement interface), bản chất là nhận vào một đối tượng có function để callback
