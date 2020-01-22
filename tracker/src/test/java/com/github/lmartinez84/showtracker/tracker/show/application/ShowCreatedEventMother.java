package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.tracker.show.domain.event.ShowCreatedEvent;

import java.util.Collections;
import java.util.List;

public class ShowCreatedEventMother {
    public static List<DomainEvent<? extends AggregateRoot>> fromCommand(CreateShowCommand cmd) {
        return Collections.singletonList(ShowCreatedEvent.create(cmd.id(), cmd.title(), cmd.year()));
    }
}
