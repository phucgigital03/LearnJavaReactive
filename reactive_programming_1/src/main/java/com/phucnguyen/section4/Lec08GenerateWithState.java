package com.phucnguyen.section4;

import com.phucnguyen.common.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec08GenerateWithState {

    public static void main(String[] args) {

//        demoWithProblem();

        demoSolveProblem();
    }

    private static void demoWithProblem(){
        AtomicInteger count = new AtomicInteger(0);
        Flux.generate(synchronousSink -> {
                    var country = Util.getFaker().country().name();
                    synchronousSink.next(country);

                    count.incrementAndGet();
                    if(count.get() == 10 || country.equalsIgnoreCase("canada")){
                        synchronousSink.complete();
                    }
                })
                .subscribe(Util.subscriber());

//      If someone else does count.incrementAndGet(); outside Flux.generate.
//      This is a problem.
    }

    private static void demoSolveProblem(){
        Flux.generate(
                () -> 1, // invoked once when init state
                (counter, sink)->{
                    var country = Util.getFaker().country().name();
                    sink.next(country);
                    if(counter == 10 || country.equalsIgnoreCase("canada")){
                        sink.complete();
                        return counter;
                    }
                    return ++counter;
                }
//                have one argument to be invoked once when close connection.
        )
                .subscribe(Util.subscriber());
    }


}
