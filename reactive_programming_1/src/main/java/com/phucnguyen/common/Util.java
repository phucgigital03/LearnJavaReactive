package com.phucnguyen.common;

import com.github.javafaker.Faker;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class  Util {
    private static final Faker faker = Faker.instance();
    private static final Logger log = LoggerFactory.getLogger(Util.class);

    public static void sleepSecondDuration(long duration) {
        try {
            Thread.sleep(Duration.ofSeconds(duration));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Faker getFaker() {
        return faker;
    }

    public static <T> Subscriber<T> subscriber(){
        return new DefaultSubscriber<T>("");
    }

    public static <T> Subscriber<T> subscriber(String name){
        return new DefaultSubscriber<T>(name);
    }

    public static <T> Function<Flux<T>,Flux<T>> fluxLogger(String name) {
        return flux -> flux
                .doOnSubscribe(s -> log.info("subscribing to {}", name))
                .doOnCancel(() -> log.info("cancelling {}", name))
                .doOnComplete(() -> log.info("{} completed", name));
    }

//    public static void main(String[] args) {
//        var mono  = Mono.just("Hello");
//
//        mono.subscribe(subscriber());
//        mono.subscribe(subscriber("sub1"));
//    }
}
