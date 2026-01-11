package com.phucnguyen.section9.helper;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

public class NameGenerator {
    private static final Logger log = LoggerFactory.getLogger(NameGenerator.class);
    private static final ArrayList<String> nameList = new ArrayList<>();

    public static Flux<String> generateName(){
        return Flux.generate(sink -> {
            var name = Util.getFaker().name().fullName();
            log.info("generating name {}", name);
            Util.sleepSecondDuration(1);
            sink.next(name);
            nameList.add(name);
        })
                .startWith(nameList)
                .cast(String.class)
                ;
    }
}
