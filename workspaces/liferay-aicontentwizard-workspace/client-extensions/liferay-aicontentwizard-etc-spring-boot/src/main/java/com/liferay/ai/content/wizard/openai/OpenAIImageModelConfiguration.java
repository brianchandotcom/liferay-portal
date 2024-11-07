/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.openai;

import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiImageModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Keven Leone
 */
@Configuration
public class OpenAIImageModelConfiguration {

	public ImageModel getOpenAIImageLanguageModel() {
		return OpenAiImageModel.builder(
		).apiKey(
			_openAIKey
		).modelName(
			_modelName
		).build();
	}

	@Value("${liferay.aicontentwizard.openai.image.model.name}")
	private String _modelName;

	@Value("${liferay.aicontentwizard.openai.api.key}")
	private String _openAIKey;

}