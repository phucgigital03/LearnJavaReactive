package com.phucnguyen.section4.helper;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class NameGenerator implements Consumer<FluxSink<String>> {
    private static final Logger log = LoggerFactory.getLogger(NameGenerator.class);
    private FluxSink<String> sink;

    @Override
    public void accept(FluxSink<String> sink) {
        this.sink = sink;
    }

    public void generateName() {
        log.info("thread name: " + Thread.currentThread().getName());
        this.sink.next(Util.getFaker().name().firstName());
    }
}
