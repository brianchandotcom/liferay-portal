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
		apiKey = jsonObject.getString("apiKey");
		imageModelName = jsonObject.getJSONObject(
			"imageModel"
		).getString(
			"name"
		);
		modelName = jsonObject.getJSONObject(
			"model"
		).getString(
			"name"
		);
		providerName = jsonObject.getJSONObject(
			"provider"
		).getString(
			"name"
		);
	}

	public String apiKey;
	public long id;
	public String imageModelName;
	public String modelName;
	public String providerName;

}