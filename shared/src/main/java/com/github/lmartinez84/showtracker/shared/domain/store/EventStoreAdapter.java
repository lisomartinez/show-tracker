package com.github.lmartinez84.showtracker.shared.domain.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class EventStoreAdapter<T extends AggregateRoot> {
    protected final ObjectMapper objectMapper;
    private final EventStore eventStore;

    public EventStoreAdapter(EventStore eventStore, ObjectMapper objectMapper) {
        this.eventStore = eventStore;
        this.objectMapper = objectMapper;
    }

    public Mono<T> load(String aggregateType, Identifier id) {
        return eventStore.retrieve(aggregateType, id.value())
                .map(this::toEventStoreResponse).map(this::createAggregate);

    }

    private EventStoreResponse toEventStoreResponse(String jsonNode) {
        EventStoreResponse response = null;
        try {
            response = objectMapper.readValue(jsonNode, EventStoreResponse.class);
        } catch (JsonProcessingException e) {

        }
        return response;
    }

    private T createAggregate(EventStoreResponse eventStoreResponse) {
        T root = createEmtpyAggregate();
        try {
            if (!eventStoreResponse.snapshot().isBlank()) {
                root = mapSnapshot(eventStoreResponse, root);
            }
            Class<? extends AggregateRoot> aClass = root.getClass();
            List<DomainEvent<T>> events = mapEvents(eventStoreResponse);

            for (DomainEvent<T> domainEvent : events) {
                domainEvent.onEvent(root);
            }
        } catch (JsonProcessingException ex) {

        }
        return root;
    }

    protected abstract T createEmtpyAggregate();

    protected abstract List<DomainEvent<T>> mapEvents(EventStoreResponse eventStoreResponse) throws
            JsonProcessingException;

    protected abstract T mapSnapshot(EventStoreResponse eventStoreResponse, T root) throws
            JsonProcessingException;

}
