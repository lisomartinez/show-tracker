package com.github.lmartinez84.showtracker.tracker.show.domain;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.tracker.show.domain.event.ShowCreatedEvent;
import com.github.lmartinez84.showtracker.tracker.show.domain.event.ShowTitleChangedEvent;

import java.util.Collections;
import java.util.List;

public final class Show extends AggregateRoot {
    private ShowId id;
    private ShowTitle title;
    private ShowYear year;

    public Show(ShowId id, ShowTitle title, ShowYear year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    private Show() {
        this.id = null;
        this.title = null;
        this.year = null;
    }

    public static List<DomainEvent<? extends AggregateRoot>> create(ShowId id, ShowTitle title, ShowYear year) {
        return Collections.singletonList(ShowCreatedEvent.create(id.value(), title.value(), year.value()));
    }

    public static Show createEmpty() {
        return new Show();
    }

    public void apply(ShowCreatedEvent event) {
        this.id = ShowId.create(event.id());
        this.title = ShowTitle.create(event.title());
        this.year = ShowYear.create(event.year());
    }

    public void apply(ShowTitleChangedEvent event) {

    }

    public ShowId id() {
        return id;
    }
}
