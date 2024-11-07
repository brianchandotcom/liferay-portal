/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.schemas;

import dev.langchain4j.model.output.structured.Description;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class KnowledgeBaseArticle {

	public String getArticleBody() {
		return articleBody;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public String getTitle() {
		return title;
	}

	public com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseArticle
		toKnowledgeBaseArticle() {

		return com.liferay.headless.delivery.client.dto.v1_0.
			KnowledgeBaseArticle.toDTO(
				new JSONObject(
				).put(
					"articleBody", articleBody
				).put(
					"keywords", keywords
				).put(
					"title", title
				).toString());
	}

	@Description(
		"The knowledge base article body, the output is plain text, without HTML tags or Markdown."
	)
	public String articleBody;

	@Description(
		"Identify the content of the blog and add meaningful keywords using the following format: hyphen-case'"
	)
	public String[] keywords;

	@Description("Title of knowledge base article")
	public String title;

}