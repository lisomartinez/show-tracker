package com.github.lmartinez84.showtracker.shared.domain.store;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.EventSourcingAggregate;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventSourcingRepository<T extends EventSourcingAggregate, K extends Identifier> {
    Flux<DomainEvent> save(T aggregate);

    Mono<T> load(Identifier id);
}
