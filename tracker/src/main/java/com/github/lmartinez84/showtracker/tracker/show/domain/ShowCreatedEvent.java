package com.github.lmartinez84.showtracker.tracker.show.domain;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;

import java.util.Objects;

public class ShowCreatedEvent extends DomainEvent {
    public ShowCreatedEvent(final String aggregateId) {
        super(aggregateId, Show.class.getName(), Identifier.newEventId(), ShowCreatedEvent.class.getName());
    }

    /* Todo: Check equals and hashcode implementation. Should it check event id also? */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShowCreatedEvent that = (ShowCreatedEvent) o;

        return aggregateId.equals(that.aggregateId) &&
               aggregateType.equals(that.aggregateType) &&
               eventType.equals(that.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, aggregateType, eventType);
    }
}
