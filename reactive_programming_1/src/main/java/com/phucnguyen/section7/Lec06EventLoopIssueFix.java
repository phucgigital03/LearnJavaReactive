package com.phucnguyen.section7;

import com.phucnguyen.common.Util;
import com.phucnguyen.section7.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec06EventLoopIssueFix {
    private static final Logger log = LoggerFactory.getLogger(Lec06EventLoopIssueFix.class);

    public static void main(String[] args) {
        log.info("Start Lec06EventLoopIssueFix");
        var client = new ExternalServiceClient();

        for (int i = 1; i <= 10; i++) {
            client.getProductName(i)
                    .map(Lec06EventLoopIssueFix::process)
                    .subscribe(Util.subscriber()); // multiple subscribers,
            // so it run parallelly with boundedElastic pool
        }

        Util.sleepSecondDuration(20);
    }

    private static String process(String input){
        Util.sleepSecondDuration(1);
        return input + "-processed";
    }
}
