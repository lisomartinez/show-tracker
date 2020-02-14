package com.github.lmartinez84.showtracker.shared.infrastructure.store;

import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.EventStoreBuilder;


public class EventStoreConfiguration {
    public static EventStore eventStore(String host, int port, String user, String password) {
        return EventStoreBuilder.newBuilder()
                                .singleNodeAddress(host, port)
                                .userCredentials(user, password)
                                .build();
    }
}
