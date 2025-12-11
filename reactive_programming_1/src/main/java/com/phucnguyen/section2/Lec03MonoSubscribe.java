package com.phucnguyen.section2;

import com.phucnguyen.section1.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec03MonoSubscribe {
    private static final Logger log = LoggerFactory.getLogger(Lec03MonoSubscribe.class);

    public static void main(String[] args) {
        System.out.println("Lec03MonoSubscribe");

//        var mono = Mono.just(1)

//        var mono = Mono.just(1)
//                .map(i -> i + "a");

        var mono = Mono.just(1)
                .map(i -> i / 0);

        mono.subscribe(
                i -> log.info("i = " + i),
                err -> log.error("err = {}", err.getMessage()),
                () -> log.info("Completed"),
                subscription ->  subscription.request(2)
        );

    }
}
