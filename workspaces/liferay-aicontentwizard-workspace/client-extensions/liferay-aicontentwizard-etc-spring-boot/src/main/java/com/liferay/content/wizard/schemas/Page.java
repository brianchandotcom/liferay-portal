/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.wizard.schemas;

import dev.langchain4j.model.output.structured.Description;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class Page {

	public String getName() {
		return name;
	}

	public PageStructure[] getPageStructures() {
		return pageStructures;
	}

	public String toString() {
		JSONArray jsonArray = new JSONArray();

		for (PageStructure pageStructure : pageStructures) {
			jsonArray.put(new JSONObject(pageStructure.toString()));
		}

		return new JSONObject(
		).put(
			"name", name
		).put(
			"pageStructure", jsonArray
		).toString();
	}

	@Description("The page name")
	public String name;

	@Description(
		"where each object either has a \"name\" key or a \"components\" key. \n" +
			"When an object has only one element, it should simply use the \"name\" key to define it, like \"name\": \"heading\" or \"name\": \"carousel\".\n" +
				"For cases where multiple elements are involved, the \"components\" key is used. \n" +
					"This key will hold an array of objects, each with its own \"name\" key to specify the type of the component, such as \"name\": \"paragraph\" or \"name\": \"card\". \n" +
						"When any social components are present in a group, they must be consolidated into a single object with the \"name\" key set to \"social\", rather than listing multiple social elements. This ensures that all social elements are grouped together under one \"social\" entry.\n" +
							"When components of the same type appear more than once, they should still be represented as individual objects within the \"components\" array, as seen with repeated \"card\" components."
	)
	public PageStructure[] pageStructures;

}