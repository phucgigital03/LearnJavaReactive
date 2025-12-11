package com.phucnguyen.section2;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

public class Lec05MonoFromSupplier {
    private static final Logger log = LoggerFactory.getLogger(Lec05MonoFromSupplier.class);

    public static void main(String[] args) {
        System.out.println("Lec05");

        var nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Mono.just(sum(nums));

//      To avoid compute intensive data
        Mono.fromSupplier(() -> sum(nums));

//        Mono.fromSupplier(() -> sum(nums))
//                .subscribe(Util.subscriber());
    }

    private static int sum(List<Integer> list) {
        log.info("finding sum of list");
        return list.stream()
                .mapToInt(a -> a)
                .sum()
                ;
    }
}
