/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.schemas;

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary;

import dev.langchain4j.model.output.structured.Description;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class BaseCategory {

	public String getName() {
		return name;
	}

	public Map<String, String> getName_i18n() {
		return name_i18n;
	}

	public TaxonomyCategory toTaxonomyCategory() {
		return TaxonomyCategory.toDTO(
			new JSONObject(
			).put(
				"name", name
			).put(
				"name_i18n", name_i18n
			).toString());
	}

	public TaxonomyVocabulary toTaxonomyVocabulary() {
		return TaxonomyVocabulary.toDTO(
			new JSONObject(
			).put(
				"name", name
			).put(
				"name_i18n", name_i18n
			).toString());
	}

	@Description("Name of the category")
	public String name;

	@Description("Name of the category in BPC47 format, like en-US: Book")
	public Map<String, String> name_i18n;

}