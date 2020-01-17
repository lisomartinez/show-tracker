package com.github.lmartinez84.showtracker.shared.domain.bus.command;

public interface CommandBus {
    <T extends Command> void dispatch(T command);
}
