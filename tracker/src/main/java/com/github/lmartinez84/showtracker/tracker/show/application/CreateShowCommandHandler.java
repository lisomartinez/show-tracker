package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.shared.domain.bus.command.CommandHandler;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowId;

public final class CreateShowCommandHandler implements CommandHandler<CreateShowCommand> {
    private final ShowCreator creator;

    public CreateShowCommandHandler(ShowCreator creator) {
        this.creator = creator;
    }

    @Override
    public void handle(CreateShowCommand command) {
        ShowId id = ShowId.from(command.id());
        creator.create(id);
    }
}
