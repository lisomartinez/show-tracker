package com.github.lmartinez84.showtracker.shared.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DomainEventStorage {
    private List<DomainEvent> changes;

    private DomainEventStorage(List<DomainEvent> changes) {
        this.changes = changes;
    }

    public static DomainEventStorage initialize() {
        return new DomainEventStorage(new ArrayList<>());
    }

    public List<DomainEvent> pollChanges() {
        List<DomainEvent> changesToReturn = this.changes;
        clear();
        return changesToReturn;
    }

    public void addChange(DomainEvent event) {
        this.changes.add(event);
    }

    public void clear() {
        changes = Collections.emptyList();
    }
}
