package com.phucnguyen.section2;

import com.phucnguyen.common.Util;
import com.phucnguyen.section2.assignment.FileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Lec12Assignment {
    private static final Logger log = LoggerFactory.getLogger(Lec12Assignment.class);

    public static void main(String[] args) {
        System.out.println("Lec12Assignment");
//        String currentDir = System.getProperty("user.dir");
//        System.out.println("Current dir: " + currentDir);


        var fileServiceImpl = new FileServiceImpl();

        fileServiceImpl.writeFile("file.txt", "This is the file content")
                .subscribe(Util.subscriber());

        fileServiceImpl.readFile("file.txt")
                        .subscribe(Util.subscriber());

//        fileServiceImpl.deleteFile("file.txt")
//                .subscribe(Util.subscriber());

        Util.sleepSecondDuration(4);

    }

}
