package com.phucnguyen.section7;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;


/*
    By default, the current thread is doing all the work
*/
public class Lec01DefaultBehaviourDemo {
    private static final Logger log = LoggerFactory.getLogger(Lec01DefaultBehaviourDemo.class);

    public static void main(String[] args) {

        var flux = Flux.create(sink -> {
            for (int i = 1; i < 3; i++) {
                log.info("generating value {}", i);
                sink.next(i);
            }
            sink.complete();
        })
                .doOnNext(i -> {log.info("value {}", i);});

//        flux.subscribe(Util.subscriber("sub1"));
//        flux.subscribe(Util.subscriber("sub2"));

        Runnable task1 = () -> {
            flux.subscribe(Util.subscriber("sub1"));
        };

        Runnable task2 = () -> {
            flux.subscribe(Util.subscriber("sub2"));
        };

        Thread thread1 = new Thread(task1); // it is the User Thread
        Thread thread2 = new Thread(task2); // it is the User Thread
//        thread1.setDaemon(true);
//        thread2.setDaemon(true);
        thread1.start();
        thread2.start();

    }
}

//Note:
//.delayElements() (Reactor) is the Daemon Thread
//JVM có đợi không ? Không, thoát ngay khi main xong.

