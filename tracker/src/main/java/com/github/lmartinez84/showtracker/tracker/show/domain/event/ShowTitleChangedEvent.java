package com.github.lmartinez84.showtracker.tracker.show.domain.event;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.tracker.show.domain.Show;

import java.io.Serializable;
import java.util.Map;

public final class ShowTitleChangedEvent extends DomainEvent<Show> {
    public ShowTitleChangedEvent(String aggregateId, String aggregateType, String eventId, String eventType) {
        super(aggregateId, aggregateType, eventId, eventType);
    }

    @Override
    public void onEvent(Show aggregate) {
        aggregate.apply(this);
    }

    @Override
    public DomainEvent<Show> fromPrimitives(String aggregateId, Map<String, Serializable> body) {
        return null;
    }
}
