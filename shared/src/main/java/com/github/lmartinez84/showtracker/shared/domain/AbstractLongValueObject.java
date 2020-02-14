package com.github.lmartinez84.showtracker.shared.domain;

import java.util.Objects;

public abstract class AbstractLongValueObject {
    private final long value;

    protected AbstractLongValueObject(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractLongValueObject that = (AbstractLongValueObject) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
