package com.github.lmartinez84.showtracker.shared.infrastructure.store;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;

import java.util.Map;

public interface EventDeserializer<T extends DomainEvent<? extends AggregateRoot>> {
    T deserialize(Map<String, Map<String, String>> json);
}
