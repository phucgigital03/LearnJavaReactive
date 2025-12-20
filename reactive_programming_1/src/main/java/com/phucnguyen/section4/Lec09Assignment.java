package com.phucnguyen.section4;

import com.phucnguyen.common.Util;
import com.phucnguyen.section1.subscriber.SubscriberImpl;
import com.phucnguyen.section4.assignment.FileReaderServiceImpl;
import reactor.core.publisher.Flux;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Lec09Assignment {
    private static final Path baseDir = Paths.get("reactive_programming_1", "src", "main", "resources", "section4");

    public static void main(String[] args) {
        Path file = baseDir.resolve("file.txt").normalize();
        var fileReader = new FileReaderServiceImpl();
//        var subscriber = new SubscriberImpl();

        fileReader.read(file)
                .take(100)
                .subscribe(Util.subscriber());

//        subscriber.getSubscription().request(2);
//        subscriber.getSubscription().request(2);
//        subscriber.getSubscription().cancel();
//        subscriber.getSubscription().request(2);
//        subscriber.getSubscription().request(2);



    }

}
