package com.phucnguyen.section5;

import com.phucnguyen.common.Util;
import reactor.core.publisher.Flux;

public class Lec02HandelUntilAssignment {

    public static void main(String[] args) {
//      Cast to String here to make sure item at handle always receives string.
        Flux.<String>generate(synchronousSink -> {
            synchronousSink.next(Util.getFaker().country().name());
        })
                .handle((item, sink) -> {
                    sink.next(item);
                    if(item.equalsIgnoreCase("canada")){
                        sink.complete();
                    }
                })
                .subscribe(Util.subscriber());
    }
}
