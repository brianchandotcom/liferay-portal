/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.models;

import com.liferay.ai.content.wizard.openai.OpenAIImageModelConfiguration;
import com.liferay.ai.content.wizard.services.LiferayService;

import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 */
@Component
public class AIContext {

	public AIContext(
		LiferayService liferayService,
		OpenAIImageModelConfiguration openAIImageModelConfiguration) {

		_liferayService = liferayService;
		_openAIImageModelConfiguration = openAIImageModelConfiguration;
	}

	public LiferayService getLiferayService() {
		return _liferayService;
	}

	public OpenAIImageModelConfiguration getOpenAIImageModelConfiguration() {
		return _openAIImageModelConfiguration;
	}

	public long getSiteId() {
		return _siteId;
	}

	public void setSiteId(long siteId) {
		_siteId = siteId;
	}

	private final LiferayService _liferayService;
	private final OpenAIImageModelConfiguration _openAIImageModelConfiguration;
	private long _siteId;

}