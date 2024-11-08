/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.langchain4j.tools;

import com.liferay.ai.content.wizard.langchain4j.descriptions.BlogPostingDescriptions;
import com.liferay.headless.delivery.client.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.BlogPostingResource;

import dev.langchain4j.agent.tool.Tool;

import java.util.ArrayList;

import org.json.JSONObject;

import org.springframework.http.HttpHeaders;

/**
 * @author Keven Leone
 * @author Brian Wing Shun Chan
 */
public class BlogPostingTools extends BaseTools {

	public BlogPostingTools(ToolsContext toolsContext) {
		super(toolsContext);

		_blogPostingResource = BlogPostingResource.builder(
		).endpoint(
			toolsContext.liferayDXPURL
		).header(
			HttpHeaders.AUTHORIZATION, toolsContext.authorization
		).build();
	}

	@Tool("Returns a list of blog entries")
	public Page<BlogPosting> getSiteBlogPostingsPage() throws Exception {
		return _blogPostingResource.getSiteBlogPostingsPage(
			toolsContext.siteId, "", new ArrayList<String>(), "",
			Pagination.of(1, 20), "");
	}

	@Tool("Creates a blog entry")
	public BlogPosting postSiteBlogPosting(
			BlogPostingDescriptions blogPostingDescriptions)
		throws Exception {

		return _blogPostingResource.postSiteBlogPosting(
			toolsContext.siteId,
			BlogPosting.toDTO(
				new JSONObject(
				).put(
					"alternativeHeadline",
					blogPostingDescriptions.alternativeHeadline
				).put(
					"articleBody", blogPostingDescriptions.articleBody
				).put(
					"headline", blogPostingDescriptions.headline
				).put(
					"keywords", blogPostingDescriptions.keywords
				).toString()));
	}

	private final BlogPostingResource _blogPostingResource;

}