package com.github.lmartinez84.showtracker.shared.domain;

import javax.validation.constraints.NotNull;

public abstract class StringValueObject {
    private final String value;

    protected StringValueObject(@NotNull String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StringValueObject that = (StringValueObject) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }


}
