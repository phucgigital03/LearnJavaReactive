package com.phucnguyen.section7;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    We can have multiple subscribeOn.
    The closest to the source will take the precedence!
 */
public class Lec03MultipleSubscribeOn {
    private static final Logger log = LoggerFactory.getLogger(Lec03MultipleSubscribeOn.class);

    public static void main(String[] args) {
        log.info("Start Lec03MultipleSubscribeOn");

        var flux = Flux.create(sink -> {
                    for (int i = 1; i <= 2; i++) {
                        log.info("generating {}", i);
                        sink.next(i);
                    }
                    sink.complete();
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(i -> log.info("value 1 {}", i))
                .doFirst(() -> log.info("first 1"))
                .subscribeOn(Schedulers.parallel())
                .doFirst(() -> log.info("first 2"))
                .doOnNext(i -> log.info("value 2 {}", i))
                ;

        Runnable task1 = () -> {
            flux
                    .subscribe(Util.subscriber("sub1"));
        };

        Runnable task2 = () -> {
            flux
                    .subscribe(Util.subscriber("sub2"));
        };

        Thread thread1 = new Thread(task1);
        thread1.start();

        Thread thread2 = new Thread(task2);
        thread2.start();

        Util.sleepSecondDuration(2);
    }
}
