package com.github.lmartinez84.showtracker.shared.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public abstract class DomainEvent {
    protected final String aggregateId;
    protected final String eventId;
    protected final String eventType;
    protected final String ocurredOn;
    protected final long version;

    public DomainEvent() {
        aggregateId = null;
        eventId = null;
        eventType = null;
        ocurredOn = null;
        version = Long.MIN_VALUE;
    }

    public DomainEvent(String aggregateId, String eventId, String eventType, long version) {
        this(aggregateId, eventId, eventType, version, Utils.occurredOn());
    }

    public DomainEvent(String aggregateId, String eventId, String eventType, long version, String occurredOn) {
        this.aggregateId = aggregateId;
        this.eventId = eventId;
        this.eventType = eventType;
        this.version = version;
        this.ocurredOn = occurredOn;
    }

    public DomainEvent(String type) {
        aggregateId = null;
        eventId = null;
        eventType = type;
        ocurredOn = null;
        version = Long.MIN_VALUE;
    }


    public abstract <T extends AggregateRoot> void applyTo(T aggregate);

    @Override
    public String toString() {
        return "DomainEvent{" +
                "aggregateId='" + aggregateId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", ocurredOn='" + ocurredOn + "}";
    }

    public abstract DomainEvent fromPrimitives(String aggregateId,
                                               String eventId,
                                               String eventType,
                                               String occurredOn,
                                               Map<String, Serializable> attributes
    );

    public abstract Map<String, Serializable> toPrimitives();

    public String aggregateId() {
        return aggregateId;
    }

    public String eventId() {
        return eventId;
    }

    public String type() {
        return eventType;
    }

    public String ocurredOn() {
        return ocurredOn;
    }

    public long version() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainEvent that = (DomainEvent) o;
        return version == that.version &&
                Objects.equals(aggregateId, that.aggregateId) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(eventType, that.eventType) &&
                Objects.equals(ocurredOn, that.ocurredOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, eventId, eventType, ocurredOn, version);
    }
}
