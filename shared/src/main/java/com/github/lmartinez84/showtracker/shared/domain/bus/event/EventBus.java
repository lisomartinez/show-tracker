package com.github.lmartinez84.showtracker.shared.domain.bus.event;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;

import java.util.List;

public interface EventBus {
    void publish(List<DomainEvent<? extends AggregateRoot>> events);

//    void publish(DomainEvent<? extends AggregateRoot> event);
}
