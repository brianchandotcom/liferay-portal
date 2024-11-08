/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.langchain4j.descriptions;

import dev.langchain4j.model.output.structured.Description;

import java.util.Map;

/**
 * @author Keven Leone
 * @author Brian Wing Shun Chan
 */
public class TaxonomyVocabularyDescriptions {

	@Description("Vocabulary name")
	public String name;

	@Description("Vocabulary name in BPC47 format, like en-US: Book")
	public Map<String, String> name_i18n;

	@Description("An array of categories")
	public TaxonomyCategoryDescriptions[] taxonomyCategoryDescriptionsArray;

}