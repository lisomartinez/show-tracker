package com.github.lmartinez84.showtracker.shared.infrastructure.store;

import com.github.lmartinez84.showtracker.shared.domain.store.EventStore;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public final class RestEventStore implements EventStore {
    private final WebClient client;

    public RestEventStore() {
        client = WebClient.builder().build();
    }

    @Override
    public Mono<String> retrieve(String aggregateType, String id) {
        return client.get().uri("").retrieve().bodyToMono(String.class);
    }
}
