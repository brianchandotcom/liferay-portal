/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.wizard.schemas;

import com.liferay.headless.delivery.client.dto.v1_0.BlogPosting;

import dev.langchain4j.model.output.structured.Description;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class Blog {

	public String getAlternativeHeadline() {
		return alternativeHeadline;
	}

	public String getArticleBody() {
		return articleBody;
	}

	public String getHeadline() {
		return headline;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public String getPictureDescription() {
		return pictureDescription;
	}

	public BlogPosting toBlogPosting() {
		return BlogPosting.toDTO(
			new JSONObject(
			).put(
				"alternativeHeadline", alternativeHeadline
			).put(
				"articleBody", articleBody
			).put(
				"headline", headline
			).put(
				"keywords", keywords
			).toString());
	}

	@Description("A headline that is a summary of the blog")
	public String alternativeHeadline;

	@Description(
		"The content of the blog article, the output must be HTML format."
	)
	public String articleBody;

	@Description("The title of the blog article")
	public String headline;

	@Description(
		"Identify the content of the blog and add meaningful keywords using the following format: hyphen-case'"
	)
	public String[] keywords;

	@Description(
		"A description of an appropriate image for this blog in three sentences."
	)
	public String pictureDescription;

}