/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.wizard.tools;

import com.liferay.content.wizard.models.AIContext;
import com.liferay.content.wizard.schemas.KnowledgeBase;
import com.liferay.content.wizard.schemas.KnowledgeBaseArticle;
import com.liferay.content.wizard.schemas.KnowledgeBasePage;
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolder;

import dev.langchain4j.agent.tool.Tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 */
@Component
public class KnowledgeBaseTool extends AITools {

	public KnowledgeBaseTool(AIContext aiContext) {
		super(aiContext);
	}

	@Tool("Creates a Knowledge Base")
	public String createKnowledgeBase(KnowledgeBasePage knowledgeBasePage)
		throws Exception {

		for (KnowledgeBase knowledgeBase : knowledgeBasePage.knowledgeBases) {
			KnowledgeBaseFolder knowledgeBaseFolder =
				liferayService.createKnowledgeBaseFolder(
					siteId, knowledgeBase.toKnowledgeBaseFolder());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Knowledge Base Folder created " + knowledgeBase.getName());
			}

			for (KnowledgeBaseArticle knowledgeBaseArticle :
					knowledgeBase.getArticles()) {

				liferayService.createKnowledgeBase(
					knowledgeBaseFolder.getId(),
					knowledgeBaseArticle.toKnowledgeBaseArticle());

				if (_log.isInfoEnabled()) {
					_log.info(
						"Knowledge Base Article created " +
							knowledgeBaseArticle.getTitle());
				}
			}
		}

		return "Knowledge Base created...";
	}

	private static final Log _log = LogFactory.getLog(KnowledgeBaseTool.class);

}