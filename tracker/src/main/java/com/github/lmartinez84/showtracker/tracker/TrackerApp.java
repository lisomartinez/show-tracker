package com.github.lmartinez84.showtracker.tracker;

import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.EventStoreBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.github.lmartinez84.showtracker.shared.infrastructure.store")
@ComponentScan(basePackages = "com.github.lmartinez84.showtracker.shared.infrastructure.bus.command")
@ComponentScan(basePackages = "com.github.lmartinez84.showtracker.shared.infrastructure.bus.event")
public class TrackerApp {

    private String host;
    private int port;
    private String user;
    private String password;

    public static void main(String[] args) {

        SpringApplication.run(TrackerApp.class, args);
    }

    @Bean
    public EventStore eventStore() {
        return EventStoreBuilder.newBuilder()
                                .singleNodeAddress("host", 1113)
                                .userCredentials("user", "password")
                                .build();
    }

}
