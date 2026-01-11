package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    To collect the items received via Flux. Assuming we will have finite items!
    Note: **collecting everything internally all the items when this flux emits complete signal right that time okay.
 */
public class Lec14CollectList {
    private static final Logger log = LoggerFactory.getLogger(Lec14CollectList.class);

    public static void main(String[] args) {

        Flux.range(1, 9)
                .concatWith(Flux.error(new RuntimeException("oops")))
                .collectList()
                .subscribe(Util.subscriber());

    }
}
