package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import com.phucnguyen.section3.client.ExternalServiceClient;
import com.phucnguyen.section3.helper.StockSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec12Assignment {
    private static final Logger log = LoggerFactory.getLogger(Lec12Assignment.class);


    public static void main(String[] args) {
        var exService = new ExternalServiceClient();
        var subscriber = new StockSubscriber("");
        exService.getStockPriceFlux()
                .subscribe(subscriber);


        Util.sleepSecondDuration(20);
    }
    
}
