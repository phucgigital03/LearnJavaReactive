package com.phucnguyen.section6;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec01ColdPublisher {

    private static final Logger log = LoggerFactory.getLogger(Lec01ColdPublisher.class);

    public static void main(String[] args) {

//      This example vs Lec02FluxCreateRefactor in section 4
//      1. This example not create sink outside
//      2. Lec02FluxCreateRefactor create accept that is class
        AtomicInteger count = new AtomicInteger(0);
        var flux = Flux.create(sink -> {
            log.info("invoked");
            for (int i = 1; i <= 3; i++) {
                sink.next(count.incrementAndGet());
            }
            sink.complete();
        });

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

    }
}
