package com.github.lmartinez84.showtracker.tracker.show.domain.event;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;
import com.github.lmartinez84.showtracker.tracker.show.domain.Show;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class ShowCreatedEvent extends DomainEvent<Show> {
    private final String title;
    private final int year;

    public ShowCreatedEvent() {
        this(null, null, 0);
    }

    public ShowCreatedEvent(
            final String aggregateId,
            final String title,
            final int year
    ) {
        super(aggregateId, Show.class.getName(), Identifier.newEventId(), ShowCreatedEvent.class.getName());
        this.title = title;
        this.year = year;
    }

    public static ShowCreatedEvent create(
            @NotNull final String aggregateId,
            @NotNull final String title,
            final int year
    ) {
        return new ShowCreatedEvent(aggregateId, title, year);
    }

    public String id() {
        return aggregateId;
    }

    public String title() {
        return title;
    }

    public int year() {
        return year;
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


    @Override
    public void onEvent(Show aggregate) {
        aggregate.apply(this);
    }

    @Override
    public ShowCreatedEvent fromPrimitives(String aggregateId, Map<String, Serializable> body) {
        return new ShowCreatedEvent(aggregateId, body.get("title").toString(), Integer.parseInt(body.get("year").toString()));
    }


}
