package com.github.lmartinez84.showtracker.shared.domain.bus.command;

public class CommandHandlerExecutionError extends RuntimeException {
    public CommandHandlerExecutionError(Throwable cause) {
        super(cause);
    }
}
