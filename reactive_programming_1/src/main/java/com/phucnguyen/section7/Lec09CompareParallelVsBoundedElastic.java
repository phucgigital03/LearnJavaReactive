package com.phucnguyen.section7;


import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

// ***Note Compare to:
// Schedulers.boundedElastic(): dynamic pool
// vs Schedulers.parallel(): fixed pool
public class Lec09CompareParallelVsBoundedElastic {
    private static final Logger log = LoggerFactory.getLogger(Lec09CompareParallelVsBoundedElastic.class);

    public static void main(String[] args) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Your Machine Cores: " + cores);

//        // We have 20 tasks. Each takes 1 second.
        int numberOfTasks = 20;

        System.out.println("\n--- TEST 1: Schedulers.parallel() (Fixed Pool) ---");
        measureTimeWithIO("Parallel", numberOfTasks, Schedulers.parallel());

        System.out.println("\n--- TEST 2: Schedulers.boundedElastic() (Dynamic Pool) ---");
        measureTimeWithIO("BoundedElastic", numberOfTasks, Schedulers.boundedElastic());


        // We run 40 heavy math tasks (more than your 8 cores)
//        int numberOfTasks = 100;
//        measureTimeWithMath("Warm up ", 10, Schedulers.parallel());
//
//        measureTimeWithMath("Parallel ", numberOfTasks, Schedulers.parallel());
//        measureTimeWithMath("BoundedElastic ", numberOfTasks, Schedulers.boundedElastic());

    }

    private static void measureTimeWithIO(String name, int tasks, reactor.core.scheduler.Scheduler scheduler) throws InterruptedException {
        long start = System.currentTimeMillis();

        // Latch to wait for all flows to finish before stopping main thread
        CountDownLatch latch = new CountDownLatch(tasks);

        Flux.range(1, tasks)
                .flatMap(i -> {

                    return Mono.fromCallable(() -> {
                                log.info("generating {}", i);
                                // Simulate blocking I/O (Database call)
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                }
                                return i;
                            })
                            .doOnNext(val -> {
                                log.info("value {}", val);
                            })
                            .subscribeOn(scheduler); // <--- The crucial switch
                })
                .doOnNext(i -> {
                    // System.out.println(name + " finished task " + i + " on " + Thread.currentThread().getName());
                    latch.countDown();
                })
                .subscribe(Util.subscriber());

        // Wait for all tasks to finish
        latch.await();

        long end = System.currentTimeMillis();
        System.out.println("Time taken with " + name + ": " + (end - start) + "ms");
    }

    private static void measureTimeWithMath(String name, int tasks, reactor.core.scheduler.Scheduler scheduler) throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(tasks);

        Flux.range(1, tasks)
                .flatMap(i -> Mono.fromCallable(() -> fib(42))
                        .subscribeOn(scheduler) // <--- Force scheduler to pick a thread per task
                )
                .doOnNext(i -> latch.countDown())
                .subscribe();

        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("Time taken with " + name + ": " + (end - start) + "ms");
    }


    // A very inefficient recursive calculation to burn CPU cycles
    private static long fib(int n) {
        if (n <= 1) return n;
        return fib(n - 1) + fib(n - 2);
    }

}
