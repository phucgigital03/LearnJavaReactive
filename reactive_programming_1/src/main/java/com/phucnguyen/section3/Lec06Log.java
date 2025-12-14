package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec06Log {
    private static final Logger log = LoggerFactory.getLogger(Lec06Log.class);

    public static void main(String[] args) {
//        ****Summary:
//        onSubscribe goes Down (Source -> You).
//        request goes Up (You -> Source).
//        onNext goes Down (Source -> You).


        Flux.range(1, 5)
                .log("range-map")
                .map(i -> Util.getFaker().name().fullName())
                .log("map-sub")
                .subscribe(Util.subscriber());

    }
    
}
