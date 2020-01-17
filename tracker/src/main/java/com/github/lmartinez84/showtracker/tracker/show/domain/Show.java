package com.github.lmartinez84.showtracker.tracker.show.domain;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;

public final class Show extends AggregateRoot {
    private final ShowId id;

    public Show(ShowId id) {
        this.id = id;
        record(new ShowCreatedEvent(id.value()));
    }

    public static Show create(ShowId id) {
        return new Show(id);
    }

    public void apply(ShowCreatedEvent event) {
         //TBD
    }
}
