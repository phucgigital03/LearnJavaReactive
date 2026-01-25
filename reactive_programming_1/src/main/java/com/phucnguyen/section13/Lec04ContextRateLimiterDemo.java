package com.phucnguyen.section13;

import com.phucnguyen.common.Util;
import com.phucnguyen.section13.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.util.context.Context;

public class Lec04ContextRateLimiterDemo {
    private static final Logger log = LoggerFactory.getLogger(Lec04ContextRateLimiterDemo.class);

    public static void main(String[] args) {
        demo1();

    }

    public static void demo1(){
        var client = new ExternalServiceClient();
        for (int i = 0; i < 20; i++) {
            client.getBook()
                    .contextWrite(Context.of("user", "mike"))
                    .subscribe(Util.subscriber());
            Util.sleepSecondDuration(1);
        }

        Util.sleepSecondDuration(5);
    }
}
