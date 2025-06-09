package com.liferay.stream.hub.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.client.extension.util.spring.boot3.LiferayOAuth2ResourceServerEnableWebSecurity;

import com.liferay.stream.hub.dto.StreamMessage;
import com.liferay.stream.hub.types.MessageType;
import com.liferay.stream.hub.utils.StreamMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final LiferayOAuth2ResourceServerEnableWebSecurity liferayOAuth2ResourceServerEnableWebSecurity;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PendingEvents pendingEvents;
    private final SessionManager sessionManager;


    @Autowired
    public WebSocketHandler( SessionManager sessionManager, LiferayOAuth2ResourceServerEnableWebSecurity liferayOAuth2ResourceServerEnableWebSecurity, PendingEvents pendingEvents) {
        this.sessionManager = sessionManager;
        this.liferayOAuth2ResourceServerEnableWebSecurity = liferayOAuth2ResourceServerEnableWebSecurity;
        this.pendingEvents = pendingEvents;

    }

    private void handleMissingMessageNotifications(String clientId) throws JsonProcessingException {

        List<Map<String,Object>> pendingNotifications =pendingEvents.getEventsCollection(clientId);

        pendingNotifications.stream().forEach(event -> {
            try {
                sendMessage(MessageType.Event,event.get("data").toString(),event.get("name").toString(),List.of(event.get("clientId").toString()),false);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public <T> void sendMessage(MessageType type, T data, String name, List<String> clientIds
            , Boolean enableOfflineMessageQueue) throws JsonProcessingException {

        StreamMessage<T> _message = StreamMessageUtil.create(type, name, data);

        String json = objectMapper.writeValueAsString(_message);

        clientIds.forEach(clientId -> {
            Set<WebSocketSession> sessions = sessionManager.getSessions(clientId);
            AtomicBoolean isOnline = new AtomicBoolean(false);

            sessions.forEach(session -> {
               try {
                   if (session.isOpen()) {
                       session.sendMessage(new TextMessage(json));
                       isOnline.set(true);
                   }
               }catch (Exception error){
                   error.printStackTrace();
               }
            });

            if (!isOnline.get() && enableOfflineMessageQueue) {
                pendingEvents.Insert(clientId,MessageType.Event,name,data);
            }

        });


    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        List<String> protocols = session.getHandshakeHeaders().get("sec-websocket-protocol");

        if (protocols == null || protocols.isEmpty()) {
            System.out.println("No Sec-WebSocket-Protocol header found.");
            System.out.println("Connection established for user: " + "Guest");
            sessionManager.addSession("Guest", session);
            return;
        }

        String subProtocol = protocols.get(0);

        Map<String, Object> claims;

        try {
            Jwt token =  liferayOAuth2ResourceServerEnableWebSecurity.jwtDecoder().decode(subProtocol);

            claims = token.getClaims();

        } catch (Exception e) {
            System.out.println("Token parsing failed: " + e.getMessage());
            session.close();
            return;
        }

        if (clientId == null) {
            System.out.println("clientId is null (Spring injection problem?)");
            session.close();
            return;
        }

        if (claims.get("client_id") != null && claims.get("client_id").equals(clientId)) {

            sessionManager.addSession(claims.get("sub").toString(), session);

            System.out.println("Connection established for user: " + claims.get("sub"));

            handleMissingMessageNotifications(claims.get("sub").toString());

        } else {

            System.out.println("Unauthorized: token client_id mismatch.");

            session.close();
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, Object> messageObj = objectMapper.readValue(payload, Map.class);

        MessageType type = MessageType.fromValue(messageObj.get("type").toString());

        switch (type) {
            default:{
                break;
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("WebSocket transport error: " + exception.getMessage());
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionManager.removeSession(session);
    }

    @Value("${liferay-stream-hub-etc-spring-boot-oauth-application-user-agent.oauth2.user.agent.client.id}")
    private String clientId;
}
