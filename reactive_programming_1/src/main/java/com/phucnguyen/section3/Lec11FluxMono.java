package com.phucnguyen.section3;

import com.phucnguyen.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec11FluxMono {
    private static final Logger log = LoggerFactory.getLogger(Lec11FluxMono.class);


    public static void main(String[] args) {
//      convert mono to flux
        var fluxConversion = getFlux(3);
        runGetUser(fluxConversion);

//      convert flux to mono
        var flux = Flux.fromIterable(List.of(1,2,3));
        Mono.from(flux)
                .subscribe(Util.subscriber());

    }

    private static Flux<String> getFlux(int userId) {
        var mono = getUserById(userId);
        return Flux.from(mono);
    }

    public static Mono<String> getUserById(int userId){
        return switch (userId){
            case 1 -> Mono.just("User1");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("Invalid input"));
        };
    }

    private static void runGetUser(Flux<String> flux) {
        flux
                .subscribe(Util.subscriber());
    }
}
