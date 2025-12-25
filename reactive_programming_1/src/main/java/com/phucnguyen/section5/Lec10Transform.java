package com.phucnguyen.section5;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Lec10Transform {
    private static final Logger log = LoggerFactory.getLogger(Lec10Transform.class);
//    1. What is a Record?
//    Normally, if you want a Person class with a name and age, you would have to write fields, a constructor, getters, equals(), hashCode(), and toString().
//    With a record, you do it in one line:
        //    Java
        //    public record Person(String name, int age) {}
//    The Java compiler automatically generates:
//    Fields: private final String name and private final int age.
//    Constructor: A "canonical" constructor that assigns these fields.
//    Getters: Methods named name() and age() (note: no "get" prefix).
//    Standard Methods: Robust implementations of equals(), hashCode(), and toString().

//    2. When to use a Record?
//    DTOs (Data Transfer Objects): Perfect for moving data between layers or API responses.
//    API Responses: Ideal for modeling the JSON structure of a REST API.
//    Query Results: Use them to store specific rows fetched from a database.
//    Map Keys: Because they are immutable and have a built-in hashCode(), they are excellent as keys in a HashMap.
    record Customer(int id, String name) {}
    record PurchaseOrder(String productName, int price, int quantity) {}

    public static void main(String[] args) {
        var isDebug = false;
        getCustomers()
                .transform(isDebug ?  addDebugger() : Function.identity())
                .subscribe();


        getPurchaseOrders()
                .transform(addDebugger())
                .subscribe();

    }

    public static Flux<Customer> getCustomers() {
        return Flux.range(1, 3)
                .map(i ->
                        new Customer(i, Util.getFaker().name().fullName())
                );
    }

//    .map with List and Flux is the same
//    that it insert item into List or Flux
    public static Flux<PurchaseOrder> getPurchaseOrders() {
        return Flux.range(1, 5)
                .map(i ->
                        new PurchaseOrder(Util.getFaker().commerce().productName(), i , i * 10)
                );
    }

    private static <T> UnaryOperator<Flux<T>> addDebugger(){
        return flux -> flux
                .doOnNext(i -> log.info("received: {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(err -> log.error("error", err));
    }
}
