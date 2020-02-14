package com.github.lmartinez84.showtracker.shared.infrastructure.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.msemys.esjc.ResolvedEvent;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
public final class EventStoreDeserializer {
    private final ObjectMapper objectMapper;

    public EventStoreDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, Serializable> deserialize(ResolvedEvent e) {
        HashMap<String, Serializable> domainEventField = new HashMap<>();
        domainEventField.put("meta", new HashMap<>());
        try {
            HashMap<String, Serializable> dataMap = new HashMap<>();
            String eventString = new String(e.originalEvent().data);
            JsonNode jsonNode = objectMapper.readTree(eventString);
            JsonNode data = jsonNode.get("data");
            String id = data.get("id").textValue();
            dataMap.put("id", id);
            String type = data.get("type").textValue();
            dataMap.put("type", type);
            String occurredOn = data.get("occurred_on").textValue();
            dataMap.put("occurred_on", occurredOn);
            String attributes = data.get("attributes").toString();
            HashMap<String, Serializable> attributesMap = objectMapper.readValue(attributes, new TypeReference<>() {
            });
            dataMap.put("attributes", attributesMap);
            domainEventField.put("data", dataMap);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return domainEventField;


    }
}
