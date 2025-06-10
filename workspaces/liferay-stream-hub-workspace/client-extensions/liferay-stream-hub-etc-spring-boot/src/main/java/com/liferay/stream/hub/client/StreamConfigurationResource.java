/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.stream.hub.dto.ObjectsPage;
import com.liferay.stream.hub.dto.StreamActionListItem;
import com.liferay.stream.hub.dto.StreamConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * @author Mahmoud Hussein Tayem
 */
@Component
public class StreamConfigurationResource {

	public StreamConfigurationResource(WebClient.Builder webClientBuilder) {
		_webClientBuilder = webClientBuilder;
	}

	public List<StreamActionListItem> getConfigurationEntryByObjectDefinitionId(
			String objectDefinitionId)
		throws JsonProcessingException {

		String token = _liferayOAuth2AccessTokenManager.getAuthorization(
			_mainOauthServerApplicationName);

		WebClient client = _webClientBuilder.baseUrl(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).defaultHeader(
			"Authorization", token
		).build();

		Mono<ObjectsPage<StreamConfiguration>> responseMono = client.get(
		).uri(
			uriBuilder -> uriBuilder.path(
				"/o/c/streamhubconfigurations"
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

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Value("${main.liferay.server.oauth.application}")
	private String _mainOauthServerApplicationName;

	private final WebClient.Builder _webClientBuilder;

}