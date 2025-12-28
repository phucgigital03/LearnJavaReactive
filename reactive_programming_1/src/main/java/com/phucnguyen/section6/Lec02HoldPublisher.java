package com.phucnguyen.section6;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Concept:
    Hot - 1 data producer for all the subscribers.
    share => publish().refCount(1)
    It needs 1 min subscriber to emit data.
    It stops when there is 0 subscriber.
    Re-subscription - It starts again where there is a new subscriber.
    To have min 2 subscribers, use publish().refCount(2);
 */


// ***Explain for using case 2 (number 6)
//How share() Works in Detail
//When you use .share(), Reactor inserts a "middleman" (a Coordinator)
//between the source and the subscribers.
//
//The Reference Counting (refCount)
//The share() operator is actually a combination of publish() and refCount(1).
//
//Subscription 1 (phuc): The middleman sees the "Reference Count" go from 0 to 1.
// It says, "Okay, someone is watching," and it triggers the upstream Flux.generate.
//
//Subscription 2 (heo): The middleman sees the count go from 1 to 2.
// Instead of calling the generator again, it just adds "heo" to
// its internal list of "people to notify."
//
//Data Delivery: When Scene 5 is generated, the middleman loops
// through its list and calls .onNext() for both phuc and heo.


// ***Explain for using case 1 (number 3)
//1. The "Empty Theater" Problem
//The .share() operator uses refCount(1). This means:
//Start: When the 1st subscriber (phuc) joins, the movie starts.
//Stop: When the last subscriber leaves, the movie stops immediately.
//
//2. The Timeline Breakdown
//T=2s: phuc subscribes. refCount becomes 1. The movie starts at Scene 1.
//T=5s: phuc has received 3 scenes (1, 2, and 3).
//Because phuc had .take(3), "phuc" sends a cancel signal to the upstream.
//Since phuc was the only one watching, the refCount drops to 0.
//The Movie Stops! The generator is shut down to save resources.
//T=6s: heo arrives at the theater.
//Because the previous movie was cancelled, heo is
// now technically the "new" first subscriber.
//The refCount goes from 0 back to 1.
//A new movie starts! heo will likely see Scene 1 again.

public class Lec02HoldPublisher {
    private static final Logger log = LoggerFactory.getLogger(Lec02HoldPublisher.class);

    public static void main(String[] args) {
        var movieFlux = movieStream()
                .share(); //publish().refCount(1)

        // Note: change number 6
        // when case 1: number 3 take emit completed signal.
        // when case 2: number 6 don't take emit completed signal.
        Util.sleepSecondDuration(2);
        log.info("phuc start joining");
        movieFlux
                .take(7)
                .subscribe(Util.subscriber("phuc"));


        Util.sleepSecondDuration(4);
        log.info("heo start joining");
        movieFlux
                .take(4)
                .subscribe(Util.subscriber("heo"));

        Util.sleepSecondDuration(15);
    }

    private static Flux<String> movieStream() {
        return Flux.generate(
                () -> {
                    log.info("received the request {}", Thread.currentThread().getName());
                    return 1;
                },
                (state, sink) -> {
                    var scene = "movie scene " + state;
                    log.info("playing {}", scene);
                    sink.next(scene);
                    return ++state;
                }
        )
                .take(10)
                .delayElements(Duration.ofSeconds(1))
                .cast(String.class);
    }
}


//Note:
//1. The Role of the Scheduler
//When you use delayElements, Reactor stops using the thread
// that initiated the call (the main thread) and hands the
// task over to a Scheduler. By default, this is
// Schedulers.parallel().
//The Thread Pool: This scheduler maintains a pool of "Worker" threads.
// Usually, it creates exactly as many threads as you have CPU cores
// (e.g., 8 cores = 8 threads).
//The Task Queue: Each delayed "scene" becomes a task.
// When the 1-second timer expires, the scheduler picks
// an available thread from the pool to "push" that scene
// to the subscriber.
//
//2. Why you see Parallelism in your Logs
//Parallelism is occurring because you have two independent subscriptions
// (phuc and heo) running at the same time.
//
//The Timeline View:
//Thread A (e.g., parallel-1) is busy calculating and delivering "Scene 4" for phuc.
//Thread B (e.g., parallel-2) is simultaneously busy delivering "Scene 1" for heo.
//
//Because these two tasks are assigned to different threads,
// the OS can schedule them onto different physical CPU cores.
// This means the instructions for "phuc" and the instructions
// for "heo" are being executed at the exact same nanosecond

