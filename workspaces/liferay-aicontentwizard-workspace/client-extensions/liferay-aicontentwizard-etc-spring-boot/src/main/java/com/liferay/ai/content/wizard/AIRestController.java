/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard;

import com.liferay.ai.content.wizard.langchain4j.tools.AccountTools;
import com.liferay.ai.content.wizard.langchain4j.tools.BlogPostingTools;
import com.liferay.ai.content.wizard.langchain4j.tools.KnowledgeBaseTools;
import com.liferay.ai.content.wizard.langchain4j.tools.SitePageTools;
import com.liferay.ai.content.wizard.langchain4j.tools.SiteTools;
import com.liferay.ai.content.wizard.langchain4j.tools.ToolsContext;
import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

import java.net.URL;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class AIRestController extends BaseRestController {

	@PostMapping(
		produces = MediaType.APPLICATION_JSON_VALUE, value = "/generate"
	)
	public ResponseEntity<String> generate(@RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		ToolsContext toolsContext = new ToolsContext(
			_getAuthorization(),
			new URL(lxcDXPServerProtocol + "://" + lxcDXPMainDomain),
			jsonObject.getLong("siteId"));

		LiferayAIService liferayAIService = AiServices.builder(
			LiferayAIService.class
		).chatLanguageModel(
			OpenAiChatModel.builder(
			).apiKey(
				_openAIAPIKey
			).modelName(
				_openAIModelName
			).responseFormat(
				"json_schema"
			).strictJsonSchema(
				true
			).logRequests(
				true
			).logResponses(
				true
			).build()
		).tools(
			new AccountTools(toolsContext), new BlogPostingTools(toolsContext),
			new KnowledgeBaseTools(toolsContext),
			new SitePageTools(toolsContext), new SiteTools(toolsContext)
		).chatMemory(
			MessageWindowChatMemory.withMaxMessages(10)
		).build();

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"output",
				liferayAIService.systemMessage(jsonObject.getString("question"))
			).toString(),
			HttpStatus.OK);
	}

	public interface LiferayAIService {

		@SystemMessage(
			"You are a chatbot called 'Liferay Assistant', specialist in Liferay Portal" +
				"Do not answer topics related to competitors, if you are not " +
					"sure with Tools to use just say 'Sorry, I cannot help you.'"
		)
		public String systemMessage(String message);

	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-aicontentwizard-etc-spring-boot-oauth-application-" +
				"headless-server");
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.aicontentwizard.openai.api.key}")
	private String _openAIAPIKey;

	@Value("${liferay.aicontentwizard.openai.model.name}")
	private String _openAIModelName;

}