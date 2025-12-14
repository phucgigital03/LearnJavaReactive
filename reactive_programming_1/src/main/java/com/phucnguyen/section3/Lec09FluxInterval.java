package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec09FluxInterval {
    private static final Logger log = LoggerFactory.getLogger(Lec09FluxInterval.class);


    public static void main(String[] args) {
        Flux.interval(Duration.ofMillis(500))
                .map(i -> Util.getFaker().name().fullName())
                .subscribe(Util.subscriber());

        Util.sleepSecondDuration(5);
    }
    
}
