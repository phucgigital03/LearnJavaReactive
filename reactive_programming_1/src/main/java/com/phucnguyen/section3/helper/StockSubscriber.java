package com.phucnguyen.section3.helper;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockSubscriber<T> implements Subscriber<T> {
    private static final Logger logger = LoggerFactory.getLogger(StockSubscriber.class);
    private Subscription subscription;
    private String name;
    private double balance = 1000;
    private final double originalBalance = balance;
    private int boughtCount = 0;

    public StockSubscriber(String name) {
        this.name = name;
    }

//    public Subscription getSubscription() {
//        return subscription;
//    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T item) {
        logger.info("{} received {}", name, item);
        double price = Double.parseDouble(item.toString());
        if(price < 90){
            balance -= price;
            boughtCount++;
        }else if(price > 110){
            if(boughtCount > 0){
//                for(int i = 0; i < boughtCount; i++){
//                    balance += price;
//                }
                balance += (price * boughtCount);
                boughtCount = 0;
                this.subscription.cancel();
                logger.info("profit made {}", balance - originalBalance);
            }
        }
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
