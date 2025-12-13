package com.phucnguyen.section2.assignment;

import reactor.core.publisher.Mono;

public interface FileService {
//  read and return content
    Mono<String> readFile(String fileName);

//  create file and write content
    Mono<Void> writeFile(String fileName, String fileContent);

//  delete file
    Mono<Void> deleteFile(String fileName);
}
