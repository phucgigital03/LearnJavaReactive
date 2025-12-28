package com.phucnguyen.section6;


import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    - publish().autoConnect(0) will provide new values to the subscribers.
    - replay allows us to cache
*/
public class Lec04HotPublisherCache {
    private static final Logger log = LoggerFactory.getLogger(Lec02HoldPublisher.class);

    public static void main(String[] args) {
        var stockFlux = stockStream()
                .replay(3).autoConnect(0); //default by 1

        Util.sleepSecondDuration(8);
        log.info("phuc start joining");
        stockFlux
                .subscribe(Util.subscriber("phuc"));


        Util.sleepSecondDuration(4);
        log.info("heo start joining");
        stockFlux
                .subscribe(Util.subscriber("heo"));

        Util.sleepSecondDuration(15);
    }

    private static Flux<Integer> stockStream() {
        return Flux.generate(
                     sink -> {
                         sink.next(Util.getFaker().random().nextInt(10,100));
                     }
                )
                .delayElements(Duration.ofSeconds(2))
                .doOnNext(price -> log.info("emitting price {}", price))
                .cast(Integer.class);
    }

}
