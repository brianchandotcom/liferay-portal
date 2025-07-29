/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.stream.hub.constants.ApplicationConstants;
import com.liferay.stream.hub.model.ObjectsPage;
import com.liferay.stream.hub.model.StreamActionListItem;
import com.liferay.stream.hub.model.StreamConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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

	public List<StreamActionListItem> getConfigurationEntryByObjectDefinitionId(
		String objectDefinitionId)
		throws JsonProcessingException {

		String token = _liferayOAuth2AccessTokenManager.getAuthorization(
			ApplicationConstants.OAUTH_SERVER_APPLICATION_NAME);

		WebClient client = _webClientBuilder.baseUrl(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).defaultHeader(
			"Authorization", token
		).build();

		Mono<ObjectsPage<StreamConfiguration>> responseMono = client.get(
		).uri(
			uriBuilder -> uriBuilder.path(
				ApplicationConstants.CONFIGURATION_ENDPOINT_RELATIVE_URL
			).queryParam(
				"filter", "objectDefinitionId eq '" + objectDefinitionId + "'"
			).build()
		).retrieve(
		).bodyToMono(
			new ParameterizedTypeReference<ObjectsPage<StreamConfiguration>>() {
			}
		);

		ObjectsPage<StreamConfiguration> objectsPage = responseMono.block();

		if (objectsPage.getTotalCount() > 0) {
			StreamConfiguration streamConfiguration = objectsPage.getItems(
			).get(
				0
			);
			ObjectMapper objectMapper = new ObjectMapper();

			Map<String, Object> configuration = objectMapper.readValue(
				streamConfiguration.getConfiguration(), Map.class);

			List<Map<String, Object>> actionsListMap =
				(List<Map<String, Object>>)configuration.get("actionsList");

			List<StreamActionListItem> streamActionsConfigurations =
				new ArrayList<>();

			for (Map<String, Object> action : actionsListMap) {
				String name = action.get(
					"name"
				).toString();
				String type = action.get(
					"type"
				).toString();
				List<String> roles = (List<String>)action.get("roles");
				List<String> fields = (List<String>)action.get("fields");

				boolean enableOfflineMessageQueue = false;

				if (action.get("enableOfflineMessageQueue") != null) {
					enableOfflineMessageQueue = (Boolean)action.get(
						"enableOfflineMessageQueue");
				}

				StreamActionListItem item = new StreamActionListItem(
					name, type, roles, fields, enableOfflineMessageQueue);

				streamActionsConfigurations.add(item);
			}

			return streamActionsConfigurations;
		}

		return null;
	}

	@PostMapping({"/add", "/delete", "/update", "/standalone"})
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
				_objectMapper.writeValueAsString(eventMessage), found.getName(),
				userIds, found.getEnableOfflineMessageQueue());
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
