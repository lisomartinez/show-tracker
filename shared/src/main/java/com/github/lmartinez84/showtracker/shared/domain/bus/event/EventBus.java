package com.github.lmartinez84.showtracker.shared.domain.bus.event;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import reactor.core.publisher.Flux;

public interface EventBus {
    void publish(Flux<DomainEvent> events);

    Flux<DomainEvent> consume();
}
