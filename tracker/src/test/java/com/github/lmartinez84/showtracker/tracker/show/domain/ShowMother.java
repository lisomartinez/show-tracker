package com.github.lmartinez84.showtracker.tracker.show.domain;

import com.github.lmartinez84.showtracker.tracker.show.domain.show.Show;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowId;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowTitle;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowYear;

public final class ShowMother {
    public static Show from(String id, String atitle, int year) {
        return Show.create(ShowId.create(id),
                           ShowTitle.create(atitle),
                           ShowYear.create(year));
    }

    public static Show random() {
        return from(ShowId.newAggregateId(), "aTitle", 2010);
    }

}
