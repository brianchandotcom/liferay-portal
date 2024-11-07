/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.services;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Keven Leone
 */
@Service
public class WizardSettingService {

	public JSONObject getActiveWizardSettingJSONObject() {
		return _activeWizardSettingJSONObject;
	}

	public String getApiKey() {
		return _apiKey;
	}

	public String getProvider() {
		return _provider;
	}

	@PostConstruct
	public void init() {
		try {
			JSONObject jsonObject = _liferayService.getActiveWizardSetting();

			if (jsonObject == null) {
				return;
			}

			_activeWizardSettingJSONObject = jsonObject;
			_apiKey = jsonObject.getString("apiKey");
			_provider = jsonObject.getJSONObject(
				"provider"
			).getString(
				"key"
			);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	public void setApiKey(String apiKey) {
		_apiKey = apiKey;
	}

	public void setProvider(String provider) {
		_provider = provider;
	}

	private static final Log _log = LogFactory.getLog(
		WizardSettingService.class);

	private JSONObject _activeWizardSettingJSONObject;
	private String _apiKey;

	@Autowired
	private LiferayService _liferayService;

	private String _provider;

}