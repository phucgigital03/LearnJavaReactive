package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import com.phucnguyen.section9.applications.PaymentService;
import com.phucnguyen.section9.applications.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
 */
public class Lec09MonoFlatMap {
    private static final Logger log = LoggerFactory.getLogger(Lec09MonoFlatMap.class);

    public static void main(String[] args) {
//        Mono<Mono<Integer>> mike = UserService.getUserId("mike")
//                .map(userId -> PaymentService.getUserBalance(userId));

//       Demo 1
//        UserService.getUserId("mike")
//                .map(userId -> PaymentService.getUserBalance(userId))
//                .subscribe(Util.subscriber()); //received MonoSupplier it doesn't still subscribe

//        Demo 2
//        UserService.getUserId("mike")
//                .map(userId -> "hello " + userId + "!")
//                .subscribe(Util.subscriber()); //map just for in-memory computing

//        Demo 3
        /*
            We have username.
            Get user account balance
         */
        Mono<Integer> mike = UserService.getUserId("mike")
                .flatMap(userId -> {
                    return PaymentService.getUserBalance(userId);
                });
        mike
                .subscribe(Util.subscriber());



    }

}
