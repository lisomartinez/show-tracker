package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.bus.event.EventBus;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.event.DomainEventSubscribers;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.event.ReactiveEventBus;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowCreatedEventMother;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.event.ShowCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ApplicationContext.class,
        ReactiveEventBus.class,
        DomainEventSubscribers.class})
public final class ReactiveEventBusTest {

    @MockBean
    private ShowCreatedEventHandler handler;

    @Autowired
    private DomainEventSubscribers subscribers;

    @Autowired
    private EventBus eventBus;

    @Test
    void publishingAnEventIsReadyForConsumption() {
        ShowCreatedEvent expected = ShowCreatedEventMother.createRandom();
        Flux<DomainEvent> domainEventFlux = Flux.just(expected);
        eventBus.publish(domainEventFlux);
        StepVerifier.create(eventBus.consume()).assertNext(e -> assertThat(e)
                .as("Event send by event bus should be the same as the event published")
                .isEqualTo(expected))
                    .thenCancel();
    }

    @Test
    void eventHandlersReceiveEventsOfCertainTypeToHandle() {
        ShowCreatedEvent expected = ShowCreatedEventMother.createRandom();
        Flux<DomainEvent> domainEventFlux = Flux.just(expected);
        eventBus.publish(domainEventFlux);
        verify(handler, times(1)).handle(expected);
    }
}
