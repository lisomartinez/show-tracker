package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;
import com.github.lmartinez84.showtracker.shared.infrastructure.store.DeserializersRegistry;
import com.github.lmartinez84.showtracker.shared.infrastructure.store.EventDeserializer;
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

public final class DeserializersRegistryTest {
    private DeserializersRegistry registry;

    @Test
    @DisplayName("")
//    @SuppressWarnings("all")
    void name() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        registry = new DeserializersRegistry();
        String eventId = Identifier.newEventId();
        Map<String, Map<String, String>> event = Map.of("event_data", Map.of("id", eventId, "title", "aTitle", "year", "2010"));
        Show show = Show.createEmpty();
        Show expected = new Show(ShowId.create(eventId), ShowTitle.create("aTitle"), ShowYear.create(2010));
        Class<? extends EventDeserializer<? extends DomainEvent<? extends AggregateRoot>>> deserializer = registry.getDeserializer(
                ShowCreatedEvent.class.getName());
        EventDeserializer<? extends DomainEvent<? extends AggregateRoot>> eventDeserializer = deserializer.getConstructor()
                .newInstance();
        Method deserialize = deserializer.getMethod("deserialize", Map.class);

        DomainEvent<Show> domainEvent = (DomainEvent<Show>) deserialize.invoke(eventDeserializer, event);

//        domainEvent.onEvent(show);
//        assertThat(show.id()).isEqualTo(eventId);
    }
}
