package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.tracker.show.application.show.create.CreateShowCommand;
import com.github.lmartinez84.showtracker.tracker.show.application.show.create.CreateShowCommandHandler;
import com.github.lmartinez84.showtracker.tracker.show.application.show.create.ShowCreator;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowIdMother;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowId;

public final class CreateShowCommandHandlerMother {
    public static CreateShowCommandHandler with(ShowCreator creator) {
        CreateShowCommand cmd = CreateShowCommandMother.random();
        ShowId showId = ShowIdMother.from(cmd);
        return new CreateShowCommandHandler(creator);
    }
}
