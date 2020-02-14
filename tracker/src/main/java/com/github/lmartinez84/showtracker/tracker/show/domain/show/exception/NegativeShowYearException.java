package com.github.lmartinez84.showtracker.tracker.show.domain.show.exception;

public final class NegativeShowYearException extends RuntimeException {
    public NegativeShowYearException(int value) {
        super("a Show's year may not be negative. actual=" + value);
    }
}
