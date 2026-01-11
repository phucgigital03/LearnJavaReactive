package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import com.phucnguyen.section9.assignment.ExternalClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec13ConcatMap {
    private static final Logger log = LoggerFactory.getLogger(Lec13ConcatMap.class);

    public static void main(String[] args) {
        var client = new ExternalClientService();


        Flux.range(1, 9)
                .concatMap(productId -> client.getProduct(productId), 3)
                .subscribe(Util.subscriber("sub1"));

        Util.sleepSecondDuration(10);
    }
}
