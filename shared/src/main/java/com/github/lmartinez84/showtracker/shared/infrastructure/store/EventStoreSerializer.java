package com.github.lmartinez84.showtracker.shared.infrastructure.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.msemys.esjc.EventData;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public final class EventStoreSerializer {
    private final ObjectMapper objectMapper;

    public EventStoreSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<EventData> serialize(List<DomainEvent> domainEvents) {
        return domainEvents.stream()
                           .map(this::toEventData)
                           .collect(Collectors.toList());
    }

    private EventData toEventData(DomainEvent domainEvent) {
        try {
            return EventData.newBuilder()
                            .type(domainEvent.type())
                            .eventId(UUID.fromString(domainEvent.eventId()))
                            .jsonData(objectMapper.writeValueAsString(domainEventtoHashMap(domainEvent)))
                            .build();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("domain event not mapped to event data");
        }
    }


    private HashMap<String, Serializable> domainEventtoHashMap(DomainEvent domainEvent) {
        HashMap<String, Serializable> toSerialize = new HashMap<>();
        HashMap<String, Serializable> data = new HashMap<>();
        data.put("id", domainEvent.eventId());
        data.put("type", domainEvent.type());
        data.put("occurred_on", domainEvent.ocurredOn());
        data.put("attributes", new HashMap<>(domainEvent.toPrimitives()));
        toSerialize.put("data", data);
        toSerialize.put("meta", new HashMap<String, Serializable>());
        return toSerialize;
    }
}
