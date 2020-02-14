package com.github.lmartinez84.showtracker.tracker.show.domain.show.exception;

public final class BlankShowTitleException extends RuntimeException {
    public BlankShowTitleException() {
        super("show title may not be empty or contain only whitespaces");
    }
}
