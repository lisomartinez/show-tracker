package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.event.DomainEventSubscribers;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.event.EventStoreSubscription;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.event.ReactiveEventBus;
import com.github.lmartinez84.showtracker.shared.infrastructure.store.*;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowMother;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.Show;
import com.github.msemys.esjc.EventStore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = {ApplicationContext.class,
        EventBusConfiguration.class,
        DomainEventInformation.class,
        EventStoreDeserializer.class,
        EventStoreSerializer.class,
        EventStore.class,
        ReactiveEventBus.class,
        EventMapper.class,
        DomainEventSubscribers.class,
        EventStoreSubscription.class,
        EventStoreAdapter.class,
        ObjectMapper.class
})
public class EventConsumerTest extends MessagingInfrastructureTest {

    @Test
    void savedEventsToEventsStoreShouldBeSendToEventBus() {
        Show show = ShowMother.random();
        adapter.save(show.type(), show.id().value(), show);
        shouldBeSendToEventBus(show);

    }

    @Test
    void savedEventsToEventsStoreShouldBeReceivedByEventHandlers() throws InterruptedException {
        Show show = ShowMother.random();
        adapter.save(show.type(), show.id().value(), show);
        shouldBeReceivedByEventHandler(show);
    }
}
