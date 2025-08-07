/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.stream.hub.constants.ApplicationConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * @author Mahmoud Hussein Tayem
 */
@RequestMapping("/stream-hub-events")
@RestController
public class StreamHubEventRestController extends BaseRestController {

	public StreamHubEventRestController(
		SessionManager sessionManager, WebSocketHandler webSocketHandler,
		WebClient.Builder webClientBuilder) {

		_sessionManager = sessionManager;
		_webSocketHandler = webSocketHandler;
		_webClientBuilder = webClientBuilder;
	}

	public List<JSONObject> getConfigurationEntryByObjectDefinitionId(
			String objectDefinitionId)
		throws JsonProcessingException {

		String token = _liferayOAuth2AccessTokenManager.getAuthorization(
			ApplicationConstants.OAUTH_SERVER_APPLICATION_NAME);

		WebClient client = _webClientBuilder.baseUrl(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).defaultHeader(
			"Authorization", token
		).build();

		Mono<String> responseMono = client.get(
		).uri(
			uriBuilder -> uriBuilder.path(
				ApplicationConstants.CONFIGURATION_ENDPOINT_RELATIVE_URL
			).queryParam(
				"filter", "objectDefinitionId eq '" + objectDefinitionId + "'"
			).build()
		).retrieve(
		).bodyToMono(
			String.class
		);

		String rawJSON = responseMono.block();

		JSONObject resultJSONObject = new JSONObject(rawJSON);

		if (resultJSONObject.has("totalCount") &&
			(resultJSONObject.getInt("totalCount") > 0)) {

			JSONArray itemsJSONArray = resultJSONObject.getJSONArray("items");

			JSONObject streamConfigurationJSONObject =
				itemsJSONArray.getJSONObject(0);

			String configurationString =
				streamConfigurationJSONObject.getString("configuration");

			JSONObject configurationJSONObject = new JSONObject(
				configurationString);

			JSONArray actionsListJSONArray =
				configurationJSONObject.optJSONArray("actionsList");

			if (actionsListJSONArray == null) {
				return List.of();
			}

			List<Object> rawList = actionsListJSONArray.toList();
			List<JSONObject> jsonList = new ArrayList<>();

			for (Object item : rawList) {
				if (item instanceof Map) {
					jsonList.add(new JSONObject((Map<?, ?>)item));
				}
			}

			return jsonList;
		}

		return List.of();
	}

	@PostMapping({"/add", "/delete", "/update", "/standalone"})
	public ResponseEntity<String> handleEventActions(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws JsonProcessingException {

		Map<String, Object> map = _objectMapper.readValue(json, Map.class);

		Map<String, Object> objectEntry = (Map<String, Object>)map.get(
			"objectEntry");

		String objectActionTriggerKey = map.getOrDefault(
			"objectActionTriggerKey", "standalone"
		).toString();

		String objectDefinitionId = map.get(
			"objectDefinitionId"
		).toString();

		List<JSONObject> configuration =
			getConfigurationEntryByObjectDefinitionId(objectDefinitionId);

		JSONObject foundJSONObject = null;

		for (JSONObject itemJSONObject : configuration) {
			if (Objects.equals(
					itemJSONObject.optString("type"), objectActionTriggerKey)) {

				foundJSONObject = itemJSONObject;

				break;
			}
		}

		if (foundJSONObject != null) {
			List<String> fields = List.of();
			List<String> roles = List.of();
			Map<String, Object> eventMessage = new HashMap<>();

			if (foundJSONObject.optJSONArray("roles") != null) {
				List<Object> rawRoles = foundJSONObject.getJSONArray(
					"roles"
				).toList();
				roles = new ArrayList<>();

				for (Object role : rawRoles) {
					roles.add(role.toString());
				}
			}

			if (foundJSONObject.optJSONArray("fields") != null) {
				List<Object> rawFields = foundJSONObject.getJSONArray(
					"fields"
				).toList();
				fields = new ArrayList<>();

				for (Object field : rawFields) {
					fields.add(field.toString());
				}
			}

			List<String> userIds = _sessionManager.getUsersIfInRoles(roles);

			Map<String, Object> values = (Map<String, Object>)objectEntry.get(
				"values");

			for (String field : fields) {
				if (objectEntry.get(field) != null) {
					eventMessage.put(field, objectEntry.get(field));
				}
				else if ((values != null) && (values.get(field) != null)) {
					eventMessage.put(field, values.get(field));
				}
			}

			_webSocketHandler.sendMessage(
				_objectMapper.writeValueAsString(eventMessage),
				foundJSONObject.optString("name", "unknown"), userIds,
				foundJSONObject.optBoolean("enableOfflineMessageQueue", false));
		}

		return ResponseEntity.ok("Ok");
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	private final ObjectMapper _objectMapper = new ObjectMapper();
	private final SessionManager _sessionManager;
	private final WebClient.Builder _webClientBuilder;
	private final WebSocketHandler _webSocketHandler;

}