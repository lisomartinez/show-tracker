package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.infrastructure.bus.command.CommandBusSubscribers;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.command.InMemoryCommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = {
        ApplicationContext.class,
        CommandBusSubscribers.class,
        InMemoryCommandBus.class})
public class IntegrationTest {
    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected CommandBusSubscribers subscribers;

    @Autowired
    protected InMemoryCommandBus bus;
}
