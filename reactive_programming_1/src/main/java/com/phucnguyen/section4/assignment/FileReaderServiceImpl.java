package com.phucnguyen.section4.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

/*
    - do the work only when it is subscribed
    - do the work based on the demand
    - stop producing when subscriber cancels
    - produce only the requested items
    - file should be closed once done
 */
public class FileReaderServiceImpl implements FileReaderService {
    private static final Logger log = LoggerFactory.getLogger(FileReaderServiceImpl.class);


    @Override
    public Flux<String> read(Path path) {
        AtomicInteger counter = new AtomicInteger(0);
        return Flux.generate(
                () -> {
                    System.out.println("open file");
                    return Files.newBufferedReader(path);
                },
                (reader, sink)->{
                    try {
                        String line = reader.readLine();
                        if (line != null) {
                            log.info("Read line: " + counter.incrementAndGet());
                            sink.next(line);
                        }else {
                            sink.complete();
                        }
                    } catch (IOException e) {
                        sink.error(e);
                    }
                    return reader;
                },
                (reader)->{
                    try {
                        System.out.println("close file");
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

    }
}