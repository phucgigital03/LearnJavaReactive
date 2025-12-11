package com.phucnguyen.section2;

import com.phucnguyen.section1.subscriber.SubscriberImpl;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

public class Lec02MonoJust {
    private static final Logger log = LoggerFactory.getLogger(Lec02MonoJust.class);

    public static void main(String[] args) {
        var mono = Mono.just("Hello");
        var subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);

//        subscriber.getSubscription().cancel();
//        subscriber.getSubscription().request(3);

        subscriber.getSubscription().request(3);
        subscriber.getSubscription().cancel();

        System.out.println(mono);

        save(Mono.just("To pass arg")); // create quickly
    }

    private static final void save(Publisher<String> publisher) {

    }
}
