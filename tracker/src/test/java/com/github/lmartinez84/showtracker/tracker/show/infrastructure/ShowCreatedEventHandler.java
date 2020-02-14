package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.domain.bus.event.EventHandler;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.event.ShowCreatedEvent;

public class ShowCreatedEventHandler implements EventHandler<ShowCreatedEvent> {
    @Override
    public void handle(ShowCreatedEvent event) {
        throw new UnsupportedOperationException();
    }
}
