package com.phucnguyen.section2;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;


public class Lec09PublisherCreateVsExecution {
    private static final Logger log = LoggerFactory.getLogger(Lec09PublisherCreateVsExecution.class);

    public static void main(String[] args) {
//      Demo 1: It is just create Publisher
//        getName();
//      Demo 2: It create publisher and when call .subscribe() execute business inside
        getName()
                .subscribe(Util.subscriber());
        log.info("Thread name: {}", Thread.currentThread().getName());
    }

    private static Mono<String> getName(){
        log.info("entered get name method");
        return Mono.fromSupplier(() -> {
            log.info("Thread name: {}", Thread.currentThread().getName());
            log.info("generating name");
            Util.sleepSecondDuration(2);
            return Util.getFaker().name().firstName();
        });
    }

}
