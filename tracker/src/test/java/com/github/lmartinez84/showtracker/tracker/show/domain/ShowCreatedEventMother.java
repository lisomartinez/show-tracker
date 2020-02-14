package com.github.lmartinez84.showtracker.tracker.show.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;
import com.github.lmartinez84.showtracker.shared.domain.Utils;
import com.github.lmartinez84.showtracker.tracker.show.application.show.create.CreateShowCommand;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.event.ShowCreatedEvent;
import reactor.core.publisher.Flux;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class ShowCreatedEventMother {
    public static Flux<DomainEvent> fromCommand(CreateShowCommand cmd) {
        return Flux.just(ShowCreatedEvent.create(cmd.id(), cmd.title(), cmd.year(), 0));
    }

    public static HashMap<String, Serializable> randomPrimitives(ShowCreatedEvent show) throws JsonProcessingException {
        HashMap<String, Serializable> domainEventField = new HashMap<>();
        domainEventField.put("data", data());
        domainEventField.put("meta", new HashMap<>());
        domainEventField.put("attributes", new HashMap<>(show.toPrimitives()));
        return domainEventField;
    }

    private static HashMap<String, Serializable> data() {
        HashMap<String, Serializable> data = new HashMap<>();
        data.put("id", UUID.randomUUID().toString());
        data.put("type", ShowCreatedEvent.class.getSimpleName());
        data.put("occurred_on", Utils.occurredOn());
        return data;
    }

    public static ShowCreatedEvent createRandom() {
        return ShowCreatedEvent.create(Identifier.newAggregateId(), "title", 2010, 0L);
    }

    public static Flux<DomainEvent> fluxOfSingleEvent() {
        return Flux.just(createRandom());
    }
}
