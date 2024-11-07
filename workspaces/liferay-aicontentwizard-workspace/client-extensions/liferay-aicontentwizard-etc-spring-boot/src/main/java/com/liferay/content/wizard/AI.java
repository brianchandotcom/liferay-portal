/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.wizard;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.content.wizard.interfaces.LiferayAssistant;
import com.liferay.content.wizard.models.AIContext;
import com.liferay.content.wizard.openai.OpenAIChatModelConfiguration;
import com.liferay.content.wizard.openai.OpenAIImageModelConfiguration;
import com.liferay.content.wizard.services.LiferayService;
import com.liferay.content.wizard.tools.AccountTool;
import com.liferay.content.wizard.tools.BlogTool;
import com.liferay.content.wizard.tools.CategoryTool;
import com.liferay.content.wizard.tools.KnowledgeBaseTool;
import com.liferay.content.wizard.tools.SiteTool;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keven Leone
 */
@RequestMapping("/ai")
@RestController
public class AI extends BaseRestController {

	@PostMapping(
		produces = MediaType.APPLICATION_JSON_VALUE, value = "/generate"
	)
	public ResponseEntity<String> generate(@RequestBody String json) {
		JSONObject jsonObject = new JSONObject(json);

		String question = jsonObject.getString("question");

		ChatLanguageModel chatLanguageModel =
			_openAIChatModelConfiguration.getOpenAIChatLanguageModel();

		AIContext aiContext = new AIContext(
			_liferayService, openAIImageModelConfiguration);

		aiContext.setSiteId(jsonObject.getLong("siteId"));

		LiferayAssistant assistant = AiServices.builder(
			LiferayAssistant.class
		).chatLanguageModel(
			chatLanguageModel
		).tools(
			new AccountTool(aiContext), new BlogTool(aiContext),
			new CategoryTool(aiContext), new KnowledgeBaseTool(aiContext),
			new SiteTool(aiContext)
		).chatMemory(
			MessageWindowChatMemory.withMaxMessages(10)
		).build();

		if (_log.isInfoEnabled()) {
			_log.info("Asked question: " + question);
		}

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"output", assistant.chat(question)
			).toString(),
			HttpStatus.OK);
	}

	@Autowired
	protected OpenAIImageModelConfiguration openAIImageModelConfiguration;

	private static final Log _log = LogFactory.getLog(AI.class);

	@Autowired
	private LiferayService _liferayService;

	@Autowired
	private OpenAIChatModelConfiguration _openAIChatModelConfiguration;

}