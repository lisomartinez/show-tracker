package com.github.lmartinez84.showtracker.shared.domain;

public final class EventSourcingAggregateVersioning {
    private AggregateVersion aggregateVersion;
    private DomainEventVersion domainEventVersion;

    private EventSourcingAggregateVersioning() {
        aggregateVersion = AggregateVersion.forNewAggregate();
        domainEventVersion = DomainEventVersion.forNewDomainEvent();
    }

    public static EventSourcingAggregateVersioning initialize() {
        return new EventSourcingAggregateVersioning();
    }

    public void updateVersion(long version) {
        this.aggregateVersion = AggregateVersion.create(version);
        this.domainEventVersion = DomainEventVersion.create(version);
    }

    public long nextDomainEventVersionAsLong() {
        return domainEventVersion.nextAsLong();
    }

    public void nextDomainEventVersion() {
        domainEventVersion = domainEventVersion.next();
    }

    public AggregateVersion aggregateVersion() {
        return aggregateVersion;
    }


}
