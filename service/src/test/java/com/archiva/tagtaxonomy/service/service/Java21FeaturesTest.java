package com.archiva.tagtaxonomy.service.service;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Java21FeaturesTest {

    @Test
    void virtualThreadsRunConcurrentTasks() throws Exception {
        List<String> inputs = List.of("alpha", "beta", "gamma");

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var results = inputs.stream()
                    .map(input -> executor.submit(() -> {
                        if (input.startsWith("a")) {
                            return input.toUpperCase();
                        }
                        return input;
                    }))
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();

            assertEquals(List.of("ALPHA", "beta", "gamma"), results);
        }
    }
}
