package com.phucnguyen.section5;

import com.phucnguyen.common.Util;
import com.phucnguyen.section5.assignment.ExternalServiceClient;

public class Lec11Assignment {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

//        1. The Event Loop Model
//        Netty uses an Event Loop. Instead of having hundreds of threads waiting for database or network responses,
//        it has a very small number of worker threads (usually equal to the number of CPU cores you have).
//        Non-blocking: When your code says "fetch this product name,"the thread doesn't sit there waiting for the server to answer.
//        The "Register and Leave" approach: The phuc-nio-1 thread sends the request to the network card,
//        registers a "callback" (an instruction on what to do when data arrives),
//        and immediately moves on to help another user.
//
//        2. Do they run in parallel?
//        Yes and No, depending on where you look:
//        ***Parallel across threads: If you have multiple worker threads
//        (e.g., phuc-nio-1, phuc-nio-2), they run in parallel on different CPU cores.
//        ***Concurrency within one thread: A single thread like phuc-nio-1
//        can handle thousands of concurrent requests. It switches between them
//        so fast that it looks like they are happening at the same time,
//        but it is actually just handling "events" (data arrived, timeout reached, connection closed)
//        as they happen.
//
//        3. Why your logs show phuc-nio-1 for everything
//        In your specific log, you see phuc-nio-1 repeatedly. This happens for two reasons:
//        Low Load: You are only running 4 requests in a loop.
//        One single thread is more than fast enough to handle the events for all 4 requests.
//        Affinity: Reactor often tries to keep related work on the same thread
//        to keep the CPU cache "warm" and improve performance.
//
//        4. Visualizing your specific execution
//        Imagine phuc-nio-1 is a single waiter in a restaurant:
    //        Main Thread (The Boss): Orders the waiter to handle 4 tables (your loop).
    //        phuc-nio-1 (The Waiter):
    //        Goes to Table 1, sends the order to the kitchen (Primary Request), and leaves.
    //        Immediately goes to Table 2, sends the order, and leaves.
    //        Suddenly, the Kitchen says "Order 2 is out!" (Data arrived). The waiter picks it up and serves it (received product-2).
    //        The waiter notices the "Timer" for Table 3 went off (Timeout). He goes to the kitchen, gets the alternative meal,
    //        and serves it (received product-3 from timeout-fallback).

//        Example 2:
        for(int i = 1; i < 5; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }


//        Note: Explain for example 2 below
//        1. Why do you see "main" so many times?In Reactive Programming,
//        there is a difference between Assembly Time (building the pipeline) and Execution Time (running the data).
//        Assembly on Main Thread: When you call client.getProductName(i), you are running a Java method
//        on the main thread.
//        Immediate Method Execution: Inside getProductName(int productId), you are calling the helper
//        method getProductName(String path) three times for every single loop iteration:Once for the primary request
//        .Once for the .timeout() fallback.
//        Once for the .switchIfEmpty() fallback.
//        The "Logging" Trap: Your logger is inside the method body, before the Reactive Stream is actually executed.
//        Because you are creating those three Mono objects immediately to pass them as arguments to timeout and switchIfEmpty,
//        the logger.info runs immediately on the thread calling the method (the main thread).
//        The Math: * You have a loop of 4 ($i=1$ to $4$).Each loop triggers 3 log statements (Primary + Timeout Fallback + Empty Fallback).
//        $4 \times 3 = 12$ logs on the main thread. This matches your log output!

//        Example 2:
//        for(int i = 1; i < 5; i++) {
//            client.getProductName(i);
//        }

        Util.sleepSecondDuration(5);
    }

}


//Note: Entire flow of main thread
//1. The Execution Timeline
//Since the sleep is outside the loop, the main thread acts like a "Machine Gun":
//        0ms: The main thread enters the loop. It calls getProductName(1) through 4 as fast as possible.
//        1ms - 5ms: For each call, the main thread "subscribes." This tells the phuc-nio worker threads: "Go fetch these 4 products."
//        6ms: The main thread finishes the loop and hits Util.sleepSeconds(5). The main thread is now frozen/sleeping.
//        100ms - 2000ms: While main is sleeping, the phuc-nio threads are busy in the background. They receive the data from the server, handle the timeouts,
//        and print the logs (e.g., received product-1).
//        5000ms: The main thread wakes up and finishes the program.
//
//2. Why this is "Good"
//In a traditional (blocking) program, you would have to wait for Product 1
//before starting Product 2. The total time would be: Time(P1) + Time(P2) + Time(P3) + Time(P4).
//In your Reactive code: The main thread spends almost zero time starting the requests.
//All 4 requests are running at the same time in the background. The total time is just
//the time of the slowest request.