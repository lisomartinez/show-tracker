package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.tracker.show.domain.ShowYear;

public final class ShowYearMother {
    public static ShowYear from(CreateShowCommand cmd) {
        return ShowYear.create(cmd.year());
    }
}
