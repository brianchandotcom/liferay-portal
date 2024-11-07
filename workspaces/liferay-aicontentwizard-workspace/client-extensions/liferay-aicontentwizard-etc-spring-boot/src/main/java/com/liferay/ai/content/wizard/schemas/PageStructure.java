/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.schemas;

import com.liferay.ai.content.wizard.enums.StructureKeys;

import dev.langchain4j.model.output.structured.Description;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class PageStructure {

	public PageComponent[] getComponents() {
		return components;
	}

	public StructureKeys getName() {
		return name;
	}

	public String toString() {
		return new JSONObject(
		).put(
			"components", components
		).put(
			"name", name
		).toString();
	}

	@Description(
		"For cases where multiple elements are involved, the \"pageComponents\" key is used."
	)
	public PageComponent[] components;

	@Description("The only available components for the JSON structure")
	public StructureKeys name;

}