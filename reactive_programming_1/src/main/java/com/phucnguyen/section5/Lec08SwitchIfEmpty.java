package com.phucnguyen.section5;

import com.phucnguyen.common.Util;
import reactor.core.publisher.Flux;

public class Lec08SwitchIfEmpty {
    public static void main(String[] args) {

//      Using for cases: Postgresql + Redis

        Flux.range(1, 10)
                .filter(i -> i > 11)
                .switchIfEmpty(fallBack())
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> fallBack(){
        return Flux.range(100, 6);
    }
}
