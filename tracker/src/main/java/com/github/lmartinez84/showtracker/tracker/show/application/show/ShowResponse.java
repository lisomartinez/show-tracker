package com.github.lmartinez84.showtracker.tracker.show.application.show;

public final class ShowResponse implements Response {
    private final String title;

    private ShowResponse(String title) {
        this.title = title;
    }

    public static ShowResponse create(String title) {
        return new ShowResponse(title);
    }

    public String title() {
        return title;
    }
}
