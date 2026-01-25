package com.phucnguyen.section12;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

public class Lec04Multicast {
    private static final Logger log = LoggerFactory.getLogger(Lec04Multicast.class);

    public static void main(String[] args) {

//        demo1();

        demo2();


    }

    private static void demo1(){

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        // ***Note: It answers the question: "What happens if I push data
        // into the Sink faster than the Subscriber can read it?"
        var sink = Sinks.many().multicast().onBackpressureBuffer();

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

    // warmup
    private static void demo2(){

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().onBackpressureBuffer();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        Util.sleepSecondDuration(2);

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));
        flux.subscribe(Util.subscriber("jake"));

        sink.tryEmitNext("new message");

    }


}
