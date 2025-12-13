package com.phucnguyen.section2;

import com.phucnguyen.common.Util;
import com.phucnguyen.section2.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Lec11NonBlockingIO {
    private static final Logger log = LoggerFactory.getLogger(Lec11NonBlockingIO.class);

    public static void main(String[] args) {
        System.out.println("Lec11NonBlockingIO");

        var externalService = new ExternalServiceClient();
        log.info("starting request");
//        Demo1: Blocking (just one thread main doing)
//        for(int i = 1; i <= 100; i++) {
//            var namePro = externalService.getProductName(i)
//                    .block();
//
//            log.info("namePro: " + namePro);
//        }

//        Demo2: Non-Blocking (two threads working)
        for(int i = 1; i <= 100; i++) {
            externalService.getProductName(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSecondDuration(2);
    }

}
