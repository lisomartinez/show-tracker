package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

class CreateShowCommandHandlerTest extends ShowModuleUnitTestCase {
    private CreateShowCommandHandler handler;

    @Test
    void it_should_create_a_show_and_publish_ShowCreatedEvent() {
        //given
        CreateShowCommand cmd = CreateShowCommandMother.random();
        handler = new CreateShowCommandHandler(new ShowCreator(this.eventBus));
        List<DomainEvent> events = ShowCreatedEventMother.fromCommand(cmd);

        //when
        handler.handle(cmd);

        //then
        shouldHavePublished(events);
    }
}