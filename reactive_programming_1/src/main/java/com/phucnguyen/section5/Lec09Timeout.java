package com.phucnguyen.section5;


import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

/*
    timeout - will produce timeout error.
        - We can handle as part of onError methods
    there is also an overloaded method to accept a publisher
    we can have multiple timeouts. the closest one to the subscriber will take effect for the subscriber.
 */
public class Lec09Timeout {
    private static final Logger log = LoggerFactory.getLogger(Lec09Timeout.class);

    public static void main(String[] args) {
        var mono = getProductName()
                .timeout(Duration.ofMinutes(300));

        mono
                .timeout(Duration.ofMillis(800), fallBackProductName())
                .subscribe(Util.subscriber());

        Util.sleepSecondDuration(2);
    }

    private static Mono<String> getProductName() {
        return Mono.fromSupplier(() -> "service - " + Util.getFaker().name().fullName())
                .delayElement(Duration.ofMillis(600))
                ;
    }

    private static Mono<String> fallBackProductName() {
        return Mono.fromSupplier(() -> "fallback - " + Util.getFaker().name().fullName())
                .delayElement(Duration.ofMillis(900))
                .doFirst(()-> log.info("do First"))
                ;
    }
}
