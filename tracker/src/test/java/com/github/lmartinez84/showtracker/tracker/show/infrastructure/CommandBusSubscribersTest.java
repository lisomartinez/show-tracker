package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.domain.bus.command.Command;
import com.github.lmartinez84.showtracker.shared.infrastructure.bus.command.CommandBusSubscribers;
import com.github.lmartinez84.showtracker.tracker.show.application.CreateShowCommand;
import com.github.lmartinez84.showtracker.tracker.show.application.CreateShowCommandMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.ReplayProcessor;
import reactor.test.StepVerifier;

@SpringBootTest(classes = {ApplicationContext.class, CommandBusSubscribers.class})
@ActiveProfiles("test")
class CommandBusSubscribersTest {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private CommandBusSubscribers subscribers;

    @Test
    @DisplayName("")
    void it_should_get_Publisher_for_certaint_command_event() throws InterruptedException {
        //given
        ReplayProcessor<Command> publisher = subscribers.getPublisherFor(CreateShowCommand.class);
        CreateShowCommand cmd = CreateShowCommandMother.random();
        //when
        publisher.onNext(cmd);
        publisher.onComplete();
        //then
        StepVerifier.create(publisher).expectNext(cmd).verifyComplete();
    }
}