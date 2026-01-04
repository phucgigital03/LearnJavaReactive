package com.phucnguyen.common;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class  Util {
    private static final Faker faker = Faker.instance();

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

//    public static void main(String[] args) {
//        var mono  = Mono.just("Hello");
//
//        mono.subscribe(subscriber());
//        mono.subscribe(subscriber("sub1"));
//    }
}
