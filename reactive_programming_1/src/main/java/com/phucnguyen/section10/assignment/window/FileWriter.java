package com.phucnguyen.section10.assignment.window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriter {
    private static final Logger log = LoggerFactory.getLogger(FileWriter.class);
    private final Path path;
//    Note:
//    When to use BufferedWriter?
//    You should use it almost 99% of the time when you are writing text files (logs, CSVs, JSON, reports).
    //    Bad (Slow): FileWriter alone.
    //    Good (Fast): BufferedWriter wrapping a FileWriter.
    //    Best (Modern Java 7+): Files.newBufferedWriter(Path path) (This is a newer utility method that creates a BufferedWriter for you cleanly).
    private BufferedWriter writer;

    private FileWriter(Path path) {
        this.path = path;
    }

    private void createFile() {
        try {
            this.writer = Files.newBufferedWriter(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeFile() {
        try {
            this.writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // just for demo
    private void write(String content) {
        try {
            this.writer.write(content);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Mono<Void> create(Flux<String> flux, Path path) {
        var writer = new FileWriter(path);
        log.info("start creating file");
        return flux.doOnNext(event -> writer.write(event))
                .doFirst(() -> {
                    log.info("create file {}", path);
                    writer.createFile();
                })
                .doFinally(s -> {
                    log.info("close file {}", path);
                    writer.closeFile();
                })
                .then();
    }

}
