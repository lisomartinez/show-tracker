package com.github.lmartinez84.showtracker.shared.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public abstract class DomainEvent<T extends AggregateRoot> {
    protected final String aggregateId;
    protected final String aggregateType;
    protected final String eventId;
    protected final String eventType;
    protected final String ocurredOn;
    protected String triggeringEvent;


    public DomainEvent(String aggregateId, String aggregateType, String eventId, String eventType) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventId = eventId;
        this.eventType = eventType;
        this.ocurredOn = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String type() {
        return eventType;
    }

    public abstract void onEvent(T aggregate);

    @Override
    public String toString() {
        return "DomainEvent{" +
                "aggregateId='" + aggregateId + '\'' +
                ", aggregateType='" + aggregateType + '\'' +
                ", eventId='" + eventId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", ocurredOn='" + ocurredOn + '\'' +
                ", triggeringEvent='" + triggeringEvent + '\'' +
                '}';
    }

    public abstract DomainEvent<T> fromPrimitives(String aggregateId, Map<String, Serializable> body);
}
