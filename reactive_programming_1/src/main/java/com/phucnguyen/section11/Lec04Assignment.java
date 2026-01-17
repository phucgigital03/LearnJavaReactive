package com.phucnguyen.section11;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec04Assignment {
    private static final Logger log = LoggerFactory.getLogger(Lec04Assignment.class);

    public static void main(String[] args) {
        Flux.just("a")
                .repeat(1)
                .repeat(2)
                .subscribe(Util.subscriber());
    }
}
