package com.liferay.stream.hub.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class SocketServerConfig implements WebSocketConfigurer {

    private final WebSocketAuthInterceptor webSocketAuthInterceptor;
    private final WebSocketHandler webSocketHandler;

    @Value("${socket.path}")
    private String socketPath;

    @Autowired
    public SocketServerConfig(WebSocketHandler webSocketHandler,
                              WebSocketAuthInterceptor webSocketAuthInterceptor) {
        this.webSocketHandler = webSocketHandler;
        this.webSocketAuthInterceptor = webSocketAuthInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("Socket path: " + socketPath);
        registry.addHandler(webSocketHandler, socketPath)
                .setAllowedOrigins("*")
                .addInterceptors(webSocketAuthInterceptor);
    }
}
