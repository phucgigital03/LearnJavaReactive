package com.phucnguyen.section2;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;


public class Lec10MonoDefer {
    private static final Logger log = LoggerFactory.getLogger(Lec10MonoDefer.class);

    public static void main(String[] args) {
        System.out.println("Lec10MonoDefer");
        var nums = List.of(1, 2, 3, 4);

//      Demo1: not using Mono.Defer
//        createMonoPublisher(nums);

//      Demo2: using Mono.Defer
        Mono.defer(()-> createMonoPublisher(nums));
//            .subscribe(Util.subscriber());
    }


//  create Publisher
    private static Mono<Integer> createMonoPublisher(List<Integer> nums){
        Util.sleepSecondDuration(3);
        log.info("createMonoPublisher");
        return Mono.fromSupplier(() -> sum(nums));
    }

//  business-logic execution
    private static int sum(List<Integer> list) {
        log.info("finding sum of list : {}",  list);
        Util.sleepSecondDuration(3);
        return list.stream()
                .mapToInt(a -> a)
                .sum()
                ;
    }

}
