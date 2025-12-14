package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import com.phucnguyen.section3.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec07FluxVsList {
    private static final Logger log = LoggerFactory.getLogger(Lec07FluxVsList.class);


    public static void main(String[] args) {
//        traditional way
        var list = NameGenerator.generateList();
        list.forEach(System.out::println);


//        new way
        NameGenerator.generateFlux()
                .subscribe(Util.subscriber());
    }
    
}
