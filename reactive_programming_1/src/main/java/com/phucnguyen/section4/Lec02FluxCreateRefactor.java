package com.phucnguyen.section4;

import com.phucnguyen.common.Util;
import com.phucnguyen.section4.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class Lec02FluxCreateRefactor {
    public static void main(String[] args) {
        System.out.println("Lec02FluxCreateRefactor");

        var generator = new NameGenerator();
        var fluxSink = Flux.create(generator);

        fluxSink
                .subscribe(Util.subscriber("sub1"));

        fluxSink
                .subscribe(Util.subscriber("sub2"));

        for(int i = 0; i < 10; i++) {
            generator.generateName();
        }
    }
}

//***Note:
//Why "sub1" is getting ignored
//When you use Flux.create(generator), Project Reactor calls the accept(FluxSink<String> sink) method every time a new subscription occurs.
//Here is the step-by-step timeline of your code:
//sub1 subscribes: * Reactor creates a sink for sub1.
//It calls generator.accept(sinkForSub1).
//Your code does: this.sink = sinkForSub1;.
//sub2 subscribes: * Reactor creates a new, separate sink for sub2.
//It calls generator.accept(sinkForSub2).
//Your code does: this.sink = sinkForSub2;. (The reference to sub1's sink is now gone/overwritten!)
//The Loop runs:
//You call generator.generateName().
//The method uses this.sink.next(...). Since this.sink currently points to the last person who subscribed (sub2), only sub2 gets the data.
