package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec05FluxRange {
    private static final Logger log = LoggerFactory.getLogger(Lec05FluxRange.class);


    public static void main(String[] args) {
        Flux.range(1, 10)
                .subscribe(Util.subscriber());

        Flux.range(3, 10)
                .subscribe(Util.subscriber());
    }
    
}
