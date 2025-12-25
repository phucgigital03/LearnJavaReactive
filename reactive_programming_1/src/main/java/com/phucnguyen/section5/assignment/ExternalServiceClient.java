package com.phucnguyen.section5.assignment;

import com.phucnguyen.common.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class ExternalServiceClient extends AbstractHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(ExternalServiceClient.class);

    public Mono<String> getProductName(int productId){
        var productServicePath = "/demo03/product/";
        var timeoutPath = "/demo03/timeout-fallback/product/";
        var emptyPath = "/demo03/empty-fallback/product/";

        return getProductName(productServicePath + productId)
                .timeout(Duration.ofSeconds(2), getProductName(timeoutPath  + productId))
                .switchIfEmpty(getProductName(emptyPath + productId));
    }

    private Mono<String> getProductName(String path) {
        logger.info("get ProductName with {}", Thread.currentThread().getName());
        return this.httpClient.get()
                .uri(path)
                .responseContent()
                .asString()
                .next();
    }

}
