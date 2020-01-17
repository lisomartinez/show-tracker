package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.tracker.show.domain.ShowId;

import java.util.UUID;

public class ShowIdMother {

    public static ShowId random() {
        return new ShowId(UUID.randomUUID().toString());
    }
}
