package com.phucnguyen.section3.helper;

import com.phucnguyen.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

public class NameGenerator {

    public static List<String> generateList(){
        return IntStream.range(0, 10)
                .mapToObj(i -> generateString())
                .toList();
    }

    public static Flux<String> generateFlux(){
        return Flux.range(1,10)
                .map(i -> generateString());
    }

    private static String generateString(){
        Util.sleepSecondDuration(1);
        return Util.getFaker().name().fullName();
    }

}
