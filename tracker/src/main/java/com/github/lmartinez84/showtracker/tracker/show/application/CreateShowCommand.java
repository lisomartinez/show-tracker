package com.github.lmartinez84.showtracker.tracker.show.application;

import com.github.lmartinez84.showtracker.shared.domain.bus.command.Command;

public final class CreateShowCommand implements Command {
    private final String id;

    public CreateShowCommand(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
