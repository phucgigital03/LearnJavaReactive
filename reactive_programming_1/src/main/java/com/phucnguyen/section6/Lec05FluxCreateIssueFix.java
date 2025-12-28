package com.phucnguyen.section6;


import com.phucnguyen.common.Util;
import com.phucnguyen.section4.helper.NameGenerator;
import reactor.core.publisher.Flux;

/*
    To fix the issue we faced in sec04/Lec02FluxCreateRefactor
*/
public class Lec05FluxCreateIssueFix {

    public static void main(String[] args) {

        var generator = new NameGenerator();
//        var fluxSink = Flux.create(generator);

        var fluxSink = Flux.create(generator)
                .share();

        fluxSink
                .subscribe(Util.subscriber("sub1"));

        generator.generateName();
        generator.completeSink();

        fluxSink
                .subscribe(Util.subscriber("sub2"));

        fluxSink
                .subscribe(Util.subscriber("sub3"));

        for(int i = 0; i < 10; i++) {
            generator.generateName(); // Not calling next() immediately like Flux.generate()
        }
    }
}
