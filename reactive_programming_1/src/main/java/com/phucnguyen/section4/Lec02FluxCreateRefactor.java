package com.phucnguyen.section4;

import com.phucnguyen.common.Util;
import com.phucnguyen.section4.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class Lec02FluxCreateRefactor {
    public static void main(String[] args) {
        System.out.println("Lec02FluxCreateRefactor");

        var generator = new NameGenerator();
        var fluxsink = Flux.create(generator);

        fluxsink
                .subscribe(Util.subscriber());

        generator.generateName();
        generator.generateName();
        generator.generateName();
    }
}
