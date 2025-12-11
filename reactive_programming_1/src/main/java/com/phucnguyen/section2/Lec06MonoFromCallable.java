package com.phucnguyen.section2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec06MonoFromCallable {
    private static final Logger log = LoggerFactory.getLogger(Lec06MonoFromCallable.class);

    public static void main(String[] args) {
        System.out.println("Lec06MonoFromCallable");

        var nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Mono.fromSupplier(() -> {
            try {
                return sum(nums);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        Mono.fromCallable(() -> sum(nums));
//        The main difference between Supplier and Callable is Exception Handling.
//        1. Supplier is for simple data generation (cannot throw checked exceptions).
//        2. Callable is for tasks that might fail (can throw checked exceptions).

    }

    private static int sum(List<Integer> list) throws Exception {
        log.info("finding sum of list");
        return list.stream()
                .mapToInt(a -> a)
                .sum()
                ;
    }

}
