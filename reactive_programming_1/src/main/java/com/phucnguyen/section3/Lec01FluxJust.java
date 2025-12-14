package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec01FluxJust {
    private static final Logger log = LoggerFactory.getLogger(Lec01FluxJust.class);
    public static void main(String[] args) {
        log.info("Starting Lec01FluxJust");

        Flux.just(1)
                .subscribe(Util.subscriber());

        Flux.just(1, "Data", "Data 2")
                .subscribe(Util.subscriber());


        Flux.just(1, "Data", "Data 2")
                .subscribe(
                        i -> System.out.println(i),
                        e -> log.info(e.getMessage()),
                        () -> log.info("completed"),
                        subscription -> subscription.request(2)
                );

    }
}
