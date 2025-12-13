package com.phucnguyen.section2.client;

import com.phucnguyen.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {

//  Note: when the method is invoked, we create a Mono which is
//  capable of sending a request. But the actual HTTP request is sent,
//  only when it is subscribed
    public Mono<String> getProductName(int productId) {
        return this.httpClient.get()
                .uri("/demo01/product/" + productId)
                .responseContent()
                .asString()
                .next();

    }

}
