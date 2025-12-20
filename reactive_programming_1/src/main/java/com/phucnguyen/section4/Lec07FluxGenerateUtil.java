package com.phucnguyen.section4;

import com.phucnguyen.common.Util;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateUtil {

    public static void main(String[] args) {
//        demo1();

        demo2();
    }

    private static void demo1(){
        Flux.generate(synchronousSink -> {
            var country = Util.getFaker().country().name();
            synchronousSink.next(country);

            if(country.equalsIgnoreCase("canada")){
                synchronousSink.complete();
            }
        })
                .subscribe(Util.subscriber());
    }

    private static void demo2(){
        Flux.<String>generate(synchronousSink ->{
            var country = Util.getFaker().country().name();
            synchronousSink.next(country);
        })
                .takeUntil(c -> c.equalsIgnoreCase("canada"))
                .subscribe(Util.subscriber());
    }

}
