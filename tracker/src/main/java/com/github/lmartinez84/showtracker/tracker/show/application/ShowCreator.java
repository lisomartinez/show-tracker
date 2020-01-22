package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.bus.event.EventBus;
import com.github.lmartinez84.showtracker.tracker.show.domain.Show;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowId;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowTitle;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowYear;

import java.util.List;

public class ShowCreator {
    private final EventBus bus;

    public ShowCreator(EventBus bus) {
        this.bus = bus;
    }

    public void create(ShowId id, ShowTitle title, ShowYear year) {
        List<DomainEvent<? extends AggregateRoot>> domainEvents = Show.create(id, title, year);
        bus.publish(domainEvents);
    }
}
