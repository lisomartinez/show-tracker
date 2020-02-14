package com.github.lmartinez84.showtracker.shared.infrastructure;

import com.github.lmartinez84.showtracker.shared.infrastructure.bus.command.CommandBusSubscribers;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.command.InMemoryCommandBus;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.event.ReactiveEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = {
        ApplicationContext.class,
        CommandBusSubscribers.class,
        InMemoryCommandBus.class,
        ReactiveEventBus.class
})
public class IntegrationTest {
    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected CommandBusSubscribers subscribers;

    @Autowired
    protected InMemoryCommandBus commandBus;

    @Autowired
    protected ReactiveEventBus eventBus;
}
