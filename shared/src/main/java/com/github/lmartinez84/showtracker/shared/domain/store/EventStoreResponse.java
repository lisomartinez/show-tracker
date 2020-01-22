package com.github.lmartinez84.showtracker.shared.domain.store;

public final class EventStoreResponse {
    private final String snapshot;
    private final String data;

    public EventStoreResponse(String snapshot, String data) {
        this.snapshot = snapshot;
        this.data = data;
    }

    public String snapshot() {
        return snapshot;
    }

    public String data() {
        return data;
    }
}
