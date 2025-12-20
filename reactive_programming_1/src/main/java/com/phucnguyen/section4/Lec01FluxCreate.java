package com.phucnguyen.section4;

import com.phucnguyen.common.Util;
import com.phucnguyen.section1.subscriber.SubscriberImpl;
import reactor.core.publisher.Flux;

public class Lec01FluxCreate {
    public static void main(String[] args) {
        System.out.println("Lec01FluxCreate");

//        var subscriber = new SubscriberImpl();

        Flux.<String>create(fluxSink -> {
            String country;
            do{
                country = Util.getFaker().country().name();
                fluxSink.next(country);
            }while (!country.equalsIgnoreCase("canada"));
            fluxSink.complete();
        })
                .subscribe(Util.subscriber());

    }
}
