package com.github.lmartinez84.showtracker.tracker.show.application.show.create;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.bus.event.EventBus;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ShowCreator {
    private final EventBus eventBus;
    private final ShowRepository showRepository;

    public ShowCreator(EventBus eventBus, ShowRepository showRepository) {
        this.eventBus = eventBus;
        this.showRepository = showRepository;
    }

    public void create(ShowId id, ShowTitle title, ShowYear year) {
        Show show = Show.create(id, title, year);
        Flux<DomainEvent> savedEvents = showRepository.save(show);
        eventBus.publish(savedEvents);
    }
}
