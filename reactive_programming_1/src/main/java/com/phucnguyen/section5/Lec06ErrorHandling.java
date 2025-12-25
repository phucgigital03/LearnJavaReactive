package com.phucnguyen.section5;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/*
    How to handle error in a reactive pipeline
    Flux.(...)
        ...
        ...
        ...
        ...
 */
public class Lec06ErrorHandling {
    private static final Logger log = LoggerFactory.getLogger(Lec06ErrorHandling.class);

    public static void main(String[] args) {
//        onErrorReturn();

//        onErrorResume();

//        onErrorComplete();

        onErrorContinue();
    }

//    when returning hardcode value and simple computation
    private static void onErrorReturn() {
        Flux.range(1, 10)
                .onErrorReturn(-3) // some onErrorReturns will process for each Publisher (this one for Flux.range)
                .map(i -> i == 5 ? i/0 : i)
//                .onErrorReturn(-3)
                .onErrorReturn(IllegalArgumentException.class, -1) // this one for Flux.map
                .onErrorReturn(ArithmeticException.class, -2)
                .onErrorReturn(-3) // .onErrorReturn(-3) place following the order
                .subscribe(Util.subscriber());

    }

//    when using another Publisher in case of error
    private static void onErrorResume(){
//        Flux.range(1, 10)
//                .map(i -> i == 5 ? i / 0 : i)
        Mono.error(new Exception("oops"))
                .onErrorResume(ArithmeticException.class,e -> fallback2())
                .onErrorResume(e -> fallback1())
                .onErrorReturn(IllegalArgumentException.class,-5)
                .onErrorReturn(-5)
                .subscribe(Util.subscriber());
    }

//    in case of error, emit complete
    private static void onErrorComplete(){
//        Mono.error(new Exception("oops"))
        Mono.just(1)
                .onErrorComplete()
                .subscribe(Util.subscriber());

    }

//    skip the error and continue
    private static void onErrorContinue(){
        Flux.range(1, 10)
                .map(i -> i == 5 ? i/0 : i)
                .onErrorContinue((ex,obj) -> {
                    log.info("onErrorContinue {} , value {}", ex.getMessage(), obj);
                })
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> fallback1() {
//        return Mono.fromSupplier(() -> Util.getFaker().random().nextInt(10, 100));
        return Mono.error(new RuntimeException("oops fallback1"));
    }

    private static Mono<Integer> fallback2() {
        return Mono.fromSupplier(() -> Util.getFaker().random().nextInt(100, 1000));
    }

}
