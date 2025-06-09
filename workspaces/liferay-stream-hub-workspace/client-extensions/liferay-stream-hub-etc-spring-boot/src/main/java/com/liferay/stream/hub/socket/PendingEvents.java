package com.liferay.stream.hub.socket;

import com.liferay.stream.hub.types.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Component
public class PendingEvents {

    private final List<Map<String, Object>> eventsCollection = new CopyOnWriteArrayList<>();

    public void Insert(String clientId, MessageType messageType, String name, Object data) {
        Map<String, Object> event = new HashMap<>();
        event.put("id", UUID.randomUUID().toString());
        event.put("clientId", clientId);
        event.put("type", messageType);
        event.put("name", name);
        event.put("data", data);

        eventsCollection.add(event);
    }


    public List<Map<String, Object>> getEventsCollection(String clientId) {
        return eventsCollection.stream().filter(event -> clientId.equals(event.get("clientId"))).collect(Collectors.toUnmodifiableList());
    }

    public void removePendingEvents(String from, String to) {
        eventsCollection.removeIf(event ->
                from.equals(event.get("from")) &&
                        to.equals(event.get("to"))
        );
    }
}
