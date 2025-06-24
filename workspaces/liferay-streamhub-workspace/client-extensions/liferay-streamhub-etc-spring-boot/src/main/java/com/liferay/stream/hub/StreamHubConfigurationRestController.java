/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.client.dto.v1_0.Status;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.pagination.Pagination;
import com.liferay.object.admin.rest.client.resource.v1_0.ObjectActionResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

/**
 * @author Mahmoud Hussein Tayem
 */
@RequestMapping("/stream-hub-configurations")
@RestController
public class StreamHubConfigurationRestController extends BaseRestController {

	@PostMapping("/configure")
	public ResponseEntity<String> postConfigure(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		JSONObject configurationJSONObject = _getConfigurationJSONObject(json);

		long objectDefinitionId = configurationJSONObject.getLong(
			"objectDefinitionId");

		_unconfigure(objectDefinitionId);

		_configure(objectDefinitionId, _getTypes(configurationJSONObject));

		return ResponseEntity.ok("");
	}

	@PostMapping("/unconfigure")
	public ResponseEntity<String> postUnconfigure(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		JSONObject configurationJSONObject = _getConfigurationJSONObject(json);

		long objectDefinitionId = configurationJSONObject.getLong(
			"objectDefinitionId");

		_unconfigure(objectDefinitionId);

		return ResponseEntity.ok("");
	}

	private void _configure(long objectDefinitionId, List<String> types)
		throws Exception {

		ObjectActionResource objectActionResource = _getObjectActionResource();
		List<ObjectAction> objectActions = new ArrayList<>();

		Status status = new Status();

		status.setLabel(() -> "Never Ran");
		status.setCode(() -> 0);

		Map<String, String[]> actionConfigs = Map.of(
			"onAfterAdd",
			new String[] {
				"function#liferay-streamhub-etc-spring-boot-object-action-1",
				"Stream Events - On Add", ""
			},
			"onAfterUpdate",
			new String[] {
				"function#liferay-streamhub-etc-spring-boot-object-action-2",
				"Stream Events - On Update", ""
			},
			"onAfterDelete",
			new String[] {
				"function#liferay-streamhub-etc-spring-boot-object-action-3",
				"Stream Events - On Delete", ""
			},
			"standalone",
			new String[] {
				"function#liferay-streamhub-etc-spring-boot-object-action-4",
				"Stream Events - Standalone",
				"Error while executing Event Streaming for Standalone"
			});

		StringBuilder sb = new StringBuilder();

		for (String action : types) {
			if (!actionConfigs.containsKey(action)) {
				continue;
			}

			String[] config = actionConfigs.get(action);

			ObjectAction objectAction = new ObjectAction();

			objectAction.setObjectActionExecutorKey(() -> config[0]);

			StringBuilder finalSb = sb;

			objectAction.setExternalReferenceCode(
				() -> finalSb.append(
					"STREAM_"
				).append(
					objectDefinitionId
				).append(
					"_"
				).append(
					action
				).toString());

			objectAction.setLabel(() -> Map.of("en_US", config[1]));
			objectAction.setObjectActionTriggerKey(() -> action);
			objectAction.setName(() -> "stream" + objectDefinitionId + action);
			objectAction.setActive(() -> true);
			objectAction.setParameters(Collections::emptyMap);
			objectAction.setStatus(() -> status);

			if (!config[2].isEmpty()) {
				objectAction.setErrorMessage(() -> Map.of("en_US", config[2]));
			}

			objectActions.add(objectAction);
			sb = new StringBuilder();
		}

		objectActionResource.postObjectDefinitionObjectActionBatch(
			Long.valueOf(objectDefinitionId), "", objectActions);
	}

	private JSONObject _getConfigurationJSONObject(String json) {
		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = objectEntryJSONObject.getJSONObject(
			"values");

		return new JSONObject(valuesJSONObject.get("configuration"));
	}

	private ObjectActionResource _getObjectActionResource() {
		return ObjectActionResource.builder(
		).header(
			"Authorization",
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-streamhub-etc-spring-boot-oahs")
		).endpoint(
			_lxcDXPMainDomain, _lxcDXPServerProtocol
		).build();
	}

	private List<String> _getTypes(JSONObject configurationJSONObject) {
		List<String> types = new ArrayList<>();

		JSONArray objectActionsJSONArray = configurationJSONObject.getJSONArray(
			"objectAction");

		for (int i = 0; i < objectActionsJSONArray.length(); i++) {
			JSONObject objectActionJSONObject =
				objectActionsJSONArray.getJSONObject(i);

			types.add(objectActionJSONObject.getString("type"));
		}

		return types;
	}

	private void _unconfigure(long objectDefinitionId) throws Exception {
		ObjectActionResource objectActionResource = _getObjectActionResource();

		Page<ObjectAction> page =
			objectActionResource.getObjectDefinitionObjectActionsPage(
				objectDefinitionId, "", Pagination.of(0, 0), "");

		for (ObjectAction objectAction : page.getItems()) {
			String externalReferenceCode =
				objectAction.getExternalReferenceCode();

			if (externalReferenceCode.startsWith("STREAM_")) {
				objectActionResource.deleteObjectAction(objectAction.getId());
			}
		}
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}