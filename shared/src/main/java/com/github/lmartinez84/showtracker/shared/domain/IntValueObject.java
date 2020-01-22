package com.github.lmartinez84.showtracker.shared.domain;

public abstract class IntValueObject {
    private final int value;

    protected IntValueObject(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IntValueObject that = (IntValueObject) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
