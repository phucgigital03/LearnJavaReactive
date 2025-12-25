package com.phucnguyen.section11.client;

// just for demo
public class ServerError extends RuntimeException {

    public ServerError() {
        super("server error");
    }

}