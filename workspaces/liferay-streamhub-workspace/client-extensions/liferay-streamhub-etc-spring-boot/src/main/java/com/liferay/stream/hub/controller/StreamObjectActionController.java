/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.stream.hub.client.ObjectDefinitionUtils;
import com.liferay.stream.hub.client.StreamConfigurationResource;
import com.liferay.stream.hub.dto.StreamActionListItem;
import com.liferay.stream.hub.socket.SessionManager;
import com.liferay.stream.hub.socket.WebSocketHandler;
import com.liferay.stream.hub.types.MessageType;
import com.liferay.stream.hub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mahmoud Hussein Tayem
 */
@RequestMapping("/actions/stream")
@RestController
public class StreamObjectActionController {

	public StreamObjectActionController(
		ObjectDefinitionUtils objectDefinitionUtils,
		StreamConfigurationResource streamConfigurationResource,
		WebSocketHandler webSocketHandler, SessionManager sessionManager) {

		_objectDefinitionUtils = objectDefinitionUtils;
		_streamConfigurationResource = streamConfigurationResource;
		_webSocketHandler = webSocketHandler;
		_sessionManager = sessionManager;
	}

	@PostMapping("/config/set")
	public ResponseEntity<String> addStreamConfig(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		Map<String, Object> map = _objectMapper.readValue(json, Map.class);

		Map<String, Object> objectEntry = (Map<String, Object>)map.get(
			"objectEntry");

		Map<String, Object> values = (Map<String, Object>)objectEntry.get(
			"values");

		Map<String, Object> configuration = _objectMapper.readValue(
			values.get(
				"configuration"
			).toString(),
			Map.class);

		String objectDefinitionId = configuration.get(
			"objectId"
		).toString();
		List<Map<String, Object>> actionsList =
			(List<Map<String, Object>>)configuration.get("actionsList");

		List<String> actions = new ArrayList<>();

		for (Map<String, Object> action : actionsList) {
			actions.add(
				action.get(
					"type"
				).toString());
		}

		_objectDefinitionUtils.removeConfigObjectActions(objectDefinitionId);
		_objectDefinitionUtils.configureStreamEventsActions(
			objectDefinitionId, actions);

		return ResponseEntity.ok("Ok");
	}

	@PostMapping(
		{"/event/add", "/event/delete", "/event/update", "/event/standalone"}
	)
	public ResponseEntity<String> handleEventActions(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws JsonProcessingException {

		Map<String, Object> map = _objectMapper.readValue(json, Map.class);

		Map<String, Object> objectEntry = (Map<String, Object>)map.get(
			"objectEntry");

		String objectActionTriggerKey = "standalone";

		if (map.containsKey("objectActionTriggerKey")) {
			objectActionTriggerKey = map.get(
				"objectActionTriggerKey"
			).toString();
		}

		String objectDefinitionId = map.get(
			"objectDefinitionId"
		).toString();

		List<StreamActionListItem> configuration =
			_streamConfigurationResource.
				getConfigurationEntryByObjectDefinitionId(objectDefinitionId);

		StreamActionListItem found = null;

		for (StreamActionListItem item : configuration) {
			if (Objects.equals(item.getType(), objectActionTriggerKey)) {
				found = item;

				break;
			}
		}

		if (found != null) {
			Map<String, Object> eventMessage = new HashMap<>();
			List<String> userIds = _sessionManager.getUsersIfInRoles(
				found.getRoles());
			Map<String, Object> values = (Map<String, Object>)objectEntry.get(
				"values");

			for (String field : found.getFields()) {
				if (objectEntry.get(field) != null) {
					eventMessage.put(field, objectEntry.get(field));
				}
				else if (values.get(field) != null) {
					eventMessage.put(field, values.get(field));
				}
			}

			_webSocketHandler.sendMessage(
				MessageType.Event, JsonUtils.toJSON(eventMessage),
				found.getName(), userIds, found.getEnableOfflineMessageQueue());
		}

		return ResponseEntity.ok("Ok");
	}

	@PostMapping("/config/remove")
	public ResponseEntity<String> removeStreamConfig(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		Map<String, Object> map = _objectMapper.readValue(json, Map.class);

		Map<String, Object> objectEntry = (Map<String, Object>)map.get(
			"objectEntry");

		Map<String, Object> values = (Map<String, Object>)objectEntry.get(
			"values");

		Map<String, Object> configuration = _objectMapper.readValue(
			values.get(
				"configuration"
			).toString(),
			Map.class);

		String objectDefinitionId = configuration.get(
			"objectId"
		).toString();
		List<Map<String, Object>> actionsList =
			(List<Map<String, Object>>)configuration.get("actionsList");

		List<String> actions = new ArrayList<>();

		for (Map<String, Object> action : actionsList) {
			actions.add(
				action.get(
					"type"
				).toString());
		}

		_objectDefinitionUtils.removeConfigObjectActions(objectDefinitionId);

		return ResponseEntity.ok("Ok");
	}

	private final ObjectDefinitionUtils _objectDefinitionUtils;
	private final ObjectMapper _objectMapper = new ObjectMapper();
	private final SessionManager _sessionManager;
	private final StreamConfigurationResource _streamConfigurationResource;
	private final WebSocketHandler _webSocketHandler;

}