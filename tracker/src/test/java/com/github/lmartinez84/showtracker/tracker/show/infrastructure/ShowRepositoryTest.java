package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;
import com.github.lmartinez84.showtracker.shared.infrastructure.store.*;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowMother;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.Show;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowRepository;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.event.ShowCreatedEvent;
import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.EventStoreBuilder;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public final class ShowRepositoryTest {
    private static final String EVENT_STORE_HOST = "EVENT_STORE_HOST";
    private static final String EVENT_STORE_PORT = "EVENT_STORE_PORT";
    private static final String EVENT_STORE_USER = "EVENT_STORE_USER";
    private static final String EVENT_STORE_PASS = "EVENT_STORE_PASS";
    private ShowRepository showRepository;

    @BeforeEach
    void setUp() {
        EventStore eventStore = EventStoreBuilder.newBuilder()
                                                 .singleNodeAddress(System.getenv(EVENT_STORE_HOST),
                                                                    Integer.parseInt(System.getenv(EVENT_STORE_PORT)))
                                                 .userCredentials(System.getenv(EVENT_STORE_USER),
                                                                  System.getenv(EVENT_STORE_PASS))
                                                 .build();
        ObjectMapper objectMapper = new ObjectMapper();
        EventStoreAdapter eventStoreAdapter = new EventStoreAdapter(eventStore, new EventMapper(
                new DomainEventInformation(),
                new EventStoreDeserializer(objectMapper),
                new EventStoreSerializer(objectMapper)));
        showRepository = new ShowEventSourcedRepository(eventStoreAdapter);
    }

    @Test
    void save_should_return_a_list_of_domain_events_appended_to_the_event_store() {
        Show show = ShowMother.from(Identifier.newAggregateId(), "aTitle", 2010);
        Flux<DomainEvent> save = showRepository.save(show);
        StepVerifier.create(save)
                    .assertNext(ev -> assertThatEventIsEqualToShowCreatedEvent(show, ev))
                    .verifyComplete();
    }

    private void assertThatEventIsEqualToShowCreatedEvent(Show show, DomainEvent ev) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(ev.type()).isEqualTo(ShowCreatedEvent.TYPE);
        softAssertions.assertThat(ev.aggregateId()).isEqualTo(show.id().value());
        softAssertions.assertThat(((ShowCreatedEvent) ev).title()).isEqualTo(show.title().value());
        softAssertions.assertThat(((ShowCreatedEvent) ev).year()).isEqualTo(show.year().value());
        softAssertions.assertAll();
    }

    @Test
    void save_should_without_event_return_an_empty_list() {
        String id = Identifier.newAggregateId();
        Show show = ShowMother.from(id, "atitle", 2010);

        //this clean the shows events.
        show.pollChanges();

        Flux<DomainEvent> save = showRepository.save(show);
        StepVerifier.create(save).verifyComplete();
    }

    @Test
    void load_should_recreate_aggregate_from_domains_events_fetched_from_event_store() {
        //arrange
        Show show = ShowMother.from(Identifier.newAggregateId(), "atitle", 2010);
        Flux<DomainEvent> save = showRepository.save(show);

        //act
        Mono<Show> loaded = showRepository.load(show.id());

        //assert
        SoftAssertions sa = new AutoCloseableSoftAssertions();
        StepVerifier.create(loaded).assertNext(fetched -> {
            sa.assertThat(fetched).isEqualTo(show);
            sa.assertThat(fetched.title()).isEqualTo(show.title());
            sa.assertThat(fetched.year()).isEqualTo(show.year());
            sa.assertAll();
        }).verifyComplete();
    }
}
