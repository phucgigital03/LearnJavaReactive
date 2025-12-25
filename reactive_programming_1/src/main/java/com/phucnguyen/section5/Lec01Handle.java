package com.phucnguyen.section5;

// Handle behaves like filter + map

import com.phucnguyen.common.Util;
import reactor.core.publisher.Flux;

public class Lec01Handle {
    public static void main(String[] args) {
        System.out.println("handle operator");
//        business requirements
//        1 => -2
//        4 => do not send
//        7 => error
//        everything else => send as it is

        Flux.range(1, 10)
                .handle((integer, sink) -> {
                    if(integer == 1) {
                        sink.next(-2);
                    }else if(integer == 4){
//                        sink.next(null);
                    }else if(integer == 7){
                        sink.error(new RuntimeException("oops"));
                    }else {
                        sink.next(integer);
                    }
                })
                // Each operator have new java object like (Flux<Integer>, Flux<Object>, ...)
                // so we know just have only integer, we can cast to ...
                .cast(Integer.class)
                .subscribe(Util.subscriber());

//      Demo: Each operator have New Java Object like (Flux<Integer>, Flux<Object>, ...)
//      and then process like Publisher
        Flux<Integer> fluxFirst = Flux.range(1, 10);
        Flux<Integer> fluxSecond = fluxFirst.handle((integer, sink) -> {
            if(integer == 1 || integer == 2) {
                sink.next(integer);
            }else{
                sink.error(new RuntimeException("oops"));
            }
        });

        fluxFirst.subscribe(Util.subscriber());
        fluxSecond.subscribe(Util.subscriber());
    }
}
