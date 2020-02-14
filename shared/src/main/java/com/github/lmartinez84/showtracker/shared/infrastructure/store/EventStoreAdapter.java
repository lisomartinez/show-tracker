package com.github.lmartinez84.showtracker.shared.infrastructure.store;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.EventSourcingAggregate;
import com.github.msemys.esjc.EventData;
import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.WriteResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public final class EventStoreAdapter {
    public static final int BATCH_SIZE = 500;
    public static final boolean RESOLVE_LINK_TOS = false;
    private static final long START = 0L;
    private final EventStore eventStore;
    private EventMapper eventMapper;

    public EventStoreAdapter(EventStore eventStore, EventMapper eventMapper) {
        this.eventStore = eventStore;
        this.eventMapper = eventMapper;
    }

    public Flux<DomainEvent> loadDomainEvents(String stream, String id) {
        return Flux.fromStream(eventStore
                                       .streamEventsForward(stream + "-" + id,
                                                            START,
                                                            BATCH_SIZE,
                                                            RESOLVE_LINK_TOS
                                       )
                                       .map(eventMapper::mapToDomainEvent))
                   .flatMap(Flux::from);
    }


    public Flux<DomainEvent> save(String stream, String id, EventSourcingAggregate aggregate) {
        final List<DomainEvent> events = aggregate.pollChanges();
        if (events.isEmpty()) {
            return Flux.empty();
        }
        List<EventData> serializedEvents = eventMapper.mapToEventStoreEventData(events);
        Mono<WriteResult> result = Mono.fromFuture(appendEventsToEventStore(stream,
                                                                            id,
                                                                            aggregate,
                                                                            serializedEvents));

        return result.thenMany(Flux.fromIterable(events));
    }

    private CompletableFuture<WriteResult> appendEventsToEventStore(String stream,
                                                                    String id,
                                                                    EventSourcingAggregate aggregate,
                                                                    List<EventData> serializedEvents
    ) {
        return eventStore.appendToStream(
                stream + "-" + id,
                aggregate.aggregateVersion().value(),
                serializedEvents);
    }
}
