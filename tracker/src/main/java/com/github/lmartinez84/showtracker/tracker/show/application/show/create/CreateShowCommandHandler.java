package com.github.lmartinez84.showtracker.tracker.show.application.show.create;

import com.github.lmartinez84.showtracker.shared.domain.bus.command.CommandHandler;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowId;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowTitle;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowYear;
import org.springframework.stereotype.Service;

@Service
public class CreateShowCommandHandler implements CommandHandler<CreateShowCommand> {
    private final ShowCreator creator;

    public CreateShowCommandHandler(ShowCreator creator) {
        this.creator = creator;
    }

    @Override
    public void handle(CreateShowCommand command) {
        ShowId id = ShowId.create(command.id());
        ShowTitle title = ShowTitle.create(command.title());
        ShowYear year = ShowYear.create(command.year());
        creator.create(id, title, year);
    }
}
