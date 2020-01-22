package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.store.EventStore;
import com.github.lmartinez84.showtracker.shared.domain.store.EventStoreAdapter;
import com.github.lmartinez84.showtracker.shared.domain.store.EventStoreResponse;
import com.github.lmartinez84.showtracker.tracker.show.domain.Show;

import java.util.List;

public final class ShowEventStoreAdapter extends EventStoreAdapter<Show> {

    public ShowEventStoreAdapter(EventStore eventStore, ObjectMapper objectMapper) {
        super(eventStore, objectMapper);
    }

    @Override
    protected Show createEmtpyAggregate() {
        return Show.createEmpty();
    }

    @Override
    protected List<DomainEvent<Show>> mapEvents(EventStoreResponse eventStoreResponse) throws JsonProcessingException {
        return objectMapper
                .readValue(eventStoreResponse.data(), new TypeReference<>() {
                });
    }

    @Override
    protected Show mapSnapshot(
            EventStoreResponse eventStoreResponse, Show root
    ) throws JsonProcessingException {
        return objectMapper.readValue(eventStoreResponse.snapshot(), Show.class);
    }
}
