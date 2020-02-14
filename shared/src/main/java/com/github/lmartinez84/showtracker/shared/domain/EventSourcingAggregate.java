package com.github.lmartinez84.showtracker.shared.domain;

import java.util.List;

public interface EventSourcingAggregate {
    void loadFromHistory(List<DomainEvent> domainEvents);

    List<DomainEvent> pollChanges();

    AggregateVersion aggregateVersion();

}
