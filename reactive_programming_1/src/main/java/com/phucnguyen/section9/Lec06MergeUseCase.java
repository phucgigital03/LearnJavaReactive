package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import com.phucnguyen.section9.helper.Kayak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec06MergeUseCase {
    private static final Logger log = LoggerFactory.getLogger(Lec06MergeUseCase.class);

    public static void main(String[] args) {

        Kayak.getFlights()
                        .subscribe(Util.subscriber());

        Util.sleepSecondDuration(3);
    }
}
