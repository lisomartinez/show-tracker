package com.github.lmartinez84.showtracker.tracker.show.domain;

import com.github.lmartinez84.showtracker.tracker.show.application.show.create.CreateShowCommand;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowYear;

public final class ShowYearMother {
    public static ShowYear from(CreateShowCommand cmd) {
        return ShowYear.create(cmd.year());
    }
}
