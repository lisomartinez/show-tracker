package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.tracker.show.ShowModuleUnitTestCase;
import com.github.lmartinez84.showtracker.tracker.show.application.show.create.CreateShowCommand;
import com.github.lmartinez84.showtracker.tracker.show.application.show.create.CreateShowCommandHandler;
import com.github.lmartinez84.showtracker.tracker.show.application.show.create.ShowCreator;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowCreatedEventMother;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowIdMother;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowTitleMother;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowYearMother;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.Show;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowId;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowTitle;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowYear;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.*;

class CreateShowCommandHandlerTest extends ShowModuleUnitTestCase {
    private CreateShowCommandHandler handler;

    @Test
    void it_should_create_a_show_and_publish_ShowCreatedEvent() {
        CreateShowCommand cmd = CreateShowCommandMother.random();
        Flux<DomainEvent> expectedFluxOfEvents = ShowCreatedEventMother.fluxOfSingleEvent();
        when(this.showRepository.save(any(Show.class))).thenReturn(expectedFluxOfEvents);
        handler = CreateShowCommandHandlerMother.with(new ShowCreator(this.eventBus, this.showRepository));

        handler.handle(cmd);

        shouldHavePublished(expectedFluxOfEvents);
    }

    @Test
    void it_should_call_ShowCreator_with_ShowId_taken_from_command() {
        CreateShowCommand cmd = CreateShowCommandMother.random();
        ShowCreator creator = mock(ShowCreator.class);
        ShowId showId = ShowIdMother.from(cmd);
        ShowTitle showTitle = ShowTitleMother.from(cmd);
        ShowYear showYear = ShowYearMother.from(cmd);
        handler = new CreateShowCommandHandler(creator);

        handler.handle(cmd);

        verify(creator, times(1)).create(showId, showTitle, showYear);
    }
}