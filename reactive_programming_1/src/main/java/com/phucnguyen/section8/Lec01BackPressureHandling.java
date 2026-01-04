package com.phucnguyen.section8;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;


/*
    1. Reactor automatically handles the backpressure with only Flux generate.
    Flux generate run sequentially to save **previous state

    2. System.setProperty("reactor.bufferSize.small", "16");
 */
public class Lec01BackPressureHandling {
    private static final Logger log = LoggerFactory.getLogger(Lec01BackPressureHandling.class);

    public static void main(String[] args) {
        log.info("Start Lec01BackPressureHandling");
//        System.setProperty("reactor.bufferSize.small", "16");

        var flux = Flux.generate(
                ()-> 1,
                (state,sink)->{
                    log.info("generating {}", state);
                    sink.next(state);
                    return ++state;
                }
        )
                .subscribeOn(Schedulers.parallel())
                .cast(Integer.class);

        var fluxCreate = Flux.create(fluxSink -> {
            for (int i = 1; i <= 300; i++) {
                log.info("generating {}", i);
                fluxSink.next(i);
            }
            fluxSink.complete();
        })
                .subscribeOn(Schedulers.parallel())
                .cast(Integer.class);

//        fluxCreate
//                .publishOn(Schedulers.boundedElastic())
//                .map(Lec01BackPressureHandling::timeConsume)
//                .subscribe(Util.subscriber());


        flux
                .publishOn(Schedulers.boundedElastic())
                .map(Lec01BackPressureHandling::timeConsume)
                .subscribe(Util.subscriber());
                ;

        Util.sleepSecondDuration(20);
    }

    private static int timeConsume(int i){
        log.info("timeConsume {}", i);
        try {
            Thread.sleep(Duration.ofMillis(1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

}
