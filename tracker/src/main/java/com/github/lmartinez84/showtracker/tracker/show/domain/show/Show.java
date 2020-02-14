package com.github.lmartinez84.showtracker.tracker.show.domain.show;

import com.github.lmartinez84.showtracker.shared.domain.AbstractEventSourcingAggregate;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.event.ShowCreatedEvent;

import java.util.Objects;

public final class Show extends AbstractEventSourcingAggregate {
    public static final String TYPE = "show-tracker.tracker.show-";
    private ShowTitle title;
    private ShowYear year;

    private Show(ShowId id,
                 ShowTitle title,
                 ShowYear year
    ) {
        super(id);
        this.title = title;
        this.year = year;
        events.addChange(ShowCreatedEvent.create(
                id.value(),
                title.value(),
                year.value(),
                versions.nextDomainEventVersionAsLong()
        ));
        versions.nextDomainEventVersion();
    }

    private Show() {
        super(null);
        this.title = null;
        this.year = null;

    }

    public static Show create(ShowId id, ShowTitle title, ShowYear year) {
        return new Show(id, title, year);
    }


    public static Show createEmpty() {
        return new Show();
    }


    public void apply(ShowCreatedEvent event) {
        this.id = ShowId.create(event.id());
        this.title = ShowTitle.create(event.title());
        this.year = ShowYear.create(event.year());
    }

    public ShowTitle title() {
        return title;
    }

    public ShowYear year() {
        return year;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", title=" + title +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Objects.equals(id, show.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String type() {
        return TYPE;
    }
}
