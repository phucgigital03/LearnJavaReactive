package com.phucnguyen.section8;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    Reactor automatically handles the backpressure.
    We can also adjust the limit
*/
public class Lec02LimitRate {
    private static final Logger log = LoggerFactory.getLogger(Lec02LimitRate.class);

    public static void main(String[] args) {
        log.info("Start Lec02LimitRate");
        var producer = Flux.generate(
                        () -> 1,
                        (state, sink) -> {
                            log.info("generating {}", state);
                            sink.next(state);
                            return ++state;
                        }
                )
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer
                .limitRate(5) // limit items reach 5 items, it will stop producing temporary
                .publishOn(Schedulers.boundedElastic())
                .map(Lec02LimitRate::timeConsumingTask)
                .subscribe(Util.subscriber());


        Util.sleepSecondDuration(60);

    }

    private static int timeConsumingTask(int i) {
        Util.sleepSecondDuration(2);
        return i;
    }
}
