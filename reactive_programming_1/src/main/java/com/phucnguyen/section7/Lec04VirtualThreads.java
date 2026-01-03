package com.phucnguyen.section7;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    reactor supports virtual threads
    System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");
*/
public class Lec04VirtualThreads {
    private static final Logger log = LoggerFactory.getLogger(Lec04VirtualThreads.class);

    public static void main(String[] args) {
        log.info("Start Lec04VirtualThreads");

        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");

        var flux = Flux.create(sink -> {
                    for (int i = 1; i <= 2; i++) {
                        log.info("generating {}", i);
                        sink.next(i);
                    }
                    sink.complete();
                })
                .doOnNext(i -> log.info("value {}", i))
                .doFirst(() -> log.info("first 1 - {}", Thread.currentThread().isVirtual()))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first 2"))
                ;

        Runnable task1 = () -> {
            flux
                    .subscribe(Util.subscriber("sub1"));
        };

//        Runnable task2 = () -> {
//            flux
//                    .subscribe(Util.subscriber("sub2"));
//        };

        Thread thread1 = new Thread(task1);
        thread1.start();

//        Thread thread2 = new Thread(task2);
//        thread2.start();

        Util.sleepSecondDuration(2);
    }
}
