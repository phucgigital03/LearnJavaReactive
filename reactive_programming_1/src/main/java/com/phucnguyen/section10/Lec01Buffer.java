package com.phucnguyen.section10;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;


/*
    Collect items based on given interval / size
 */
public class Lec01Buffer {
    private static final Logger log = LoggerFactory.getLogger(Lec01Buffer.class);

    public static void main(String[] args) {
        log.info("start Lec01Buffer");

//        demo1();
//        demo2();
//        demo3();
        demo4();

        Util.sleepSecondDuration(60);
    }

    private static void demo1(){
        emitData()
                .buffer()
                // int-max value or the source has to complete
                .subscribe(Util.subscriber());
    }

    private static void demo2(){
        emitData()
                .buffer(3)
                // every 3 items
                .subscribe(Util.subscriber());
    }

    private static void demo3(){
        emitData()
                .buffer(Duration.ofMillis(500))
                // every 500ms
                .subscribe(Util.subscriber());
    }

    private static void demo4(){
        emitData()
                .bufferTimeout(3, Duration.ofSeconds(1))
                // every 3 items or max 1 second
                .subscribe(Util.subscriber());
    }

    private static Flux<String> emitData(){
        return Flux.interval(Duration.ofMillis(200))
                .take(10) // emit completed signal
                .concatWith(Flux.never())
                // does not emit completed signal, so we will miss some items
                // that inside buffer. To fix this issue, we add more one argument like demo 4
                .map(i -> "event-" + (i + 1));
    }


}
