package com.liferay.stream.hub.socket;


import com.liferay.stream.hub.client.UserResource;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class SessionManager {

    private final Map<String, List<String>> userRoles = new ConcurrentHashMap<>();
    private final Map<String, Set<WebSocketSession>> userSessions = new ConcurrentHashMap<>();
    private final UserResource userResource;

    public SessionManager(UserResource userResource) {
        this.userResource = userResource;
    }

    public List<String> getUsersIfInRoles (List<String> roles) {

        List<String> userIds = new ArrayList<>();

        userRoles.forEach((userId,userRoles) -> {
           if (userRoles.stream().anyMatch(roles::contains)) {
               userIds.add(userId);
           }
        });
        return userIds;
    }

    public void addSession(String userId, WebSocketSession session) throws Exception {

       if (userId.equals("Guest")) {
           userRoles.put(userId,List.of("Guest"));
       }else{
           userRoles.put(userId,userResource.getUserAccountRoles(Long.valueOf(userId)));
       }


        userSessions
                .computeIfAbsent(userId, k -> new CopyOnWriteArraySet<>())
                .add(session);
    }

    public boolean isOnline(String userId) {
        Set<WebSocketSession> sessions = userSessions.get(userId);
        return sessions != null && !sessions.isEmpty();
    }

    public void removeSession(WebSocketSession sessionToRemove) {
        userSessions.forEach((userId, sessions) -> {
            sessions.removeIf(session -> session.getId().equals(sessionToRemove.getId()));
        });

        userSessions.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }

    public Set<WebSocketSession> getSessions(String userId) {
        return userSessions.getOrDefault(userId, Collections.emptySet());
    }

    public Map<String, Set<WebSocketSession>> getAllSessions() {
        return Collections.unmodifiableMap(userSessions);
    }

}
