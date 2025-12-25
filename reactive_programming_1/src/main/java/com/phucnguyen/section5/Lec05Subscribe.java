package com.phucnguyen.section5;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;


public class Lec05Subscribe {
    private static final Logger log = LoggerFactory.getLogger(Lec05Subscribe.class);

    public static void main(String[] args) {
        Flux.range(1, 10)
//                .log()
                .doOnNext(i -> log.info("receive doOnNext {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(e -> log.error("error", e))
                .subscribe();
    }
}
