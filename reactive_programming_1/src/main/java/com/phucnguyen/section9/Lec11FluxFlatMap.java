package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import com.phucnguyen.section9.applications.Order;
import com.phucnguyen.section9.applications.OrderService;
import com.phucnguyen.section9.applications.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
 */
public class Lec11FluxFlatMap {
    private static final Logger log = LoggerFactory.getLogger(Lec11FluxFlatMap.class);

    public static void main(String[] args) {
        /*
            Get all the orders from order service!
         */
//      Way 1
//        Flux<Order> orderFlux = UserService.getAllUsers()
//                .map(user -> user.id())
//                .flatMap(userId -> {
//                    log.info("user id is {}", userId);
//                    return OrderService.getUserOrders(userId);
//                });
//
//        orderFlux.subscribe(Util.subscriber());

//      Way 2
        Flux<Order> orderFlux2 = UserService.getAllUsers()
                .flatMap(user -> {
                    log.info("user id is {}", user.id());
                    return OrderService.getUserOrders(user.id());
                });

        orderFlux2.subscribe(Util.subscriber());

        Util.sleepSecondDuration(5);
    }
}
