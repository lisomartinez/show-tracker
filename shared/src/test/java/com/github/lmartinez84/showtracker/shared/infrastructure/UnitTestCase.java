package com.github.lmartinez84.showtracker.shared.infrastructure;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.bus.event.EventBus;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.mockito.Mockito.*;

public abstract class UnitTestCase {
    protected EventBus eventBus;

    @BeforeEach
    void setUp() {
        eventBus = mock(EventBus.class);
    }

    public void shouldHavePublished(List<DomainEvent> domainEvents) {
        verify(eventBus, times(1)).publish(domainEvents);
    }
}
