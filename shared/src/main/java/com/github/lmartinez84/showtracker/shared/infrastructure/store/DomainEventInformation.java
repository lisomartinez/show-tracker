package com.github.lmartinez84.showtracker.shared.infrastructure.store;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
@Service
public final class DomainEventInformation {
    private final Map<String, Class<? extends DomainEvent>> registry;

    public DomainEventInformation() {
        registry = scanEvents();
    }

    private Map<String, Class<? extends DomainEvent>> scanEvents() {
        Map<String, Class<? extends DomainEvent>> events = new HashMap<>();
        Reflections reflections = new Reflections("com.github.lmartinez84.showtracker");
        Set<Class<? extends DomainEvent>> domainEvents = reflections.getSubTypesOf(DomainEvent.class);
        for (Class<? extends DomainEvent> domainEvent : domainEvents) {
            String eventName = null;
            try {
                Field field = domainEvent.getField("TYPE");
                eventName = field.get(domainEvent).toString();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            events.put(eventName, domainEvent);
        }
        return events;
    }

    public Class<? extends DomainEvent> forName(String name) {
        return registry.get(name);
    }
}
