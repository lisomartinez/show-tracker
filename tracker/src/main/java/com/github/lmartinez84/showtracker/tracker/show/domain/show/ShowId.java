package com.github.lmartinez84.showtracker.tracker.show.domain.show;

import com.github.lmartinez84.showtracker.shared.domain.Identifier;

public final class ShowId extends Identifier {
    public ShowId(String value) {
        super(value);
    }

    public static ShowId create(String value) {
        return new ShowId(value);
    }
}
