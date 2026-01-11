package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import com.phucnguyen.section9.applications.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;


/*
    Get all users and build 1 object as shown here.
    record UserInformation(Integer userId, String username, Integer balance, List<Order> orders) {}
*/
public class Lec16Assignment {
    private static final Logger log = LoggerFactory.getLogger(Lec16Assignment.class);
    record UserInfo(Integer userId, String username, Integer balance, List<Order> orders) {}

    public static void main(String[] args) {

        UserService.getAllUsers()
                .flatMap(user -> helper(user))
                .subscribe(Util.subscriber());

        Util.sleepSecondDuration(5);
    }

    private static Mono<UserInfo> helper(User user){
        return Mono.zip(
                PaymentService.getUserBalance(user.id()), // PaymentService and OrderService have final result and then Mono.zip has tuple
                OrderService.getUserOrders(user.id()).collectList()
        )
                .map(t -> new UserInfo(user.id(), user.username(), t.getT1(), t.getT2()));
    }
}
