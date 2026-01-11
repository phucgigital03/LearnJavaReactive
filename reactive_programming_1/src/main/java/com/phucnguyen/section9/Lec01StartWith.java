package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

/*
    Calls multiple publishers in a specific order
*/
public class Lec01StartWith {
    private static final Logger log = LoggerFactory.getLogger(Lec01StartWith.class);

    public static void main(String[] args) {
        log.info("Lec01StartWith");

//        demo1();
//        demo2();
//        demo3();
        demo4();


        Util.sleepSecondDuration(16);

    }

    private static void demo1() {
        producer1()
                .startWith(-1,0)
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        producer1()
                .startWith(List.of(-2, -1, 0))
                .subscribe(Util.subscriber());
    }

    private static void demo3() {
        producer1()
                .startWith(producer2())
                .subscribe(Util.subscriber());
    }

    // 49,50,51,52,53,-1,-2,-3,1,2,3
    private static void demo4() {
        producer1()
                .startWith(-1,-2,-3)
                .startWith(producer2())
                .startWith(49, 50)
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> producer1(){
        return Flux.just(1,2,3,4,5,6)
                .doOnSubscribe(sub -> log.info("subscribing to producer 1 with {}",sub))
                .delayElements(Duration.ofMillis(1000));
    }

    private static Flux<Integer> producer2(){
        return Flux.just(51,52,53,54,55)
                .doOnSubscribe(sub -> log.info("subscribing to producer 2 with {}",sub))
                .delayElements(Duration.ofMillis(1000));
    }

}
