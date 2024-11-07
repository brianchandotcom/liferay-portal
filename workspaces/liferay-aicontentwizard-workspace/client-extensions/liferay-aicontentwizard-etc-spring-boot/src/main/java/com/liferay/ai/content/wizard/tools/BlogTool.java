/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.tools;

import com.liferay.ai.content.wizard.models.AIContext;
import com.liferay.ai.content.wizard.schemas.Blog;
import com.liferay.headless.delivery.client.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.client.pagination.Page;

import dev.langchain4j.agent.tool.Tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 */
@Component
public class BlogTool extends AITools {

	public BlogTool(AIContext aiContext) {
		super(aiContext);
	}

	@Tool("Create blog posts")
	public BlogPosting createBlogsEntry(Blog blog) throws Exception {
		BlogPosting blogPosting = liferayService.createBlog(
			blog.toBlogPosting(), siteId);

		if (_log.isInfoEnabled()) {
			_log.info("Blog entry created");
		}

		return blogPosting;
	}

	@Tool("Returns a list of blog posts")
	public Page<BlogPosting> getBlogPosts() throws Exception {
		return liferayService.getBlogs(siteId);
	}

	private static final Log _log = LogFactory.getLog(BlogTool.class);

}