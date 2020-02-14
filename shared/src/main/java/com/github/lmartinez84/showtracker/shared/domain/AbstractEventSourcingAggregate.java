package com.github.lmartinez84.showtracker.shared.domain;

import java.util.List;

public abstract class AbstractEventSourcingAggregate extends AbstractAggregateRoot implements EventSourcingAggregate {
    protected EventSourcingAggregateVersioning versions;

    protected AbstractEventSourcingAggregate(Identifier id,
                                             DomainEventStorage storage,
                                             EventSourcingAggregateVersioning versions
    ) {
        super(id, storage);
        this.versions = versions;
    }

    protected AbstractEventSourcingAggregate(Identifier id) {
        super(id, DomainEventStorage.initialize());
        this.versions = EventSourcingAggregateVersioning.initialize();
    }

    @Override
    public void loadFromHistory(List<DomainEvent> domainEvents) {
        domainEvents.forEach(d -> d.applyTo(this));
        versions.updateVersion(domainEvents.get(domainEvents.size() - 1).version());
        clear();
    }

    @Override
    public AggregateVersion aggregateVersion() {
        return versions.aggregateVersion();
    }

    public abstract String type();
}
