package com.phucnguyen.section11;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/*
    repeat operator simply resubscribes when it sees complete signal.
    it does not like error signal.
 */
public class Lec01Repeat {
    private static final Logger log = LoggerFactory.getLogger(Lec01Repeat.class);

    public static void main(String[] args) {

//        stringMono.subscribe(Util.subscriber());
//        stringMono.subscribe(Util.subscriber());

//        This way to call API sequence one by one
//        ***For some case:
//        But if the API is not designed that way (type of Flux), what will you do?
//        So but doing this repeat we can keep on requesting or we can keep on calling the remote API again and again.
//            stringMono
//                .repeat(3)
//                .subscribe(Util.subscriber());

//      This way to call API like three API concurrency with non-blocking
//        for (int i = 0; i < 3; i++) {
//            stringMono
//                    .subscribe(Util.subscriber());
//        }



//        demo1();
//        demo2();
//        demo3();
        demo4();
//        demo5();

        Util.sleepSecondDuration(10);
    }

    private static void demo1(){
        getCountryName()
                .repeat(3)
                .subscribe(Util.subscriber());
    }

    private static void demo2(){
        getCountryName()
                .repeat()
                .takeUntil(c -> c.equalsIgnoreCase("canada"))
                .subscribe(Util.subscriber());
    }

    private static void demo3(){
        var atomicInteger = new AtomicInteger(0);
        getCountryName()
                .repeat(() -> {
                    var counter = atomicInteger.incrementAndGet();
                    log.info("do with {}", counter);
                    return counter < 3;
                })
                .subscribe(Util.subscriber());
    }

    private static void demo4(){
        getCountryName()
                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2)).take(2))
                .subscribe(Util.subscriber());
    }

    private static void demo5() {
        Flux.just(1, 2, 3)
                .repeat(3)
                .subscribe(Util.subscriber());
    }


    private static Mono<String> getCountryName() {
        return Mono.fromSupplier(() -> {
            log.info("processing full name");
            return Util.getFaker().country().name();
        }); //non-blocking IO
    }


}
