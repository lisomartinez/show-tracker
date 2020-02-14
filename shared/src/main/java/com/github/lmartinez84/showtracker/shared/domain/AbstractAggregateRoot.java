package com.github.lmartinez84.showtracker.shared.domain;

import java.util.List;

public abstract class AbstractAggregateRoot implements AggregateRoot {
    protected DomainEventStorage events;
    protected Identifier id;

    protected AbstractAggregateRoot(Identifier id, DomainEventStorage events) {
        this.id = id;
        this.events = events;
    }

    @Override
    public Identifier id() {
        return id;
    }

    @Override
    public List<DomainEvent> pollChanges() {
        return events.pollChanges();
    }

    protected void addChange(DomainEvent event) {
        events.addChange(event);
    }

    protected void clear() {
        events.clear();
    }
}
