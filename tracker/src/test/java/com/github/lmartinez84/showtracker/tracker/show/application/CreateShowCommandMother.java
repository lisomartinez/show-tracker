package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.tracker.show.domain.ShowId;

public class CreateShowCommandMother {

    public static CreateShowCommand random() {
        ShowId id = ShowIdMother.random();
        return new CreateShowCommand(id.value());
    }
}
