package com.github.lmartinez84.showtracker.shared.domain.bus.event;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;

public interface EventHandler<T extends DomainEvent> {
    void handle(T event);
}
