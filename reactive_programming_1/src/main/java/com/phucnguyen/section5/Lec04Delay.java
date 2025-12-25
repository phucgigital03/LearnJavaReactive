package com.phucnguyen.section5;

import com.phucnguyen.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Lec04Delay {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Util.subscriber());

        Util.sleepSecondDuration(12);
    }
}
