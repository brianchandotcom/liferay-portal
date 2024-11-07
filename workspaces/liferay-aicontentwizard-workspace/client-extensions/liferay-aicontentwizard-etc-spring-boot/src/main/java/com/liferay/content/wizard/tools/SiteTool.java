/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.wizard.tools;

import com.liferay.content.wizard.models.AIContext;
import com.liferay.content.wizard.schemas.Page;
import com.liferay.content.wizard.schemas.SiteSchema;
import com.liferay.content.wizard.utils.BuildPageDefinitionUtil;
import com.liferay.headless.site.client.dto.v1_0.Site;

import dev.langchain4j.agent.tool.Tool;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class SiteTool extends AITools {

	public SiteTool(AIContext aiContext) {
		super(aiContext);
	}

	@Tool("Create page based on the image url provided by the user")
	public void createPageFromImage(Page page) throws Exception {
		JSONObject jsonObject =
			BuildPageDefinitionUtil.createPageDefinitionJSONObject(
				new JSONObject(
					page.toString()
				).getJSONArray(
					"pageStructure"
				).toString(),
				page.getName());

		liferayService.createPage(siteId, jsonObject.toString());
	}

	@Tool("Create Site")
	public Site createSite(SiteSchema siteSchema) throws Exception {
		return liferayService.createSite(siteSchema.toSite());
	}

}