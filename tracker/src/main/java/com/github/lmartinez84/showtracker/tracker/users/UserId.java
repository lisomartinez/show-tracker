package com.github.lmartinez84.showtracker.tracker.users;

import com.github.lmartinez84.showtracker.shared.domain.Identifier;

public final class UserId extends Identifier {
    public UserId(String uid) {
        super(uid);
        ensureIsValid(uid);
    }

}
