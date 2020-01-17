package com.github.lmartinez84.showtracker.shared.domain.bus.command;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
