package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import com.phucnguyen.section9.assignment.ExternalClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    Ensure that the external service is up and running!
 */
public class Lec12FluxFlatMapAssignment {
    private static final Logger log = LoggerFactory.getLogger(Lec12FluxFlatMapAssignment.class);

    public static void main(String[] args) {
        var client = new ExternalClientService();

//        Flux.range(1, 9)
//                .map(productId -> client.getProduct(productId))
//                        .subscribe(Util.subscriber());

        Flux.range(1, 9)
                .flatMap(productId -> client.getProduct(productId), 3)
                .subscribe(Util.subscriber("sub1"));

        Util.sleepSecondDuration(10);
    }
}
