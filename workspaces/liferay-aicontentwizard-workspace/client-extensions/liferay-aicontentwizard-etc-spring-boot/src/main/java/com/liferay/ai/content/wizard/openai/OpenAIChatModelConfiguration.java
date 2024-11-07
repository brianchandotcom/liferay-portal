/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.openai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Keven Leone
 */
@Configuration
public class OpenAIChatModelConfiguration {

	public ChatLanguageModel getOpenAIChatLanguageModel() {
		return OpenAiChatModel.builder(
		).apiKey(
			_openAIKey
		).modelName(
			_modelName
		).responseFormat(
			"json_schema"
		).strictJsonSchema(
			true
		).logRequests(
			true
		).logResponses(
			true
		).build();
	}

	@Value("${liferay.aicontentwizard.openai.model.name}")
	private String _modelName;

	@Value("${liferay.aicontentwizard.openai.api.key}")
	private String _openAIKey;

}