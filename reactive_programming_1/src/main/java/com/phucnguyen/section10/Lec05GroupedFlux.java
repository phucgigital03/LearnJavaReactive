package com.phucnguyen.section10;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec05GroupedFlux {
    private static final Logger log = LoggerFactory.getLogger(Lec05GroupedFlux.class);

    public static void main(String[] args) {
        Flux.range(1,30)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> {
                    if(i == 1){
                        return i;
                    }else{
                        return i * 2;
                    }
                })
                .startWith(1)
                .groupBy(i -> i % 2)
                .flatMap(groupFlux -> processStream(groupFlux))
                .subscribe();


        Util.sleepSecondDuration(60);
    }

    private static Mono<Void> processStream(GroupedFlux<Integer,Integer> groupedFlux) {
        log.info("start processing stream with key {}", groupedFlux.key());
        return groupedFlux
                .doOnNext(i -> log.info("key: {}, value: {}", groupedFlux.key(), i))
                .doOnComplete(() -> {
                    log.info("end processing stream with key {}", groupedFlux.key());
                })
                .then()
                ;
    }

}
