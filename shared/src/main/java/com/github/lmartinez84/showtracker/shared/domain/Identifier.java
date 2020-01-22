package com.github.lmartinez84.showtracker.shared.domain;

import java.io.Serializable;
import java.util.UUID;

public abstract class Identifier implements Serializable {
    protected final String value;

    public Identifier(String value) {
        ensureIsValid(value);
        this.value = value;
    }

    protected Identifier() {
        this.value = null;
    }

    public static String newEventId() {
        return createRandomUUID();
    }

    private static String createRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String newCommandId() {
        return createRandomUUID();
    }


    protected void ensureIsValid(String uid) {
        try {
            //noinspection ResultOfMethodCallIgnored
            UUID.fromString(uid);
        } catch (RuntimeException ex) {
            throw new InvalidUidException(uid);
        }

    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Identifier that = (Identifier) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
