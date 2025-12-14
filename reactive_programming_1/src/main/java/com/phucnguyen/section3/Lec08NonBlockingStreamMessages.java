package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import com.phucnguyen.section3.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec08NonBlockingStreamMessages {
    private static final Logger log = LoggerFactory.getLogger(Lec08NonBlockingStreamMessages.class);

    public static void main(String[] args) {
        var externalService = new ExternalServiceClient();

        externalService.getProductNameFlux()
                .subscribe(Util.subscriber());

        Util.sleepSecondDuration(6);
    }
    
}
