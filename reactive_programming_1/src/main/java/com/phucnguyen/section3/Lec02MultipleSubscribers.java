package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec02MultipleSubscribers {
    private static final Logger log = LoggerFactory.getLogger(Lec02MultipleSubscribers.class);


    public static void main(String[] args) {

        var flux = Flux.just(1,2,3,4,5,6);

        flux
                .subscribe(Util.subscriber("sub1"));

        flux
                .filter(i -> i % 2 == 0)
                .map(i -> i + "a")
                .subscribe(Util.subscriber("sub2"));

        flux
                .filter(i -> i > 7)
                .subscribe(Util.subscriber("sub3"));
    }

}
