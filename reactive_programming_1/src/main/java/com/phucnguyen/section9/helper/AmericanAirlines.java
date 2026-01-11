package com.phucnguyen.section9.helper;

import com.phucnguyen.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

// to represent the client class to call remote service
public class AmericanAirlines {
    private static final String AIRLINE = "American Airlines";

    public static Flux<Flight> getFlights(){
        return Flux.range(1, Util.getFaker().random().nextInt(5, 10))
                .delayElements(Duration.ofMillis(Util.getFaker().random().nextInt(200, 1200)))
                .map(i -> new Flight(AIRLINE, Util.getFaker().random().nextInt(300, 1200)))
                .transform(Util.fluxLogger(AIRLINE));
    }
}
