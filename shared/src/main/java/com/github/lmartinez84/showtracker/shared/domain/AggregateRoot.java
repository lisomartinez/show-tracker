package com.github.lmartinez84.showtracker.shared.domain;

import java.util.List;

public interface AggregateRoot {
    Identifier id();

    List<DomainEvent> pollChanges();
}
