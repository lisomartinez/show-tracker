package com.github.lmartinez84.showtracker.tracker.show.domain;

import com.github.lmartinez84.showtracker.tracker.show.application.show.create.CreateShowCommand;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowId;

import java.util.UUID;

public class ShowIdMother {

    public static ShowId random() {
        return new ShowId(UUID.randomUUID().toString());
    }

    public static ShowId from(CreateShowCommand cmd) {
        return ShowId.create(cmd.id());
    }
}
