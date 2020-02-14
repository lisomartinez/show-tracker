package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.infrastructure.store.DomainEventInformation;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.event.ShowCreatedEvent;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

public final class DomainEventInformationTest {
    private DomainEventInformation information;

    @Test
    void name() throws
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        //arrange
        information = new DomainEventInformation();
        //act
        Class<? extends DomainEvent> aClass = information.forName(ShowCreatedEvent.class.getName());
        DomainEvent domainEvent = aClass.getConstructor().newInstance();

        assertThat(domainEvent).isInstanceOf(ShowCreatedEvent.class);
    }
}
