package com.phucnguyen.common;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;


public abstract class AbstractHttpClient {

    private static final String BASE_URL = "http://localhost:7070";
    protected final HttpClient httpClient;

    public AbstractHttpClient() {
        var loopResource = LoopResources.create("phuc",1, true);
        this.httpClient = HttpClient.create()
                .runOn(loopResource)
                .baseUrl(BASE_URL)
        ;
    }

}
