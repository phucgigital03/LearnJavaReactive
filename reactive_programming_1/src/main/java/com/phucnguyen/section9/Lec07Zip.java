package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Zip
    - we will subscribe to all the producers at the same time
    - all or nothing
    - all producers will have to emit an item
 */
public class Lec07Zip {
    private static final Logger log = LoggerFactory.getLogger(Lec07Zip.class);

    record Car(String body, String engine, String tires){}

    public static void main(String[] args) {
        log.info("Start Lec07Zip");

        Flux.zip(getBody(), getEngine(), getTires())
                .map(t -> new Car(t.getT1(), t.getT2(), t.getT3()))
                        .subscribe(Util.subscriber("car"));

        Util.sleepSecondDuration(5);
    }

    private static Flux<String> getBody(){
        return Flux.range(1,5)
                .map(i -> "body-" + i)
//                .map(i -> {
//                    if(i == 1){
//                        throw new RuntimeException("oops");
//                    }
//                    return "body" + i;
//                })
                .delayElements(Duration.ofMillis(100));
    }

    private static Flux<String> getEngine(){
        return Flux.range(1,3)
                .map(i -> "engine-" + i)
                .delayElements(Duration.ofMillis(200));
    }

    private static Flux<String> getTires(){
        return Flux.range(1,10)
                .map(i -> "tires-" + i)
                .delayElements(Duration.ofMillis(75));
    }


}
