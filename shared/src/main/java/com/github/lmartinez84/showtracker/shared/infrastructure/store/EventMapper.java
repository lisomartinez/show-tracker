package com.github.lmartinez84.showtracker.shared.infrastructure.store;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.msemys.esjc.EventData;
import com.github.msemys.esjc.ResolvedEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public final class EventMapper {
    private final DomainEventInformation domainEventInformation;
    private final EventStoreDeserializer eventStoreDeserializer;
    private final EventStoreSerializer eventStoreSerializer;

    public EventMapper(DomainEventInformation domainEventInformation,
                       EventStoreDeserializer eventStoreDeserializer,
                       EventStoreSerializer eventStoreSerializer
    ) {
        this.domainEventInformation = domainEventInformation;
        this.eventStoreDeserializer = eventStoreDeserializer;
        this.eventStoreSerializer = eventStoreSerializer;
    }

    public Mono<DomainEvent> mapToDomainEvent(ResolvedEvent e) {
        Map<String, Serializable> domainEventFields = eventStoreDeserializer.deserialize(e);
        HashMap<String, Serializable> data = (HashMap<String, Serializable>) domainEventFields.get("data");
        try {
            DomainEvent event = (DomainEvent) fromPrimitives(domainEventClass(data))
                    .invoke(domainEvent(domainEventClass(data)),
                            aggregateId(attributes(data)),
                            eventId(data),
                            type(data),
                            occurredOn(data),
                            attributes(data));
            return Mono.just(event);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }

    }


    private Method fromPrimitives(Class<? extends DomainEvent> domainEventClass) throws NoSuchMethodException {
        return domainEventClass.getMethod("fromPrimitives",
                                          String.class,
                                          String.class,
                                          String.class,
                                          String.class,
                                          Map.class);
    }

    private Class<? extends DomainEvent> domainEventClass(Map<String, Serializable> domainEventFields) {
        return domainEventInformation.forName(type(domainEventFields));
    }

    private DomainEvent domainEvent(Class<? extends DomainEvent> domainEventClass) throws
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException {
        return domainEventClass.getConstructor().newInstance();
    }

    private String aggregateId(Map<String, Serializable> attributes) {
        return attributes.get("id").toString();
    }

    private String eventId(Map<String, Serializable> domainEventFields) {
        return domainEventFields.get("id").toString();
    }

    private String type(Map<String, Serializable> domainEventFields) {
        return domainEventFields.get("type").toString();
    }

    private String occurredOn(Map<String, Serializable> domainEventFields) {
        return domainEventFields.get("occurred_on").toString();
    }

    private Map<String, Serializable> attributes(Map<String, Serializable> domainEventFields) {
        return (HashMap<String, Serializable>) domainEventFields.get("attributes");
    }

    public List<EventData> mapToEventStoreEventData(List<DomainEvent> events) {
        return eventStoreSerializer.serialize(events);
    }
}
