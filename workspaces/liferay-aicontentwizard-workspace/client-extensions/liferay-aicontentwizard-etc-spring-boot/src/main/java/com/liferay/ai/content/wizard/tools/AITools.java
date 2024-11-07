/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.tools;

import com.liferay.ai.content.wizard.models.AIContext;
import com.liferay.ai.content.wizard.openai.OpenAIImageModelConfiguration;
import com.liferay.ai.content.wizard.services.LiferayService;

/**
 * @author Keven Leone
 */
public abstract class AITools {

	public AITools(AIContext aiContext) {
		liferayService = aiContext.getLiferayService();
		siteId = aiContext.getSiteId();
		openAIImageModelConfiguration =
			aiContext.getOpenAIImageModelConfiguration();
	}

	protected LiferayService liferayService;
	protected OpenAIImageModelConfiguration openAIImageModelConfiguration;
	protected long siteId;

}