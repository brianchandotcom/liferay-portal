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
public class KnowledgeBaseFolderDescriptions {

	@Description(
		"Articles related to this Knowledge Base, create a variety of articles between 1 and 3"
	)
	public KnowledgeBaseArticleDescriptions[]
		knowledgeBaseArticleDescriptionsArray;

	@Description("Name of the knowledge base category")
	public String name;

	@Description(
		"The Knowledge Base can be viewed by one of these options, consider anyone if not specified."
	)
	public ViewableBy viewableBy;

	public enum ViewableBy {

		Anyone, Members, Owner

	}

}