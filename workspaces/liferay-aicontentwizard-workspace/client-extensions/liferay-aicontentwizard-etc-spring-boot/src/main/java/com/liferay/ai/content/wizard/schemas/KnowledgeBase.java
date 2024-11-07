/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.schemas;

import com.liferay.ai.content.wizard.enums.ViewableBy;
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolder;

import dev.langchain4j.model.output.structured.Description;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class KnowledgeBase {

	public KnowledgeBaseArticle[] getArticles() {
		return articles;
	}

	public String getName() {
		return name;
	}

	public ViewableBy getViewableBy() {
		return viewableBy;
	}

	public KnowledgeBaseFolder toKnowledgeBaseFolder() {
		return KnowledgeBaseFolder.toDTO(
			new JSONObject(
			).put(
				"name", name
			).put(
				"viewableBy", viewableBy
			).toString());
	}

	@Description(
		"Articles related to this Knowledge Base, create a variety of articles between 1 and 3"
	)
	public KnowledgeBaseArticle[] articles;

	@Description("Name of the knowledge base category")
	public String name;

	@Description(
		"The Knowledge Base can be viewed by one of these options, consider anyone if not specified."
	)
	public ViewableBy viewableBy;

}