package com.github.lmartinez84.showtracker.shared.domain.store;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;
import reactor.core.publisher.Mono;

public interface EventSourcingRepository<T extends AggregateRoot, V extends Identifier> {
    Mono<T> load(V id);
}
