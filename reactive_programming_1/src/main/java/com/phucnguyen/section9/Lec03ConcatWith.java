package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec03ConcatWith {
    private static final Logger log = LoggerFactory.getLogger(Lec03ConcatWith.class);

    public static void main(String[] args) {

//        demo1();
//        demo2();
        demo3();

        Util.sleepSecondDuration(10);
    }

    private static void demo1(){
        producer1()
                .concatWithValues(-1, 0)
                .subscribe(Util.subscriber());
    }

    private static void demo2(){
        producer1()
                .concatWith(producer2())
                .take(2)
                .subscribe(Util.subscriber());
    }

    private static void demo3(){
        Flux.concat(producer1(), producer2())
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
