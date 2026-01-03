package com.phucnguyen.section7;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    Often times you really do not need this!
    - prefer non-blocking IO for network calls
 */
public class Lec08Parallel {

    private static final Logger log = LoggerFactory.getLogger(Lec08Parallel.class);

    public static void main(String[] args) {
        log.info("Start Lec08Parallel");
//        Flux.range(1, 1000)
//                .publishOn(Schedulers.parallel()) // not running parallelly
//                .map(i -> {
//                    log.info("value {}", i);
//                    return i;
//                })
//                .subscribe(Util.subscriber());
//
//        Util.sleepSecondDuration(10);


        Flux.range(1, 10)
                .parallel() // edit 10 for Schedulers.boundedElastic()
                .runOn(Schedulers.parallel()) // Schedulers.boundedElastic()
                .map(Lec08Parallel::process)
                //   .sequential()
                .map(i -> i + "a")
                .subscribe(Util.subscriber());

        Util.sleepSecondDuration(30);
    }

    private static int process(int i){
        log.info("time consuming task {}", i);
        Util.sleepSecondDuration(1);
        return i * 2;
    }
}
