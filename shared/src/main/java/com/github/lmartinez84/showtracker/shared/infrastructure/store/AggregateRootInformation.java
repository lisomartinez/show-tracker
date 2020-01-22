package com.github.lmartinez84.showtracker.shared.infrastructure.store;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class AggregateRootInformation {
    Map<String, Class<? extends AggregateRoot>> registry;

    public AggregateRootInformation() {
        registry = scanAggregates();
    }

    private Map<String, Class<? extends AggregateRoot>> scanAggregates() {
        Reflections reflections = new Reflections("com.github.lmartinez84.showtracker");
        Map<String, Class<? extends AggregateRoot>> aggregates = new HashMap<>();
        Set<Class<? extends AggregateRoot>> aggregatesClass = reflections.getSubTypesOf(AggregateRoot.class);
        for (Class<? extends AggregateRoot> aggregateClass : aggregatesClass) {
            String name = aggregateClass.getName();
            aggregates.put(name, aggregateClass);
        }
        return aggregates;
    }

    public Class<? extends AggregateRoot> forName(String name) {
        return registry.get(name);
    }
}
