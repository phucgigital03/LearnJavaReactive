package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import com.phucnguyen.section9.assignment.ExternalClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
    Ensure that the external service is up and running!
*/
public class Lec08ZipAssignment {
    private static final Logger log = LoggerFactory.getLogger(Lec08ZipAssignment.class);

    public static void main(String[] args) {
        var client = new ExternalClientService();

        for (int i = 1; i < 10; i++) {
            client.getProduct(i)
                    .subscribe(Util.subscriber("sub" + i));
        }

        Util.sleepSecondDuration(2);
    }
}
