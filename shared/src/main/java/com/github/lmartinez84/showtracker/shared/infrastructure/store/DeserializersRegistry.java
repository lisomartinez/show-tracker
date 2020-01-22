package com.github.lmartinez84.showtracker.shared.infrastructure.store;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
public final class DeserializersRegistry {
    Map<String, Class<? extends EventDeserializer<? extends DomainEvent<? extends AggregateRoot>>>> deserializers;

    public DeserializersRegistry() {
        try {
            deserializers = scanDeserializers();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Class<? extends EventDeserializer<? extends DomainEvent<? extends AggregateRoot>>>> scanDeserializers() throws
            NoSuchMethodException {
        Reflections reflections = new Reflections("com.github.lmartinez84.showtracker");
        Map<String, Class<? extends EventDeserializer<? extends DomainEvent<? extends AggregateRoot>>>> registry = new HashMap<>();
        Set<Class<? extends EventDeserializer>> subTypesOf = reflections.getSubTypesOf(EventDeserializer.class);
        for (Class<? extends EventDeserializer> aClass : subTypesOf) {
            Class<?> event = aClass.getMethod("deserialize", Map.class).getReturnType();
            registry.put(event.getName(),
                    (Class<? extends EventDeserializer<? extends DomainEvent<? extends AggregateRoot>>>) aClass
            );
        }
        return registry;
    }

    public Class<? extends EventDeserializer<? extends DomainEvent<? extends AggregateRoot>>> getDeserializer(String name) {
        return deserializers.get(name);
    }
}
