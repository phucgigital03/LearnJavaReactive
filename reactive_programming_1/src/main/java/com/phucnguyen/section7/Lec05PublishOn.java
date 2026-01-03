package com.phucnguyen.section7;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/*
    publish on for downstream!
 */
public class Lec05PublishOn {

    private static final Logger log = LoggerFactory.getLogger(Lec05PublishOn.class);

    public static void main(String[] args) {
        log.info("Start Lec05PublishOn");
        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");

        var flux = Flux.create(sink -> {
                    for (int i = 1; i <= 5; i++) {
                        log.info("generating {}", i);
                        sink.next(i);
                    }
                    sink.complete();
                })
                .publishOn(Schedulers.parallel()) // multiple publish on
                .doOnNext(i -> log.info("value {}", i))
                .doFirst(() -> log.info("first 1"))
                .publishOn(Schedulers.boundedElastic())
                .map(i -> {
                    try {
                        log.info("processing {}", i);
                        Thread.sleep(Duration.ofSeconds(3));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i;
                })
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

        Util.sleepSecondDuration(16);
    }
}

// ***Default: Schedulers.parallel() and Schedulers.boundedElastic()
// do not run parallelly. They are just a thread pool and
// run in the concurrency way
