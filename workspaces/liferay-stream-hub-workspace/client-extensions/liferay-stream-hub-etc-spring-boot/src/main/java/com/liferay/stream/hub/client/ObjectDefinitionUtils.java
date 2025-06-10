/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.client;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.client.dto.v1_0.Status;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.pagination.Pagination;
import com.liferay.object.admin.rest.client.resource.v1_0.ObjectActionResource;
import com.liferay.object.admin.rest.client.resource.v1_0.ObjectDefinitionResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Mahmoud Hussein Tayem
 */
@Component
public class ObjectDefinitionUtils {

	public void configureStreamEventsActions(
			String objectDefinitionId, List<String> actions)
		throws Exception {

		ObjectActionResource objectActionResource =
			ObjectActionResource.builder(
			).header(
				"Authorization",
				_liferayOAuth2AccessTokenManager.getAuthorization(
					_mainOauthServerApplicationName)
			).endpoint(
				_lxcDXPMainDomain, _lxcDXPServerProtocol
			).build();

		Status status = new Status();

		status.setLabel(() -> "Never Ran");
		status.setCode(() -> 0);

		Map<String, String[]> actionConfigs = Map.of(
			"onAfterAdd",
			new String[] {
				"function#liferay-stream-hub-event-add-etc-spring-boot",
				"Stream Events - On Add", ""
			},
			"onAfterUpdate",
			new String[] {
				"function#liferay-stream-hub-event-update-etc-spring-boot",
				"Stream Events - On Update", ""
			},
			"onAfterDelete",
			new String[] {
				"function#liferay-stream-hub-event-delete-etc-spring-boot",
				"Stream Events - On Delete", ""
			},
			"standalone",
			new String[] {
				"function#liferay-stream-hub-event-standalone-etc-spring-boot",
				"Stream Events - Standalone",
				"Error while executing Event Streaming for Standalone"
			});

		StringBuilder sb = new StringBuilder();

		for (String action : actions) {
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

			objectActionResource.postObjectDefinitionObjectAction(
				Long.valueOf(objectDefinitionId), objectAction);
			sb = new StringBuilder();
		}
	}

	public List<Map<String, Object>> getObjectDefinitionsItems()
		throws Exception {

		ObjectDefinitionResource objectDefinitionResource =
			ObjectDefinitionResource.builder(
			).header(
				"Authorization",
				_liferayOAuth2AccessTokenManager.getAuthorization(
					_mainOauthServerApplicationName)
			).endpoint(
				_lxcDXPMainDomain, _lxcDXPServerProtocol
			).build();

		Page<ObjectDefinition> page =
			objectDefinitionResource.getObjectDefinitionsPage(
				"", null, "", Pagination.of(0, 0), "");

		List<Map<String, Object>> result = new ArrayList<>();

		for (ObjectDefinition item : page.getItems()) {
			if (item.getSystem() || Objects.equals(item.getScope(), "site")) {
				continue;
			}

			Map<String, Object> objectDefinition = Map.of(
				"externalReferenceCode", item.getExternalReferenceCode(), "id",
				item.getId(), "name", item.getName());

			List<Map<String, Object>> objectFields = new ArrayList<>();

			for (ObjectField field : item.getObjectFields()) {
				if (field.getSystem()) {
					continue;
				}

				Map<String, Object> objectField = Map.of(
					"businessType", field.getBusinessType(),
					"externalReferenceCode", field.getExternalReferenceCode(),
					"id", field.getId(), "name", field.getName());

				objectFields.add(objectField);
			}

			objectDefinition.put("objectFields", objectFields);

			result.add(objectDefinition);
		}

		return result;
	}

	public void removeConfigObjectActions(String objectDefinitionId)
		throws Exception {

		ObjectActionResource objectActionResource =
			ObjectActionResource.builder(
			).header(
				"Authorization",
				_liferayOAuth2AccessTokenManager.getAuthorization(
					_mainOauthServerApplicationName)
			).endpoint(
				_lxcDXPMainDomain, _lxcDXPServerProtocol
			).build();

		Page<ObjectAction> page =
			objectActionResource.getObjectDefinitionObjectActionsPage(
				Long.valueOf(objectDefinitionId), "", Pagination.of(0, 0), "");

		List<Long> toDelete = new ArrayList<>();

		for (ObjectAction objectAction : page.getItems()) {
			if (objectAction.getExternalReferenceCode(
				).startsWith(
					"STREAM_"
				)) {

				toDelete.add(objectAction.getId());
			}
		}

		for (Long actionId : toDelete) {
			objectActionResource.deleteObjectAction(actionId);
		}
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Value("${main.liferay.server.oauth.application}")
	private String _mainOauthServerApplicationName;

}