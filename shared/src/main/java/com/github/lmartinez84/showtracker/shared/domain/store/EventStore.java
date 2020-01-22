package com.github.lmartinez84.showtracker.shared.domain.store;

import reactor.core.publisher.Mono;

public interface EventStore {
    Mono<String> retrieve(String aggregateType, String id);
}
