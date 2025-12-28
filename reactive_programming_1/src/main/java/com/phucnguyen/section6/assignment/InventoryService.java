package com.phucnguyen.section6.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class InventoryService implements OrderProcessor {
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private final Map<String, Integer> db = new HashMap<>();

    @Override
    public void consume(Order order) {
        var currentInventory = db.getOrDefault(order.category(), 500);
        var updatedInventory = currentInventory - order.quantity();
        db.put(order.category(), updatedInventory);
    }

    @Override
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> {
//                    log.info("inventory start joining");
                    return this.db.toString();
                });
    }

}
