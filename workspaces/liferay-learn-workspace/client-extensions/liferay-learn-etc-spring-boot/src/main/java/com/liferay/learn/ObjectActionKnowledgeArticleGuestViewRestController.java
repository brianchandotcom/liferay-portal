/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.net.URI;

import java.util.List;
import java.util.Objects;

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
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Caleb Hall
 */
@RequestMapping("/object/action/knowledge/article/guest/view")
@RestController
public class ObjectActionKnowledgeArticleGuestViewRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		_updateGuestViewPermission(
			objectEntryJSONObject.getInt("status") ==
				WorkflowConstants.STATUS_APPROVED,
			jsonObject.getLong("classPK"));

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-learn-etc-spring-boot-oahs");
	}

	private void _updateGuestViewPermission(
		boolean approved, long objectEntryId) {

		URI uri = UriComponentsBuilder.fromPath(
			StringBundler.concat(
				"/o/c/p2s3knowledgearticles/", objectEntryId, "/permissions")
		).build(
		).toUri();

		JSONArray itemsJSONArray = new JSONObject(
			get(_getAuthorization(), uri)
		).getJSONArray(
			"items"
		);

		JSONObject guestPermissionJSONObject = null;

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			if (Objects.equals(
					itemJSONObject.getString("roleName"),
					RoleConstants.GUEST)) {

				guestPermissionJSONObject = itemJSONObject;

				break;
			}
		}

		if (guestPermissionJSONObject == null) {
			guestPermissionJSONObject = new JSONObject(
			).put(
				"actionIds", new JSONArray()
			).put(
				"roleName", RoleConstants.GUEST
			);

			itemsJSONArray.put(guestPermissionJSONObject);
		}

		List<Object> actionIds = guestPermissionJSONObject.getJSONArray(
			"actionIds"
		).toList();

		if (actionIds.contains(ActionKeys.VIEW) == approved) {
			return;
		}

		if (approved) {
			actionIds.add(ActionKeys.VIEW);
		}
		else {
			actionIds.remove(ActionKeys.VIEW);
		}

		guestPermissionJSONObject.put("actionIds", actionIds);

		put(_getAuthorization(), itemsJSONArray.toString(), uri);
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}