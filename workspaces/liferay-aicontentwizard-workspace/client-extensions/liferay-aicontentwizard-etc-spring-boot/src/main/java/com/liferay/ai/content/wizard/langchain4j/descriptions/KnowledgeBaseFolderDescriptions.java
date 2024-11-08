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
		"An array of this folder's article descriptions"
	)
	public KnowledgeBaseArticleDescriptions[]
		knowledgeBaseArticleDescriptionsArray;

	@Description("The name of this Knowledge Base folder")
	public String name;

	@Description(
		"Shows whether this folder can be viewed by anyone, members, or only its owner."
	)
	public ViewableBy viewableBy;

	public enum ViewableBy {

		Anyone, Members, Owner

	}

}