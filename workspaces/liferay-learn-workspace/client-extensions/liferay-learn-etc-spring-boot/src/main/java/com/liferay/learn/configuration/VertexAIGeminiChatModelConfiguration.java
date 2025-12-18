/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn.configuration;

import com.google.cloud.vertexai.VertexAI;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nilton Vieira
 */
@Configuration
public class VertexAIGeminiChatModelConfiguration {

	@Bean
	public ChatClient chatClient() {
		VertexAiGeminiChatOptions vertexAiGeminiChatOptions =
			VertexAiGeminiChatOptions.builder(
			).model(
				_vertexAIGeminiChatOptionsModel
			).temperature(
				0.4
			).build();

		return ChatClient.create(
			VertexAiGeminiChatModel.builder(
			).vertexAI(
				new VertexAI(_vertexAIGeminiProjectId, _vertexAIGeminiLocation)
			).defaultOptions(
				vertexAiGeminiChatOptions
			).toolCallingManager(
				null
			).build());
	}

	@Value("${spring.ai.vertex.ai.gemini.chat.options.model}")
	private String _vertexAIGeminiChatOptionsModel;

	@Value("${spring.ai.vertex.ai.gemini.location}")
	private String _vertexAIGeminiLocation;

	@Value("${spring.ai.vertex.ai.gemini.project-id}")
	private String _vertexAIGeminiProjectId;

}