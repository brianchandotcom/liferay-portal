/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.client.extension.util.spring.boot3.LiferayOAuth2ResourceServerEnableWebSecurity;
import com.liferay.stream.hub.dto.StreamMessage;
import com.liferay.stream.hub.types.MessageType;
import com.liferay.stream.hub.utils.ListUtils;
import com.liferay.stream.hub.utils.StreamMessageUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author Mahmoud Hussein Tayem
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {

	@Autowired
	public WebSocketHandler(
		SessionManager sessionManager,
		LiferayOAuth2ResourceServerEnableWebSecurity
			liferayOAuth2ResourceServerEnableWebSecurity,
		PendingEvents pendingEvents) {

		_sessionManager = sessionManager;
		_liferayOAuth2ResourceServerEnableWebSecurity =
			liferayOAuth2ResourceServerEnableWebSecurity;
		_pendingEvents = pendingEvents;
	}

	@Override
	public void afterConnectionClosed(
			WebSocketSession session, CloseStatus status)
		throws Exception {

		_sessionManager.removeSession(session);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
		throws Exception {

		List<String> protocols = session.getHandshakeHeaders(
		).get(
			"sec-websocket-protocol"
		);

		if (ListUtils.isEmpty(protocols)) {
			System.out.println("No Sec-WebSocket-Protocol header found.");
			System.out.println("Connection established for user: Guest");
			_sessionManager.addSession("Guest", session);

			return;
		}

		String subprotocol = protocols.get(0);

		Map<String, Object> claims;

		try {
			Jwt token =
				_liferayOAuth2ResourceServerEnableWebSecurity.jwtDecoder(
				).decode(
					subprotocol
				);

			claims = token.getClaims();
		}
		catch (Exception exception) {
			System.out.println(
				"Token parsing failed: " + exception.getMessage());
			session.close();

			return;
		}

		if (_clientId == null) {
			System.out.println("clientId is null (Spring injection problem?)");
			session.close();

			return;
		}

		if ((claims.get("client_id") != null) &&
			Objects.equals(claims.get("client_id"), _clientId)) {

			_sessionManager.addSession(
				claims.get(
					"sub"
				).toString(),
				session);

			System.out.println(
				"Connection established for user: " + claims.get("sub"));

			_handleMissingMessageNotifications(
				claims.get(
					"sub"
				).toString());
		}
		else {
			System.out.println("Unauthorized: token client_id mismatch.");

			session.close();
		}
	}

	@Override
	public void handleTransportError(
			WebSocketSession session, Throwable errorThrowable)
		throws Exception {

		System.out.println(
			"WebSocket transport error: " + errorThrowable.getMessage());

		errorThrowable.printStackTrace();
	}

	public <T> void sendMessage(
			MessageType type, T data, String name, List<String> clientIds,
			Boolean enableOfflineMessageQueue)
		throws JsonProcessingException {

		StreamMessage<T> message = StreamMessageUtil.create(type, name, data);

		String json = _objectMapper.writeValueAsString(message);

		clientIds.forEach(
			clientId -> {
				Set<WebSocketSession> sessions = _sessionManager.getSessions(
					clientId);
				AtomicBoolean isOnline = new AtomicBoolean(false);

				sessions.forEach(
					session -> {
						try {
							if (session.isOpen()) {
								session.sendMessage(new TextMessage(json));
								isOnline.set(true);
							}
						}
						catch (Exception exception) {
							System.out.println(exception.getMessage());
						}
					});

				if (!isOnline.get() && enableOfflineMessageQueue) {
					_pendingEvents.insert(
						clientId, MessageType.Event, name, data);
				}
			});
	}

	private void _handleMissingMessageNotifications(String clientId) {
		List<Map<String, Object>> pendingNotifications =
			_pendingEvents.getEventsCollection(clientId);

		pendingNotifications.stream(
		).forEach(
			event -> {
				try {
					sendMessage(
						MessageType.Event,
						event.get(
							"data"
						).toString(),
						event.get(
							"name"
						).toString(),
						List.of(
							event.get(
								"clientId"
							).toString()),
						false);
				}
				catch (JsonProcessingException jsonProcessingException) {
					throw new RuntimeException(jsonProcessingException);
				}
			}
		);

		_pendingEvents.removePendingEvents(clientId);
	}

	@Value(
		"${liferay-stream-hub-etc-spring-boot-oauth-application-user-agent.oauth2.user.agent.client.id}"
	)
	private String _clientId;

	private final LiferayOAuth2ResourceServerEnableWebSecurity
		_liferayOAuth2ResourceServerEnableWebSecurity;
	private final ObjectMapper _objectMapper = new ObjectMapper();
	private final PendingEvents _pendingEvents;
	private final SessionManager _sessionManager;

}