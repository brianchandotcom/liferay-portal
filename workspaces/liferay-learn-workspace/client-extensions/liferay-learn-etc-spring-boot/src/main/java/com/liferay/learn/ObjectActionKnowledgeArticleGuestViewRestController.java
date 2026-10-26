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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void post(@RequestBody String json) {
		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		_updateGuestViewPermission(
			objectEntryJSONObject.getInt("status") ==
				WorkflowConstants.STATUS_APPROVED,
			jsonObject.getLong("classPK"));
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

		JSONArray permissionsJSONArray = new JSONObject(
			get(_getAuthorization(), uri)
		).getJSONArray(
			"items"
		);

		JSONObject guestPermissionJSONObject = null;

		for (int i = 0; i < permissionsJSONArray.length(); i++) {
			JSONObject permissionJSONObject =
				permissionsJSONArray.getJSONObject(i);

			if (Objects.equals(
					permissionJSONObject.getString("roleName"),
					RoleConstants.GUEST)) {

				guestPermissionJSONObject = permissionJSONObject;

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

			permissionsJSONArray.put(guestPermissionJSONObject);
		}

		JSONArray actionIdsJSONArray = guestPermissionJSONObject.getJSONArray(
			"actionIds");

		List<Object> actionIds = actionIdsJSONArray.toList();

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

		put(_getAuthorization(), permissionsJSONArray.toString(), uri);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					approved ? "Added" : "Removed",
					" guest view permission for knowledge article ",
					objectEntryId));
		}
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionKnowledgeArticleGuestViewRestController.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}