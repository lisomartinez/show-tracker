package com.github.lmartinez84.showtracker.shared.infrastructure.bus.command;

import com.github.lmartinez84.showtracker.shared.domain.bus.command.Command;
import com.github.lmartinez84.showtracker.shared.domain.bus.command.CommandBus;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ReplayProcessor;

@Service
@DependsOn("subscribers")
public class InMemoryCommandBus implements CommandBus {
    private final Subscribers subscribers;

    public InMemoryCommandBus(Subscribers subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public void dispatch(Command command) {
        ReplayProcessor<Command> publisher = subscribers.searchPublisher(command.getClass());
        publisher.onNext(command);
    }
}
