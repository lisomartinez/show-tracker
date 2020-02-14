package com.github.lmartinez84.showtracker.tracker.show.domain;

import com.github.lmartinez84.showtracker.tracker.show.application.show.create.CreateShowCommand;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowTitle;

public final class ShowTitleMother {
    public static ShowTitle from(CreateShowCommand cmd) {
        return ShowTitle.create(cmd.title());
    }
}
