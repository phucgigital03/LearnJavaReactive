package com.phucnguyen.section10;

import com.phucnguyen.common.Util;
import com.phucnguyen.section10.assignment.buffer.BookOrder;
import com.phucnguyen.section10.assignment.buffer.RevenueReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Lec01BufferAssignment {
    private static final Logger log = LoggerFactory.getLogger(Lec01BufferAssignment.class);

    public static void main(String[] args) {
        var allowedCategories = Set.of(
                "Science fiction",
                "Fantasy",
                "Suspense/Thriller"
        );
//        Way 1
        bookOrderStream()
                .buffer(Duration.ofSeconds(5))
                .map(bookOrderList -> createRevenueReport(bookOrderList, allowedCategories))
                .subscribe(Util.subscriber());

//      Way 2
//        orderStream()
//                .filter(o -> allowedCategories.contains(o.genre()))
//                .buffer(Duration.ofSeconds(5))
//                .map(bookOrderList -> generateReport(bookOrderList))
//                .subscribe(Util.subscriber());


        Util.sleepSecondDuration(20);
    }

//  Way 1
    private static Flux<BookOrder> bookOrderStream(){
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> BookOrder.create());
    }

    private static RevenueReport createRevenueReport(List<BookOrder> bookOrderList, Set<String> allowedCategories) {
        LocalTime localTime = LocalTime.now();
        Map<String, Integer> revenueMap = new HashMap<>();

        bookOrderList.forEach(bookOrder -> {
            if(allowedCategories.contains(bookOrder.genre())){
                var totalPrice = revenueMap.getOrDefault(bookOrder.genre(),  0) + bookOrder.price();
                revenueMap.put(bookOrder.genre(), totalPrice);
            }
        });
        return new RevenueReport(localTime, revenueMap);
    }

//  Way 2
    private static Flux<BookOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> BookOrder.create());
    }

    private static RevenueReport generateReport(List<BookOrder> orders) {
        var revenue = orders.stream()
                .collect(Collectors.groupingBy(
                        BookOrder::genre,
                        Collectors.summingInt(BookOrder::price)
                ));
        return new RevenueReport(LocalTime.now(), revenue);
    }

}
