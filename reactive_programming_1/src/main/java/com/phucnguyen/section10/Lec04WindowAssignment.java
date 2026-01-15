package com.phucnguyen.section10;

import com.phucnguyen.common.Util;
import com.phucnguyen.section10.assignment.window.FileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Lec04WindowAssignment {
    private static final Logger log = LoggerFactory.getLogger(Lec04WindowAssignment.class);

    public static void main(String[] args) {
        var counter = new AtomicInteger(0);
        var fileNameFormat = "reactive_programming_1/src/main/resources/sec10/file%d.txt";

        eventStream()
                .window(5) // just for demo
                .flatMap(flux -> {
                    log.info("inside flapMap");
                    return FileWriter.create(flux, Path.of(fileNameFormat.formatted(counter.incrementAndGet())));
                })
                .subscribe();

        Util.sleepSecondDuration(60);
    }

    private static Flux<String> eventStream(){
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "Event-" + (i + 1));
    }

}
