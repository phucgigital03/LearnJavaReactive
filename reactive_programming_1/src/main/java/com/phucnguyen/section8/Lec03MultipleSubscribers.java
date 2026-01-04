package com.phucnguyen.section8;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec03MultipleSubscribers {
    private static final Logger log = LoggerFactory.getLogger(Lec03MultipleSubscribers.class);

    public static void main(String[] args) {
        log.info("Start Lec03MultipleSubscribers");
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
                .map(Lec03MultipleSubscribers::timeConsumingTask)
                .subscribe(Util.subscriber("sub1"));

        producer
                .take(100)
                .publishOn(Schedulers.boundedElastic())
                .subscribe(Util.subscriber("sub2"));

        Util.sleepSecondDuration(60);
    }

    private static int timeConsumingTask(int i) {
        Util.sleepSecondDuration(1);
        return i;
    }
}
