package com.phucnguyen.section2;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec04MonoEmptyError {
    private static final Logger log = LoggerFactory.getLogger(Lec04MonoEmptyError.class);

    public static void main(String[] args) {

//        getUserById(1)
//                .subscribe(Util.subscriber());
//        getUserById(2)
//                .subscribe(Util.subscriber());
//        getUserById(3)
//                .subscribe(Util.subscriber());

//      onErrorDropped
        getUserById(3)
                .subscribe(
                        i -> System.out.println(i),
                        err -> System.out.println(err.getMessage())
                );
    }

    public static Mono<String> getUserById(int userId){
        return switch (userId){
            case 1 -> Mono.just("User1");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("Invalid input"));
        };
    }
}
