package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.tracker.show.application.show.create.CreateShowCommand;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowIdMother;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowId;

public class CreateShowCommandMother {

    public static CreateShowCommand random() {
        ShowId id = ShowIdMother.random();
        return new CreateShowCommand(id.value(), "title", 2010);
    }

}
