package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;
import com.github.lmartinez84.showtracker.shared.domain.store.AggregateNotSavedException;
import com.github.lmartinez84.showtracker.shared.domain.store.EventSourcingRepository;
import com.github.lmartinez84.showtracker.shared.infrastructure.store.EventStoreAdapter;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.Show;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowId;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ShowEventSourcedRepository implements ShowRepository, EventSourcingRepository<Show, ShowId> {

    private final EventStoreAdapter adapter;

    @Autowired
    public ShowEventSourcedRepository(EventStoreAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public Flux<DomainEvent> save(Show aggregate) {
        try {
            return adapter.save(aggregate.type(), aggregate.id().value(), aggregate);
        } catch (Exception ex) {
            throw new AggregateNotSavedException(ex.getMessage());
        }
    }

    @Override
    public Mono<Show> load(Identifier id) {
        final Show show = Show.createEmpty();
        Flux<DomainEvent> events = adapter.loadDomainEvents(show.type(), id.value());
        return events.doOnNext(event -> event.applyTo(show)).then(Mono.just(show));
    }
}
