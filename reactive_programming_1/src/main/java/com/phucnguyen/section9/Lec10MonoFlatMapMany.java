package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import com.phucnguyen.section9.applications.Order;
import com.phucnguyen.section9.applications.OrderService;
import com.phucnguyen.section9.applications.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
    Mono is supposed to be 1 item - what if the flatMap returns multiple items!?
 */
public class Lec10MonoFlatMapMany {
    private static final Logger log = LoggerFactory.getLogger(Lec10MonoFlatMapMany.class);

    public static void main(String[] args) {
        /*
            We have username
            get all user orders!
         */

//      Demo 1
//        Mono<Flux<Order>> mike = UserService.getUserId("mike")
//                .map(userId -> OrderService.getUserOrders(userId));

//      Demo 2
//        UserService.getUserId("mike")
//                .flatMap(userId -> OrderService.getUserOrders(userId));
//      When you call .flatMap() on a Mono, the rule is strict: The transformation function MUST return another Mono.


//      Demo 3
        Flux<Order> mike1 = UserService.getUserId("mike")
                .flatMapMany(userId -> OrderService.getUserOrders(userId));

        mike1.subscribe(Util.subscriber());


        Util.sleepSecondDuration(3);
    }
}
