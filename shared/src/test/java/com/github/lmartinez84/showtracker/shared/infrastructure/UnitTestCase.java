package com.github.lmartinez84.showtracker.shared.infrastructure;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.bus.event.EventBus;
import org.junit.jupiter.api.BeforeEach;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.*;

public abstract class UnitTestCase {

    protected EventBus eventBus;

    @BeforeEach
    public void setUpUnitTest() {
        eventBus = mock(EventBus.class);
    }

    public void shouldHavePublished(Flux<DomainEvent> domainEvents) {
        verify(eventBus, times(1)).publish(domainEvents);
    }
}
