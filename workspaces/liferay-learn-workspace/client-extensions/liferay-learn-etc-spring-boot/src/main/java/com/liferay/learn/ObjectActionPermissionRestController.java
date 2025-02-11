/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nilton Vieira
 */
@RequestMapping("/object/action/permission")
@RestController
public class ObjectActionPermissionRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		_updatePermissions(
			jsonObject.getLong("classPK"),
			new JSONObject(
				get(
					_getAuthorization(),
					"/o/object-admin/v1.0/object-definitions/" +
						jsonObject.getLong("objectDefinitionId"))));

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private JSONArray _filterPermissions(
		JSONArray newPermissionsJSONArray,
		JSONArray currentPermissionsJSONArray) {

		List<String> sourceRoles = new ArrayList<>();

		for (int i = 0; i < newPermissionsJSONArray.length(); i++) {
			JSONObject jsonObject = newPermissionsJSONArray.getJSONObject(i);

			JSONArray actionIdsJSONArray = jsonObject.getJSONArray("actionIds");

			List<Object> list = actionIdsJSONArray.toList();

			list.removeIf(
				item -> !Arrays.asList(
					_ALLOWED_ACTION_IDS
				).contains(
					item
				));

			jsonObject.put("actionIds", list);

			sourceRoles.add(jsonObject.getString("roleName"));
		}

		for (int i = 0; i < currentPermissionsJSONArray.length(); i++) {
			JSONObject jsonObject = currentPermissionsJSONArray.getJSONObject(
				i);

			if (!sourceRoles.contains(jsonObject.getString("roleName"))) {
				newPermissionsJSONArray.put(
					new JSONObject(
					).put(
						"roleName", jsonObject.getString("roleName")
					).put(
						"actionIds", new JSONArray()
					));
			}
		}

		return newPermissionsJSONArray;
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-learn-etc-spring-boot-oauth-application-headless-server");
	}

	private JSONArray _getPermissionsJSONArray(
		long objectEntryId, String restContextPath) {

		return new JSONObject(
			get(
				_getAuthorization(),
				StringBundler.concat(
					restContextPath, "/", objectEntryId, "/permissions"))
		).getJSONArray(
			"items"
		);
	}

	private Map<Long, List<Object>> _getRelatedObjectEntries(
		JSONArray objectRelationshipsJSONArray, String restContextPath,
		long objectEntryId) {

		Map<Long, List<Object>> objectEntriesMap = new HashMap<>();

		for (int i = 0; i < objectRelationshipsJSONArray.length(); i++) {
			JSONObject objectRelationshipJSONObject =
				objectRelationshipsJSONArray.getJSONObject(i);

			if (Arrays.asList(
					_EXCLUDED_OBJECT_DEFINITION_EXTERNAL_REFERENCE_CODES
				).contains(
					objectRelationshipJSONObject.getString(
						"objectDefinitionExternalReferenceCode2")
				)) {

				continue;
			}

			JSONObject relatedObjectEntriesJSONObject = new JSONObject(
				get(
					_getAuthorization(),
					StringBundler.concat(
						restContextPath, "/", objectEntryId, "/",
						objectRelationshipJSONObject.getString("name"),
						"?fields=id&pageSize=500")));

			objectEntriesMap.put(
				objectRelationshipJSONObject.getLong("objectDefinitionId2"),
				relatedObjectEntriesJSONObject.getJSONArray(
					"items"
				).toList());
		}

		return objectEntriesMap;
	}

	private void _updatePermissions(
		long objectEntryId, JSONObject objectDefinitionJSONObject) {

		for (Map.Entry<Long, List<Object>> entry :
				_getRelatedObjectEntries(
					objectDefinitionJSONObject.getJSONArray(
						"objectRelationships"),
					objectDefinitionJSONObject.getString("restContextPath"),
					objectEntryId
				).entrySet()) {

			JSONObject relatedObjectDefinitionJSONObject = new JSONObject(
				get(
					_getAuthorization(),
					"/o/object-admin/v1.0/object-definitions/" +
						entry.getKey()));

			for (Object object : entry.getValue()) {
				Map<String, Object> map = (Map<String, Object>)object;

				put(
					_getAuthorization(),
					_filterPermissions(
						_getPermissionsJSONArray(
							objectEntryId,
							objectDefinitionJSONObject.getString(
								"restContextPath")),
						_getPermissionsJSONArray(
							GetterUtil.getLong(map.get("id")),
							relatedObjectDefinitionJSONObject.getString(
								"restContextPath"))
					).toString(),
					StringBundler.concat(
						relatedObjectDefinitionJSONObject.getString(
							"restContextPath"),
						"/", GetterUtil.getLong(map.get("id")),
						"/permissions"));

				_updatePermissions(
					GetterUtil.getLong(map.get("id")),
					relatedObjectDefinitionJSONObject);
			}
		}
	}

	private static final String[] _ALLOWED_ACTION_IDS = {
		"DELETE", "UPDATE", "VIEW", "PERMISSIONS"
	};

	private static final String[]
		_EXCLUDED_OBJECT_DEFINITION_EXTERNAL_REFERENCE_CODES = {
			"T4T14_ENROLLMENTS", "T4T14_QUIZ_QUESTION", "T4T14_QUIZ_ANSWER"
		};

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}