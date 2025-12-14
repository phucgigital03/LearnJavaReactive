package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec03FluxFromIterableOrArray {
    private static final Logger log = LoggerFactory.getLogger(Lec03FluxFromIterableOrArray.class);


    public static void main(String[] args) {


        var list = List.of("a", "b", "c");
        Flux.fromIterable(list)
                .subscribe(Util.subscriber());

        Integer[] arr = {1, 2, 3};
        Flux.fromArray(arr)
                .subscribe(Util.subscriber());
    }

}
