/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.hubspot.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.hubspot.constants.HubSpotConstants;
import com.liferay.petra.string.StringBundler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Keven Leone
 * @author Ricardo Mariz
 */
@Component
public class LiferayService extends BaseService {

	public long getClassPK(String json) {
		JSONObject jsonObject = new JSONObject(json);

		return jsonObject.getLong("classPK");
	}

	public JSONObject getObjectEntryValuesJSONObject(String json) {
		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		return objectEntryJSONObject.getJSONObject("values");
	}

	public void patchObjectEntry(JSONObject jsonObject, String path)
		throws Exception {

		if (jsonObject == null) {
			return;
		}

		String body = new JSONObject(
		).put(
			"externalReferenceCode",
			HubSpotConstants.PREFIX_HUBSPOT_ID + jsonObject.getLong("id")
		).toString();

		patch(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-hubspot-etc-spring-boot-oahs"),
			body,
			UriComponentsBuilder.fromPath(
				path
			).build(
			).toUri());

		if (_log.isInfoEnabled()) {
			_log.info(StringBundler.concat("Updated ", path, " with: ", body));
		}
	}

	private static final Log _log = LogFactory.getLog(LiferayService.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}