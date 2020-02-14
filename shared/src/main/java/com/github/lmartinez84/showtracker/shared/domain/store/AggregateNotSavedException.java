package com.github.lmartinez84.showtracker.shared.domain.store;

public final class AggregateNotSavedException extends RuntimeException {
    public AggregateNotSavedException(String message) {
        super("Aggregate not saved due: " + message);
    }
}
