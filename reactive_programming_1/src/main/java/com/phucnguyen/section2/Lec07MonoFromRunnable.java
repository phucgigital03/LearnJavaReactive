package com.phucnguyen.section2;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec07MonoFromRunnable {
    private static final Logger log = LoggerFactory.getLogger(Lec07MonoFromRunnable.class);

    public static void main(String[] args) {
        System.out.println("Lec07MonoFromRunnable");

        getProductWithEmpty(2);
//                .subscribe(Util.subscriber());

        getProduct(2);
//                .subscribe(Util.subscriber());

//        *** Summary:
//        1. Use Mono.empty() when you have nothing to give back.
//        2. Use Mono.fromRunnable() when you have work to do that doesn't return a value.
    }

    private static Mono<String> getProductWithEmpty(int productId) {
        if(productId == 1){
            return Mono.fromSupplier(() -> "product with id " + productId);
        }
        return Mono.empty();
    }

    private static Mono<String> getProduct(int productId) {
        if(productId == 1){
            return Mono.fromSupplier(() -> "product with id " + productId);
        }
        return Mono.fromRunnable(() -> log.info("product not found"));
    }

}
