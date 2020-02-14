package com.github.lmartinez84.showtracker.tracker.show.domain.show;

import com.github.lmartinez84.showtracker.shared.domain.IntValueObject;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.exception.NegativeShowYearException;

public final class ShowYear extends IntValueObject {
    private ShowYear(int value) {
        super(value);
    }

    public static ShowYear create(int value) {
        ensureIsValidYear(value);
        return new ShowYear(value);
    }

    private static void ensureIsValidYear(int value) {
        if (value <= 0) throw new NegativeShowYearException(value);
    }
}
