/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.socket;

import com.liferay.stream.hub.types.MessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

/**
 * @author Mahmoud Hussein Tayem
 */
@Component
public class PendingEvents {

	public List<Map<String, Object>> getEventsCollection(String clientId) {
		List<Map<String, Object>> result = new ArrayList<>();

		for (Map<String, Object> event : _eventsCollection) {
			Object eventClientId = event.get("clientId");

			if (clientId.equals(eventClientId)) {
				result.add(event);
			}
		}

		return result;
	}

	public void insert(
		String clientId, MessageType messageType, String name, Object data) {

		Map<String, Object> event = Map.of(
			"clientId", clientId, "data", data, "id",
			UUID.randomUUID(
			).toString(),
			"name", name, "type", messageType);

		_eventsCollection.add(event);
	}

	public void removePendingEvents(String clientId) {
		for (int i = 0; i < _eventsCollection.size(); i++) {
			Map<String, Object> event = _eventsCollection.get(i);

			Object eventClientId = event.get("clientId");

			if (eventClientId.equals(clientId)) {
				_eventsCollection.remove(i);
				i--;
			}
		}
	}

	private final List<Map<String, Object>> _eventsCollection =
		new CopyOnWriteArrayList<>();

}