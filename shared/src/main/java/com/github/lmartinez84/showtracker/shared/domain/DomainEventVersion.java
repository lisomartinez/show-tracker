package com.github.lmartinez84.showtracker.shared.domain;

public final class DomainEventVersion extends AbstractLongValueObject {
    protected DomainEventVersion(long value) {
        super(value);
    }


    public static DomainEventVersion forNewDomainEvent() {
        return new DomainEventVersion(-1);
    }

    public static DomainEventVersion create(long version) {
        return new DomainEventVersion(version);
    }

    public DomainEventVersion next() {
        return new DomainEventVersion(value() + 1);
    }

    public long nextAsLong() {
        return value() + 1;
    }

    @Override
    public String toString() {
        return String.valueOf(super.value());
    }
}
