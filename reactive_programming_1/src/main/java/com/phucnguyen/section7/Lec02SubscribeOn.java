package com.phucnguyen.section7;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class Lec02SubscribeOn {
    private static final Logger log = LoggerFactory.getLogger(Lec02SubscribeOn.class);

    public static void main(String[] args) {
        log.info("Start Lec02SubscribeOn");

//        Parent p = new Child();
//        System.out.println("var cha " + p.name);
//
//        Child c = new Child();
//        System.out.println("var con " + c.name);
//
//        p.show();
//        c.show();

        var flux = Flux.create(sink -> {
            for (int i = 1; i <= 2; i++) {
                log.info("generating {}", i);
                sink.next(i);
            }
            sink.complete();
        })
                .doOnNext(i -> log.info("value {}", i))
                .doFirst(() -> log.info("first 1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first 2"))
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
//        ***Important Note
//        If you removed .subscribeOn(...), the Flux.create loop would
//        run on the calling thread (Thread-0 and Thread-1 in your
//        case). Since you created new Thread(task1) and new Thread(task2),
//        they would still run in parallel, but they would use your manual threads
//        rather than the Reactor pool.

        Util.sleepSecondDuration(2);
    }
}

// Nếu ép về lớp Cha thì không dùng được các
// biến và hàm của lớp con.
// ***Note: quan trọng ở cách khởi tạo
class Parent {
    public String name = "Cha";

    public void show(){
        System.out.println("cha show " + name);
    }
}

class Child extends Parent {
    public String name = "Con";

    public void show(){
        System.out.println("con show " + name);
    }
}
