package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.bus.event.EventBus;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.event.DomainEventSubscribers;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.event.EventStoreSubscription;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.event.ReactiveEventBus;
import com.github.lmartinez84.showtracker.shared.infrastructure.store.*;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.Show;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.event.ShowCreatedEvent;
import com.github.msemys.esjc.EventStore;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
public abstract class MessagingInfrastructureTest {

    @MockBean
    protected ShowCreatedEventHandler handler;

    @Autowired
    protected EventStore eventStore;

    @Autowired
    protected EventBus eventBus;

    @Autowired
    protected EventStoreAdapter adapter;

    public void shouldBeReceivedByEventHandler(Show show) throws InterruptedException {
        verify(handler, times(1)).handle(any(ShowCreatedEvent.class));
        Thread.sleep(1000);
    }

    public void shouldBeSendToEventBus(Show show) {
        StepVerifier.create(eventBus.consume())
                    .assertNext(event -> assertThatSavedShowHasSendAnEventAndItHasBeenReceived(show, event)
                    ).thenCancel();
    }

    private void assertThatSavedShowHasSendAnEventAndItHasBeenReceived(Show show, DomainEvent event) {
        SoftAssertions sa = new AutoCloseableSoftAssertions();
        sa.assertThat(event.aggregateId()).isEqualTo(show.id().value());
        sa.assertThat(event.type()).isEqualTo(ShowCreatedEvent.TYPE);
        sa.assertThat(((ShowCreatedEvent) event).title()).isEqualTo(show.title());
        sa.assertThat(((ShowCreatedEvent) event).year()).isEqualTo(show.year());
        sa.assertAll();
    }
}
