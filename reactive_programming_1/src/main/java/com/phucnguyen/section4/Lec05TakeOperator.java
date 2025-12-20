package com.phucnguyen.section4;

import com.phucnguyen.common.Util;
import reactor.core.publisher.Flux;

public class Lec05TakeOperator {

    public static void main(String[] args) {
        System.out.println("Take operator is similar to Stream's limit");

//        take()
//        takeWhile();
        takeUntil();

    }

    private static void take(){
        Flux.range(1, 10)
                .log("take")
                .take(3)
                .log("sub")
                .subscribe(Util.subscriber());
    }

    private static void takeWhile(){
        Flux.range(1, 10)
                .takeWhile(i -> i < 5) // stop when condition is not met
                .subscribe(Util.subscriber());

    }

    private static void takeUntil(){
        Flux.range(1, 10)
                .takeUntil(i -> i == 2) // stop when condition is met + allow the last item.
                .subscribe(Util.subscriber());
    }
}
