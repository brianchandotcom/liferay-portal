package com.liferay.stream.hub.socket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        List<String> protocols = request.getHeaders().get("Sec-WebSocket-Protocol");

        if (protocols != null && !protocols.isEmpty()) {
            response.getHeaders().add("Sec-WebSocket-Protocol", protocols.get(0));
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Exception exception) {

    }
}
