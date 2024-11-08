/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.langchain4j.tools;

import com.liferay.ai.content.wizard.langchain4j.descriptions.KnowledgeBaseArticleDescriptions;
import com.liferay.ai.content.wizard.langchain4j.descriptions.KnowledgeBaseDescriptions;
import com.liferay.ai.content.wizard.langchain4j.descriptions.KnowledgeBaseFolderDescriptions;
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.client.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.delivery.client.resource.v1_0.KnowledgeBaseFolderResource;

import dev.langchain4j.agent.tool.Tool;

import org.json.JSONObject;

import org.springframework.http.HttpHeaders;

/**
 * @author Keven Leone
 * @author Brian Wing Shun Chan
 */
public class KnowledgeBaseTools extends BaseTools {

	public KnowledgeBaseTools(ToolsContext toolsContext) {
		super(toolsContext);

		_knowledgeBaseArticleResource = KnowledgeBaseArticleResource.builder(
		).endpoint(
			toolsContext.liferayDXPURL
		).header(
			HttpHeaders.AUTHORIZATION, toolsContext.authorization
		).build();
		_knowledgeBaseFolderResource = KnowledgeBaseFolderResource.builder(
		).endpoint(
			toolsContext.liferayDXPURL
		).header(
			HttpHeaders.AUTHORIZATION, toolsContext.authorization
		).build();
	}

	@Tool("Creates a Knowledge Base folder")
	public void postSiteKnowledgeBaseFolder(
			KnowledgeBaseDescriptions knowledgeBaseDescriptions)
		throws Exception {

		for (KnowledgeBaseFolderDescriptions knowledgeBaseFolderDescriptions :
				knowledgeBaseDescriptions.
					knowledgeBaseFolderDescriptionsArray) {

			KnowledgeBaseFolder knowledgeBaseFolder =
				_knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
					toolsContext.siteId,
					KnowledgeBaseFolder.toDTO(
						new JSONObject(
						).put(
							"name", knowledgeBaseFolderDescriptions.name
						).put(
							"viewableBy",
							knowledgeBaseFolderDescriptions.viewableBy
						).toString()));

			_postKnowledgeBaseFolderKnowledgeBaseArticle(
				knowledgeBaseFolder, knowledgeBaseFolderDescriptions);
		}
	}

	private void _postKnowledgeBaseFolderKnowledgeBaseArticle(
			KnowledgeBaseFolder knowledgeBaseFolder,
			KnowledgeBaseFolderDescriptions knowledgeBaseFolderDescriptions)
		throws Exception {

		for (KnowledgeBaseArticleDescriptions knowledgeBaseArticleDescriptions :
				knowledgeBaseFolderDescriptions.
					knowledgeBaseArticleDescriptionsArray) {

			_knowledgeBaseArticleResource.
				postKnowledgeBaseFolderKnowledgeBaseArticle(
					knowledgeBaseFolder.getId(),
					KnowledgeBaseArticle.toDTO(
						new JSONObject(
						).put(
							"articleBody",
							knowledgeBaseArticleDescriptions.articleBody
						).put(
							"keywords",
							knowledgeBaseArticleDescriptions.keywords
						).put(
							"title", knowledgeBaseArticleDescriptions.title
						).toString()));
		}
	}

	private final KnowledgeBaseArticleResource _knowledgeBaseArticleResource;
	private final KnowledgeBaseFolderResource _knowledgeBaseFolderResource;

}