package com.phucnguyen.section9;

import com.phucnguyen.common.Util;
import com.phucnguyen.section9.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec02StartWithUseCase {
    private static final Logger log = LoggerFactory.getLogger(Lec02StartWithUseCase.class);

    public static void main(String[] args) {

        NameGenerator.generateName()
                .take(2)
                .subscribe(Util.subscriber("phuc1"));

        NameGenerator.generateName()
                .take(2)
                .subscribe(Util.subscriber("phuc2"));

        NameGenerator.generateName()
                .take(3)
                .subscribe(Util.subscriber("phuc3"));
    }
}
