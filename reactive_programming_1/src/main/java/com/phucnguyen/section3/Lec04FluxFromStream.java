package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec04FluxFromStream {
    private static final Logger log = LoggerFactory.getLogger(Lec04FluxFromStream.class);

    public static void main(String[] args) {
        var list = List.of("a", "b", "c");
        var stream = list.stream();

//       Demo1:
//        Flux.fromStream(stream)
//                .subscribe(Util.subscriber());
//
//        Flux.fromStream(stream)
//                .subscribe(Util.subscriber());

//        Demo2:
        Flux.fromStream(() -> list.stream())
                .subscribe(Util.subscriber());

        Flux.fromStream(() -> list.stream())
                .subscribe(Util.subscriber());
    }
    
}
