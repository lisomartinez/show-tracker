package com.github.lmartinez84.showtracker.tracker.show.domain;

import com.github.lmartinez84.showtracker.shared.domain.StringValueObject;
import com.github.lmartinez84.showtracker.tracker.show.domain.exception.BlankShowTitleException;

import javax.validation.constraints.NotNull;

public final class ShowTitle extends StringValueObject {
    private ShowTitle(@NotNull String value) {
        super(value);
    }

    public static ShowTitle create(String value) {
        ensureValidTitle(value);
        return new ShowTitle(value);
    }

    private static void ensureValidTitle(String value) {
        if (value.isBlank()) throw new BlankShowTitleException();
    }
}
