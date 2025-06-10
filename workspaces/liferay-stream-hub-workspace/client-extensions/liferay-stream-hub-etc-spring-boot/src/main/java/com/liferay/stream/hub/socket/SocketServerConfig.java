/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Mahmoud Hussein Tayem
 */
@Configuration
@EnableWebSocket
public class SocketServerConfig implements WebSocketConfigurer {

	@Autowired
	public SocketServerConfig(
		WebSocketHandler webSocketHandler,
		WebSocketAuthInterceptor webSocketAuthInterceptor) {

		_webSocketHandler = webSocketHandler;
		_webSocketAuthInterceptor = webSocketAuthInterceptor;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("Socket path: " + _socketPath);
		registry.addHandler(
			_webSocketHandler, _socketPath
		).setAllowedOrigins(
			"*"
		).addInterceptors(
			_webSocketAuthInterceptor
		);
	}

	@Value("${socket.path}")
	private String _socketPath;

	private final WebSocketAuthInterceptor _webSocketAuthInterceptor;
	private final WebSocketHandler _webSocketHandler;

}