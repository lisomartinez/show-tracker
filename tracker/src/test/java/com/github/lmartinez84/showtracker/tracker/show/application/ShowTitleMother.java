package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.tracker.show.domain.ShowTitle;

public final class ShowTitleMother {
    public static ShowTitle from(CreateShowCommand cmd) {
        return ShowTitle.create(cmd.title());
    }
}
