/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.schemas;

import dev.langchain4j.model.output.structured.Description;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class PageComponent {

	public String getName() {
		return name;
	}

	public String toString() {
		return new JSONObject(
			"name", name
		).toString();
	}

	@Description("The content description based on the parent instructions")
	public String name;

}