package com.github.lmartinez84.showtracker.shared.infrastructure.bus.event;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.bus.event.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

public class ReactiveEventBus implements EventBus {
    private final DomainEventSubscribers subscribers;
    private final ReplayProcessor<DomainEvent> processor = ReplayProcessor.create();

    public ReactiveEventBus(DomainEventSubscribers subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public void publish(Flux<DomainEvent> events) {
        events.subscribe(event -> {
            Flux<ReplayProcessor<DomainEvent>> replayProcessorFlux = subscribers.forEvent(event);
            replayProcessorFlux.subscribe(pub -> pub.onNext(event));
            processor.onNext(event);
        });
    }

    @Override
    public Flux<DomainEvent> consume() {
        return processor;
    }

}
