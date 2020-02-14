package com.github.lmartinez84.showtracker.shared.infrastructure.bus.event;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.bus.event.EventBus;
import com.github.lmartinez84.showtracker.shared.infrastructure.store.EventMapper;
import com.github.msemys.esjc.CatchUpSubscriptionSettings;
import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.StreamEventsSlice;
import com.github.msemys.esjc.StreamPosition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@DependsOn("domainEventSubscribers")
public final class EventStoreSubscription {
    private static final int ID = 0;
    private static final int STREAM = 1;
    private final DomainEventSubscribers subscribers;
    private final EventStore eventStore;
    private final EventBus eventBus;
    private final EventMapper mapper;

    public EventStoreSubscription(DomainEventSubscribers subscribers,
                                  EventStore eventStore,
                                  EventBus eventBus,
                                  EventMapper mapper
    ) {
        this.subscribers = subscribers;
        this.eventStore = eventStore;
        this.eventBus = eventBus;
        this.mapper = mapper;
        List<String> types = subscribers.allTypes();
        types.forEach(this::subscribeToEvent);
    }

    public void subscribeToEvent(@NotNull String eventType) {
        String eventStream = "$et-" + eventType;
        Mono.fromFuture(eventStore.readStreamEventsBackward(eventStream, StreamPosition.END, 1, false))
            .map(this::getLastVersion)
            .subscribe(version -> eventStore.subscribeToStreamFrom(
                    eventStream,
                    version,
                    CatchUpSubscriptionSettings.newBuilder().resolveLinkTos(true).build(),
                    (s, e) -> {
                        String[] idAndStream = new String(e.originalEvent().data).split("@");
                        long id = Long.parseLong(idAndStream[ID]);
                        String stream = idAndStream[STREAM];
                        eventStore.readEvent(stream, id, false).thenAccept(ev -> {
                            Mono<DomainEvent> domainEventMono = mapper.mapToDomainEvent(ev.event);
                            eventBus.publish(Flux.from(domainEventMono));
                        });

                    })
            );
    }

    private Long getLastVersion(StreamEventsSlice eventsSlice) {
        System.out.println(eventsSlice.lastEventNumber);
        return eventsSlice.lastEventNumber;
    }


}
