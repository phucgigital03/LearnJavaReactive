package com.phucnguyen.section5;

import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class LecOption {
    public static void main(String[] args) {
//      ***Note: Revise stream map vs mono map, to know how we use flatMap
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list =  list.stream()
                .map(x -> x * 2).toList();
        list.forEach(System.out::println);


        Mono<Integer> mono = Mono.just(1);
        Mono<Mono<Integer>> map = mono.map(x -> Mono.just(x * 2));
        Mono<Integer> map2 = mono.map(x -> 2);
    }
}
