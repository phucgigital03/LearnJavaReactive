package com.phucnguyen.common;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSubscriber<T> implements Subscriber<T> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSubscriber.class);
//    private Subscription subscription;
    private String name;

    public DefaultSubscriber(String name) {
        this.name = name;
    }

//    public Subscription getSubscription() {
//        return subscription;
//    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T item) {
        logger.info(name + " received " + item);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error(name + " error occurred", throwable);
    }

    @Override
    public void onComplete() {
        logger.info(name +  " completed");
    }
}
