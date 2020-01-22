package com.github.lmartinez84.showtracker.shared.infrastructure.store;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;
import com.github.lmartinez84.showtracker.shared.domain.store.EventSourcingRepository;
import com.github.lmartinez84.showtracker.shared.domain.store.EventStoreAdapter;

public abstract class RestEventSourcingRepository<T extends AggregateRoot, V extends Identifier>
        implements EventSourcingRepository<T, V> {

    protected final EventStoreAdapter<T> adapter;

    protected RestEventSourcingRepository(EventStoreAdapter<T> adapter) {
        this.adapter = adapter;
    }
}
