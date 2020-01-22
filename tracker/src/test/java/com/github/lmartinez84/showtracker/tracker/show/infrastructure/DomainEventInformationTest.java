package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.infrastructure.store.DomainEventInformation;
import com.github.lmartinez84.showtracker.tracker.show.domain.Show;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowId;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowTitle;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowYear;
import com.github.lmartinez84.showtracker.tracker.show.domain.event.ShowCreatedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

public final class DomainEventInformationTest {
    private DomainEventInformation information;

    @Test
    @DisplayName("")
    void name() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        information = new DomainEventInformation();
        String eventId = UUID.randomUUID().toString();
        Map<String, String> event = Map.of("id", eventId, "title", "aTitle", "year", "2010");
        Show show = Show.createEmpty();
        Show expected = new Show(ShowId.create(eventId), ShowTitle.create("aTitle"), ShowYear.create(2010));
        Class<? extends DomainEvent> aClass = information.forName(ShowCreatedEvent.class.getName());
        Method fromPrimitives = aClass.getMethod("fromPrimitives", String.class, Map.class);
        DomainEvent domainEvent = aClass.getConstructor().newInstance();
        DomainEvent created = (DomainEvent) fromPrimitives.invoke(domainEvent, expected.id().value().toString(), event);
        created.onEvent(show);
    }
}
