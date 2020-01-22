package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.shared.domain.AggregateRoot;
import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowId;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowTitle;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowYear;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class CreateShowCommandHandlerTest extends ShowModuleUnitTestCase {
    private CreateShowCommandHandler handler;

    @BeforeEach
    void setUp() {

    }

    @Test
    void it_should_create_a_show_and_publish_ShowCreatedEvent() {
        //given
        CreateShowCommand cmd = CreateShowCommandMother.random();
        handler = new CreateShowCommandHandler(new ShowCreator(this.eventBus));
        List<DomainEvent<? extends AggregateRoot>> events = ShowCreatedEventMother.fromCommand(cmd);

        //when
        handler.handle(cmd);

        //then
        shouldHavePublished(events);
    }

    @Test
    @DisplayName("")
    void it_should_call_ShowCreator_with_ShowId_taken_from_command() {
        //given
        CreateShowCommand cmd = CreateShowCommandMother.random();
        ShowCreator creator = mock(ShowCreator.class);
        ShowId showId = ShowIdMother.from(cmd);
        ShowTitle showTitle = ShowTitleMother.from(cmd);
        ShowYear showYear = ShowYearMother.from(cmd);
        handler = new CreateShowCommandHandler(creator);
        //when
        handler.handle(cmd);
        //then
        verify(creator, times(1)).create(showId, showTitle, showYear);
    }
}