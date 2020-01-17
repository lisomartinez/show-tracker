package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowCreatedEvent;

import java.util.Collections;
import java.util.List;

public class ShowCreatedEventMother {
    public static List<DomainEvent> fromCommand(CreateShowCommand cmd) {
        return Collections.singletonList(new ShowCreatedEvent(cmd.id()));
    }
}
