package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.tracker.show.application.CreateShowCommand;
import com.github.lmartinez84.showtracker.tracker.show.application.CreateShowCommandHandler;
import com.github.lmartinez84.showtracker.tracker.show.application.CreateShowCommandMother;
import com.github.lmartinez84.showtracker.tracker.show.application.ShowCreator;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowId;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowTitle;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowYear;
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
        bus.dispatch(cmd);
        verify(creator, times(1)).create(ShowId.create(cmd.id()), ShowTitle.create(cmd.title()), ShowYear.create(cmd.year()));
    }
}
