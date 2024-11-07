/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.tools;

import com.liferay.ai.content.wizard.models.AIContext;
import com.liferay.ai.content.wizard.schemas.BaseCategory;
import com.liferay.ai.content.wizard.schemas.Category;
import com.liferay.ai.content.wizard.schemas.VocabularyPage;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary;

import dev.langchain4j.agent.tool.Tool;

import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 */
@Component
public class CategoryTool extends AITools {

	public CategoryTool(AIContext aiContext) {
		super(aiContext);
	}

	@Tool("Create Category")
	public void createCategory(VocabularyPage vocabularyPage) throws Exception {
		TaxonomyVocabulary taxonomyVocabulary =
			liferayService.createTaxonomyVocabulary(
				siteId, vocabularyPage.toTaxonomyVocabulary());

		for (Category category : vocabularyPage.getCategories()) {
			TaxonomyCategory taxonomyCategory =
				liferayService.createTaxonomyVocabularyCategory(
					category.toTaxonomyCategory(), taxonomyVocabulary.getId());

			for (BaseCategory childCategory : category.getChildCategories()) {
				TaxonomyCategory taxonomyCategory1 =
					childCategory.toTaxonomyCategory();

				taxonomyCategory1.setParentTaxonomyCategory(
					taxonomyCategory::getParentTaxonomyCategory);

				liferayService.createTaxonomyCategory(
					taxonomyCategory.getId(), taxonomyCategory1);
			}
		}
	}

}