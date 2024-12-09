/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.model;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class Settings {

	public Settings(JSONObject jsonObject) {
		active = true;
		apiKey = jsonObject.getString("apiKey");
		imageModel = jsonObject.getJSONObject(
			"imageModel"
		).getString(
			"name"
		);
		provider = jsonObject.getJSONObject(
			"provider"
		).getString(
			"name"
		);
		model = jsonObject.getJSONObject(
			"model"
		).getString(
			"name"
		);
	}

	public boolean active;
	public String apiKey;
	public long id;
	public String imageModel;
	public String model;
	public String provider;

}