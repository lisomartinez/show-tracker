package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.infrastructure.store.EventDeserializer;
import com.github.lmartinez84.showtracker.tracker.show.domain.event.ShowCreatedEvent;

import java.util.Map;

public final class ShowCreatedEventDeserializer implements EventDeserializer<ShowCreatedEvent> {
    @Override
    public ShowCreatedEvent deserialize(Map<String, Map<String, String>> json) {
        Map<String, String> data = json.get("event_data");
        String id = data.get("id");
        String title = data.get("title");
        int year = Integer.parseInt(data.get("year"));
        return new ShowCreatedEvent(id, title, year);
    }
}
