package com.github.lmartinez84.showtracker.shared.domain;

public final class AggregateVersion extends AbstractLongValueObject {
    protected AggregateVersion(long value) {
        super(value);
    }

    public static AggregateVersion forNewAggregate() {
        return new AggregateVersion(-1);
    }

    public static AggregateVersion create(long version) {
        return new AggregateVersion(version);
    }

    public AggregateVersion next() {
        return new AggregateVersion(this.value() + 1);
    }

    @Override
    public String toString() {
        return String.valueOf(super.value());
    }
}
