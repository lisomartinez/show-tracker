package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.infrastructure.store.EventStoreConfiguration;
import com.github.msemys.esjc.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventBusConfiguration {
    private final String host = System.getenv("EVENT_STORE_HOST");
    private final int port = Integer.parseInt(System.getenv("EVENT_STORE_PORT"));
    private final String user = System.getenv("EVENT_STORE_USER");
    private final String pass = System.getenv("EVENT_STORE_PASS");

    @Bean
    public EventStore eventStore() {
        return EventStoreConfiguration.eventStore(host, port, user, pass);
    }
}
