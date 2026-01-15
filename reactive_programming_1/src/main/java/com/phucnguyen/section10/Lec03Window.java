package com.phucnguyen.section10;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec03Window {
    private static final Logger log = LoggerFactory.getLogger(Lec03Window.class);

    public static void main(String[] args) {

//      demo 1
//        eventStream()
//                .window(5)
//                .flatMap(flux -> {
//                    log.info("inside flatMap");
//                    return flux;
//                })
//                .subscribe(Util.subscriber());

//      demo 2
//        eventStream()
//                .window(5)
//                .map(flux -> {
//                    log.info("inside map");
//                    return flux; // received SinkManyUnicast includes 5 items
//                })
//                .subscribe(Util.subscriber());

//        demo 3
        eventStream()
                .window(5)
                .flatMap(flux -> {
                    log.info("inside flapMap");
                    return processStream(flux);
                })
                .subscribe();

        Util.sleepSecondDuration(60);
    }

    private static Flux<String> eventStream(){
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "Event-" + (i + 1));
    }

    private static Mono<Void> processStream(Flux<String> flux){
        log.info("start processing stream");
        return flux.doOnNext(i -> System.out.print("+"))
                .doOnComplete(() -> System.out.println())
                .then()
                ;
    }

//    ***Bản chất của window(5)
//    Khi bạn dùng window(5), luồng dữ liệu thay đổi như sau:
//    Nguồn: Item 1, Item 2, Item 3...
//    Qua window: [Flux A], [Flux B], [Flux C]...
//    Flux A sẽ chứa Item 1, 2, 3, 4, 5.
//    Flux B sẽ chứa Item 6, 7, 8, 9, 10.
}
