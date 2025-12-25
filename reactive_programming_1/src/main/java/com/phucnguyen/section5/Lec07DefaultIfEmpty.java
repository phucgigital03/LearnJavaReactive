package com.phucnguyen.section5;

import com.phucnguyen.common.Util;
import reactor.core.publisher.Flux;

public class Lec07DefaultIfEmpty {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .filter(i -> i > 11)
                .defaultIfEmpty(-1)
                .subscribe(Util.subscriber());
    }
}
