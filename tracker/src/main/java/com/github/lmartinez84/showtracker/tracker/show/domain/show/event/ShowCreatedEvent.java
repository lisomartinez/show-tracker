package com.github.lmartinez84.showtracker.tracker.show.domain.show.event;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.Show;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShowCreatedEvent extends DomainEvent {
    public static final String TYPE = "show-tracker.tracker.1.event.show.created";
    private final String title;
    private final int year;

    public ShowCreatedEvent() {
        super(TYPE);
        title = null;
        year = Integer.MIN_VALUE;
    }

    private ShowCreatedEvent(
            final String aggregateId,
            final String title,
            final int year,
            final long version
    ) {
        super(aggregateId, Identifier.newEventId(), TYPE, version);
        this.title = title;
        this.year = year;
    }

    private ShowCreatedEvent(
            final String aggregateId,
            final String eventId,
            final String title,
            final int year,
            final long version,
            final String occurred_on
    ) {
        super(aggregateId, eventId, TYPE, version, occurred_on);
        this.title = title;
        this.year = year;
    }


    public static ShowCreatedEvent create(
            @NotNull final String aggregateId,
            @NotNull final String title,
            final int year,
            final long version
    ) {
        return new ShowCreatedEvent(aggregateId, title, year, version);
    }


    @Override
    public <T extends AggregateRoot> void applyTo(T aggregate) {
        Show show = (Show) aggregate;
        show.apply(this);
    }

    @Override
    public DomainEvent fromPrimitives(String aggregateId,
                                      String eventId,
                                      String eventType,
                                      String ocurredOn,
                                      Map<String, Serializable> attributes
    ) {
        return new ShowCreatedEvent(
                aggregateId,
                eventId,
                attributes.get("title").toString(),
                Integer.parseInt(attributes.get("year").toString()),
                Long.parseLong(attributes.get("version").toString()),
                ocurredOn
        );
    }

    @Override
    public Map<String, Serializable> toPrimitives() {
        Map<String, Serializable> primitives = new HashMap<>();
        primitives.put("id", aggregateId);
        primitives.put("title", title);
        primitives.put("year", year);
        primitives.put("version", version);
        return primitives;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ShowCreatedEvent that = (ShowCreatedEvent) o;
        return year == that.year &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, year);
    }
}
