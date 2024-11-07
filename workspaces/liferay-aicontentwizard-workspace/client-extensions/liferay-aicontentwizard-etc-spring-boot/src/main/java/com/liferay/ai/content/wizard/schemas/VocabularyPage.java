/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.schemas;

import dev.langchain4j.model.output.structured.Description;

/**
 * @author Keven Leone
 */
public class VocabularyPage extends BaseCategory {

	public Category[] getCategories() {
		return categories;
	}

	@Description("A list of categories")
	public Category[] categories;

}