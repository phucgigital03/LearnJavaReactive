package com.phucnguyen.section2;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

//⚠️ The "Eager" Trap (Crucial!)
//There is a big catch you must understand.
//Mono is normally Lazy (it shouldn't start until you subscribe).
//CompletableFuture is Eager (it starts the moment you create it).

//How to make it truly Lazy?
//If you want the CompletableFuture to only start when someone subscribes to the Mono,
// you must use a Supplier inside fromFuture (available in newer Reactor versions)
// or wrap it in defer.

//***Note: Summary
//1. Use Mono.fromFuture(future) when you have a CompletableFuture that is already running
// and you want to join the results.
//2. Use Mono.fromFuture(() -> future) when you want to define a task that should start
// fresh every time someone subscribes.

public class Lec08MonoFromFuture {
    private static final Logger log = LoggerFactory.getLogger(Lec08MonoFromFuture.class);

    public static void main(String[] args) {
        System.out.println("Lec08MonoFromFuture");
//      Demo 1: should be careful because 2 threads working
//        Mono.fromFuture(getName())
//                .subscribe(Util.subscriber());
//        log.info("Thread name: {}", Thread.currentThread().getName());

//      Demo 2: add main Thread sleep
//        Mono.fromFuture(getName())
//                .subscribe(Util.subscriber());
//        log.info("Thread name: {}", Thread.currentThread().getName());
//
//        Util.sleepSecondDuration(1);

//        Demo 3: Not having supplier (eager style)
//        Mono.fromFuture(getName());
//        log.info("Thread name: {}", Thread.currentThread().getName());
//
//        Util.sleepSecondDuration(1);

//        Demo 4: Having supplier (lazy style)
        Mono.fromFuture(() -> getName());
//                .subscribe(Util.subscriber());
        log.info("Thread name: {}", Thread.currentThread().getName());

        Util.sleepSecondDuration(1);
    }

    private static CompletableFuture<String> getName(){
        return CompletableFuture.supplyAsync(()->{
            log.info("Thread name: {}", Thread.currentThread().getName());
            log.info("generating name");
            return Util.getFaker().name().fullName();
        });
    }

}
