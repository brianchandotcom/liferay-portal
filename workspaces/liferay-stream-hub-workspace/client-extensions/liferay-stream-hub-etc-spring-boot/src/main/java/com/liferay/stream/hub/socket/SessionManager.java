/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.socket;

import com.liferay.stream.hub.client.UserResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author Mahmoud Hussein Tayem
 */
@Component
public class SessionManager {

	public SessionManager(UserResource userResource) {
		_userResource = userResource;
	}

	public void addSession(String userId, WebSocketSession session)
		throws Exception {

		if (userId.equals("Guest")) {
			_userRoles.put(userId, List.of("Guest"));
		}
		else {
			_userRoles.put(
				userId,
				_userResource.getUserAccountRoles(Long.valueOf(userId)));
		}

		_userSessions.computeIfAbsent(
			userId, k -> new CopyOnWriteArraySet<>()
		).add(
			session
		);
	}

	public Map<String, Set<WebSocketSession>> getAllSessions() {
		return Collections.unmodifiableMap(_userSessions);
	}

	public Set<WebSocketSession> getSessions(String userId) {
		return _userSessions.getOrDefault(userId, Collections.emptySet());
	}

	public List<String> getUsersIfInRoles(List<String> roles) {
		List<String> userIds = new ArrayList<>();

		_userRoles.forEach(
			(userId, userRoles) -> {
				if (userRoles.stream(
					).anyMatch(
						roles::contains
					)) {

					userIds.add(userId);
				}
			});

		return userIds;
	}

	public boolean isOnline(String userId) {
		Set<WebSocketSession> sessions = _userSessions.get(userId);

		if ((sessions != null) && !sessions.isEmpty()) {
			return true;
		}

		return false;
	}

	public void removeSession(WebSocketSession sessionToRemove) {
		_userSessions.entrySet(
		).removeIf(
			entry -> {
				entry.getValue(
				).removeIf(
					session -> Objects.equals(
						session.getId(), sessionToRemove.getId())
				);

				return entry.getValue(
				).isEmpty();
			}
		);
	}

	private final UserResource _userResource;
	private final Map<String, List<String>> _userRoles =
		new ConcurrentHashMap<>();
	private final Map<String, Set<WebSocketSession>> _userSessions =
		new ConcurrentHashMap<>();

}