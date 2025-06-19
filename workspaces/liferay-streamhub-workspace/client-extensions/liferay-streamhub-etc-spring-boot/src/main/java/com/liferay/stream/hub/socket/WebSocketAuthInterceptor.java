/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.socket;

import java.util.List;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * @author Mahmoud Hussein Tayem
 */
@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

	@Override
	public void afterHandshake(
		ServerHttpRequest request, ServerHttpResponse response,
		WebSocketHandler wsHandler, Exception exception) {
	}

	@Override
	public boolean beforeHandshake(
			ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler wsHandler, Map<String, Object> attributes)
		throws Exception {

		List<String> protocols = request.getHeaders(
		).get(
			"Sec-WebSocket-Protocol"
		);

		if ((protocols != null) && !protocols.isEmpty()) {
			response.getHeaders(
			).add(
				"Sec-WebSocket-Protocol", protocols.get(0)
			);
		}

		return true;
	}

}