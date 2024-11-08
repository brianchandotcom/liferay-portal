/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.langchain4j.descriptions;

import dev.langchain4j.model.output.structured.Description;

/**
 * @author Keven Leone
 * @author Brian Wing Shun Chan
 */
public class KnowledgeBaseArticleDescriptions {

	@Description(
		"The knowledge base article's content in plain text, without HTML tags or Markdown."
	)
	public String articleBody;

	@Description(
		"Add keywords in hyphen-case to help users find this knowledge base article."
	)
	public String[] keywords;

	@Description("Knowledge Base article title")
	public String title;

}