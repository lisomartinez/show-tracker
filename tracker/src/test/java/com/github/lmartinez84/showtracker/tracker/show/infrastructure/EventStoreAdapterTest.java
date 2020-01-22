package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lmartinez84.showtracker.shared.domain.store.EventStore;
import com.github.lmartinez84.showtracker.shared.domain.store.EventStoreAdapter;
import com.github.lmartinez84.showtracker.shared.infrastructure.store.RestEventStore;
import com.github.lmartinez84.showtracker.tracker.show.application.ShowIdMother;
import com.github.lmartinez84.showtracker.tracker.show.domain.Show;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventStoreAdapterTest {

    private EventStoreAdapter<Show> adapter;

    private EventStore eventStore;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        eventStore = mock(RestEventStore.class);
        mapper = new ObjectMapper();
        adapter = new ShowEventStoreAdapter(eventStore, mapper);
    }

    @Test
    @DisplayName("")
    void it_should_return_an_EventStoreResponse_with_events() {
        ShowId id = ShowIdMother.random();
        when(eventStore.retrieve(Show.class.getName(), id.value())).thenReturn(Mono.just(EventStoreResponseStringMother.random()));
    }
}