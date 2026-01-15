package com.phucnguyen.section10.assignment.groupby;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

// just for demo/assignment
// in the real life - we can have separate classes for every category
public class OrderProcessingService {
    private static final Logger log = LoggerFactory.getLogger(OrderProcessingService.class);
    private static final Map<String, UnaryOperator<Flux<PurchaseOrder>>> PROCESSOR_MAP = Map.of(
            "Kids", kidsProcessing(),
            "Automotive", automativeProcessing()
    );

    public static Predicate<PurchaseOrder> canProcess(){
        return po -> PROCESSOR_MAP.containsKey(po.category());
    }

    private static UnaryOperator<Flux<PurchaseOrder>> kidsProcessing(){
        log.info("Kids processing");
        return flux -> flux
                .flatMap(po -> getFreeKidsOrder(po).flux().startWith(po))
                ; //***
    }

    private static Mono<PurchaseOrder> getFreeKidsOrder(PurchaseOrder order) {
        return Mono.fromSupplier(() -> new PurchaseOrder(
                order.item() + "-FREE",
                order.category(),
                0
        ));
    }

    private static UnaryOperator<Flux<PurchaseOrder>> automativeProcessing(){
        log.info("Automative processing");
        return flux -> flux
                .map(po -> new PurchaseOrder(po.item(), po.category(), po.price() + 100));
    }

    public static UnaryOperator<Flux<PurchaseOrder>> getProcessor(String category){
        return PROCESSOR_MAP.get(category);
    }
}

//Note:
//Exception in thread "main" java.lang.ExceptionInInitializerError
//at com.phucnguyen.section10.Lec06GroupByAssignment.main(Lec06GroupByAssignment.java:20)
//Caused by: java.lang.NullPointerException: Cannot invoke "org.slf4j.Logger.info(String)" because "com.phucnguyen.section10.assignment.groupby.OrderProcessingService.log" is null
//at
//How to fix this bug:
//This error is happening because of the order in which you declared your static variables.
//In Java, static variables are initialized from top to bottom.

