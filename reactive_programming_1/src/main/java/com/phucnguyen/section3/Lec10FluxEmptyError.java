package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec10FluxEmptyError {
    private static final Logger log = LoggerFactory.getLogger(Lec10FluxEmptyError.class);


    public static void main(String[] args) {
        Flux.empty()
                .subscribe(Util.subscriber());

        Flux.error(new RuntimeException("error testing"))
                .subscribe(Util.subscriber());

    }
}
