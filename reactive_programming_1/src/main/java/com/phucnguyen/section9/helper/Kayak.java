package com.phucnguyen.section9.helper;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class Kayak {
    public static Flux<Flight> getFlights() {
        return Flux.merge(
                        AmericanAirlines.getFlights(),
                        Emirates.getFlights(),
                        Qatar.getFlights()
                )
                .take(Duration.ofSeconds(2)); // Give me whatever flight results you can find within 2 seconds,
                // and then STOP immediately. Cancel everything else
    }
}
