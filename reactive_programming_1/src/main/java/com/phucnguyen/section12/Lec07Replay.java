package com.phucnguyen.section12;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

public class Lec07Replay {
    private static final Logger log = LoggerFactory.getLogger(Lec07Replay.class);

    public static void main(String[] args) {
        demo1();
    }

    private static void demo1(){

        // handle through which we would push items
        var sink = Sinks.many().replay().all(1);

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        Util.sleepSecondDuration(2);

        flux.subscribe(Util.subscriber("jake"));
        sink.tryEmitNext("new message");

    }
}
