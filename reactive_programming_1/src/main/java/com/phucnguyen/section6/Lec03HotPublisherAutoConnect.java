package com.phucnguyen.section6;


import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    almost same as publish().refCount(1).
    - does NOT stop when subscribers cancel. So it will start
      producing even for 0 subscribers once it started.
    - make it real hot - publish().autoConnect(0)
*/
public class Lec03HotPublisherAutoConnect {
    private static final Logger log = LoggerFactory.getLogger(Lec02HoldPublisher.class);

    public static void main(String[] args) {
        var movieFlux = movieStream()
                .publish().autoConnect(0); //default by 1

        Util.sleepSecondDuration(4);
        movieFlux
                .take(7)
                .subscribe(Util.subscriber("phuc"));


        Util.sleepSecondDuration(2);
        movieFlux
                .take(4)
                .subscribe(Util.subscriber("heo"));

        Util.sleepSecondDuration(15);
    }

    private static Flux<String> movieStream() {
        return Flux.generate(
                        () -> {
                            log.info("received the request {}", Thread.currentThread().getName());
                            return 1;
                        },
                        (state, sink) -> {
                            var scene = "movie scene " + state;
                            log.info("playing {}", scene);
                            sink.next(scene);
                            return ++state;
                        }// One thread does and exit right here.
                )
                .take(10)
                .delayElements(Duration.ofSeconds(1))
                .cast(String.class);
    }
}
