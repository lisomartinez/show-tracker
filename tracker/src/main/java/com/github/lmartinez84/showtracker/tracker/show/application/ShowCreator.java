package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.bus.event.EventBus;
import com.github.lmartinez84.showtracker.tracker.show.domain.Show;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowId;

import java.util.List;

public final class ShowCreator {
    private final EventBus bus;

    public ShowCreator(EventBus bus) {
        this.bus = bus;
    }

    public void create(ShowId id) {
        Show show = Show.create(id);
        List<DomainEvent> domainEvents = show.pullDomainEvents();
        bus.publish(domainEvents);
    }
}
