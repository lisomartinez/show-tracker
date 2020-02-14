package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.infrastructure.IntegrationTest;
import com.github.lmartinez84.showtracker.tracker.show.application.CreateShowCommandMother;
import com.github.lmartinez84.showtracker.tracker.show.application.show.create.CreateShowCommand;
import com.github.lmartinez84.showtracker.tracker.show.application.show.create.CreateShowCommandHandler;
import com.github.lmartinez84.showtracker.tracker.show.application.show.create.ShowCreator;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowId;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowTitle;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowYear;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import(CreateShowCommandHandler.class)
class CommandBusTest extends IntegrationTest {
    @MockBean
    private ShowCreator creator;

    @Test
    @DisplayName("it should call creator.create with a show id taken from command id when create command is received")
    void it_should_call_creator_when_command_is_received() {
        CreateShowCommand cmd = CreateShowCommandMother.random();
        commandBus.dispatch(cmd);
        verify(creator, times(1)).create(ShowId.create(cmd.id()),
                                         ShowTitle.create(cmd.title()),
                                         ShowYear.create(cmd.year()));
    }
}
