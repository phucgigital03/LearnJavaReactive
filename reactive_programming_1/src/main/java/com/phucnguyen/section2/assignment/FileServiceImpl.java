package com.phucnguyen.section2.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private final Path baseDir;

    public FileServiceImpl() {
        String currentDir = System.getProperty("user.dir");
        String moduleName = "reactive_programming_1"; // Matches your folder name exactly
        if (currentDir.endsWith(moduleName)) {
            // Case 1: You are running inside the module folder
            this.baseDir = Paths.get("src", "main", "resources", "section2");
            System.out.println("PATH CHECK 1: " + this.baseDir.toAbsolutePath());
        } else {
            // Case 2: You are running from the root "projectCode" folder
            this.baseDir = Paths.get(moduleName, "src", "main", "resources", "section2");
            System.out.println("PATH CHECK 2: " + this.baseDir.toAbsolutePath());
        }
    }
    @Override
    public Mono<String> readFile(String fileName) {
        return Mono.fromCallable(()->{
           Path file = baseDir.resolve(fileName).normalize();
           if(!file.startsWith(this.baseDir)){
               throw new IllegalArgumentException("Invalid file name");
           }

           return Files.readString(file, StandardCharsets.UTF_8);
        })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> writeFile(String fileName, String fileContent) {
        return Mono.fromRunnable(() -> {
            try {
                Path file = baseDir.resolve(fileName).normalize();
                if (!file.startsWith(baseDir)) throw new IllegalArgumentException("Invalid file path");
                // create base and parent dirs only when needed
                Files.createDirectories(file.getParent() != null ? file.getParent() : baseDir);
                Files.writeString(file, fileContent, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        })
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }

    @Override
    public Mono<Void> deleteFile(String fileName) {
        return Mono.fromRunnable(() -> {
                    try {
                        // 1. Resolve and Security Check (Consistent with other methods)
                        Path file = baseDir.resolve(fileName).normalize();
                        if (!file.startsWith(baseDir)) {
                            throw new IllegalArgumentException("Invalid file path");
                        }

                        // 2. The Blocking Delete Operation
                        // Files.delete() throws an exception if the file is missing.
                        // If you prefer no error when missing, use Files.deleteIfExists(file)
                        Files.delete(file);

                        logger.info("Deleted file successfully: {}", file);

                    } catch (IOException e) {
                        // Wrap checked exception into RuntimeException so Reactor handles it
                        throw new RuntimeException(e);
                    }
                })
                // 3. Offload blocking I/O to the proper thread pool
                .subscribeOn(Schedulers.boundedElastic())
                .then(); // Convert to Mono<Void> explicitly
    }
}
