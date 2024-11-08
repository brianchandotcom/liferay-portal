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
public class BlogPostingDescriptions {

	@Description("The blog entry's subtitle")
	public String alternativeHeadline;

	@Description(
		"The blog entry's content in HTML"
	)
	public String articleBody;

	@Description("The blog entry's title")
	public String headline;

	@Description(
		"Add keywords in hyphen-case to help users find this blog entry."
	)
	public String[] keywords;

	@Description(
		"Describe this image for the visually impaired in at most three sentences."
	)
	public String pictureDescription;

}