/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.service;

import com.liferay.ai.content.wizard.model.Settings;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;

import java.net.URI;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 */
@Component
public class SettingsService extends BaseService {

	public Settings getActiveSettings(Jwt jwt) {
		if (_activeSettings != null) {
			return _activeSettings;
		}

		JSONObject jsonObject = new JSONObject(
			get(
				"Bearer " + jwt.getTokenValue(),
				URI.create(
					"/o/c/k9l6aicontentwizardsettings?filter=active eq true")));

		int totalCount = jsonObject.getInt("totalCount");

		if (totalCount == 0) {
			return null;
		}

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		_activeSettings = new Settings(jsonArray.getJSONObject(0));

		return _activeSettings;
	}

	public void setActiveSettings(JSONObject jsonObject) {
		_activeSettings = new Settings(jsonObject);
	}

	private Settings _activeSettings;

}