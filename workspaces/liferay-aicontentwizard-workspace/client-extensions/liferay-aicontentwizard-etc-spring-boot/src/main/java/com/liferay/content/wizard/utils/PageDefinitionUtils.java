/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.wizard.utils;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class PageDefinitionUtils {

	public static JSONObject createButtonFragmentJSONObject(
		String buttonSize, String buttonType, String fragmentKey) {

		return new JSONObject(
		).put(
			"fragment",
			new JSONObject(
			).put(
				"key", fragmentKey
			)
		).put(
			"fragmentConfig",
			new JSONObject(
			).put(
				"buttonSize", buttonSize
			).put(
				"buttonType", buttonType
			)
		).put(
			"type", "Fragment"
		);
	}

	public static JSONObject createFragmentJSONObject(
		String fragmentKey, String headingLevel, String text) {

		JSONObject fragmentConfigJSONObject = new JSONObject();

		if (headingLevel != null) {
			fragmentConfigJSONObject.put("headingLevel", headingLevel);
		}

		return new JSONObject(
		).put(
			"fragment",
			new JSONObject(
			).put(
				"key", fragmentKey
			)
		).put(
			"fragmentConfig", fragmentConfigJSONObject
		).put(
			"fragmentFields",
			new JSONArray(
			).put(
				new JSONObject(
				).put(
					"id", "element-text"
				).put(
					"value",
					new JSONObject(
					).put(
						"fragmentLink",
						new JSONObject(
						).put(
							"value_i18n", new JSONObject()
						)
					).put(
						"text",
						new JSONObject(
						).put(
							"value_i18n",
							new JSONObject(
							).put(
								"en_US", text
							)
						)
					)
				)
			)
		).put(
			"type", "Fragment"
		);
	}

	public static JSONObject createFragmentViewportJSONObject(
		String id, String paddingLeft, String paddingRight) {

		return new JSONObject(
		).put(
			"fragmentViewportStyle",
			new JSONObject(
			).put(
				"paddingLeft", paddingLeft
			).put(
				"paddingRight", paddingRight
			)
		).put(
			"id", id
		);
	}

	public static JSONObject createSectionElementJSONObject() {
		return new JSONObject(
		).put(
			"definition", new JSONObject()
		).put(
			"pageElements",
			new JSONArray(
			).put(
				createFragmentJSONObject(
					"BASIC_COMPONENT-heading", "h1", "Banner Title Example")
			).put(
				createFragmentJSONObject(
					"BASIC_COMPONENT-paragraph", null,
					"<span class=\"lead\">This is a simple banner component " +
						"that you can use when you need extra attention to " +
							"featured content or information.</span>")
			).put(
				createButtonFragmentJSONObject(
					"nm", "primary", "BASIC_COMPONENT-button")
			)
		).put(
			"type", "Section"
		);
	}

	public static JSONObject getBannerDefinitionJSONObject() {
		return new JSONObject(
		).put(
			"definition",
			new JSONObject(
			).put(
				"fragmentStyle",
				new JSONObject(
				).put(
					"backgroundColor", "gray500Color"
				).put(
					"borderWidth", "0"
				).put(
					"paddingBottom", "10"
				).put(
					"paddingTop", "10"
				).put(
					"textAlign", "center"
				)
			).put(
				"fragmentViewports",
				new JSONArray(
				).put(
					createFragmentViewportJSONObject(
						"landscapeMobile", "4", "4")
				).put(
					createFragmentViewportJSONObject("portraitMobile", "3", "3")
				).put(
					createFragmentViewportJSONObject("tablet", "5", "5")
				)
			).put(
				"layout",
				new JSONObject(
				).put(
					"borderWidth", 0
				).put(
					"paddingBottom", 0
				).put(
					"paddingTop", 0
				).put(
					"widthType", "Fluid"
				)
			)
		).put(
			"pageElements",
			new JSONArray(
			).put(
				createSectionElementJSONObject()
			)
		).put(
			"type", "Section"
		);
	}

	public static JSONObject getColumnDefinitionJSONObject(
		int size, JSONArray pageElementsJSONArray) {

		return new JSONObject(
		).put(
			"definition",
			new JSONObject(
			).put(
				"size", size
			)
		).put(
			"id",
			UUID.randomUUID(
			).toString()
		).put(
			"pageElements", pageElementsJSONArray
		).put(
			"type", "Column"
		);
	}

	public static JSONObject getFragmentDefinitionJSONObject(
		String fragmentKey) {

		return new JSONObject(
		).put(
			"definition",
			new JSONObject(
			).put(
				"fragment",
				new JSONObject(
				).put(
					"key", fragmentKey
				)
			).put(
				"fragmentConfig", new JSONObject()
			).put(
				"fragmentFields", new JSONArray()
			)
		).put(
			"id",
			UUID.randomUUID(
			).toString()
		).put(
			"type", "Fragment"
		);
	}

	public static JSONObject getRowDefinitionJSONObject(
		JSONArray pageElementsJSONArray) {

		JSONArray columnsJSONArray = new JSONArray();

		for (int i = 0; i < pageElementsJSONArray.length(); i++) {
			columnsJSONArray.put(
				getColumnDefinitionJSONObject(
					12 / pageElementsJSONArray.length(),
					new JSONArray(
					).put(
						pageElementsJSONArray.getJSONObject(i)
					)));
		}

		return new JSONObject(
		).put(
			"definition",
			new JSONObject(
			).put(
				"fragmentStyle",
				new JSONObject(
				).put(
					"marginBottom", "3"
				).put(
					"overflow", "visible"
				)
			).put(
				"gutters", true
			).put(
				"numberOfColumns", columnsJSONArray.length()
			)
		).put(
			"id",
			UUID.randomUUID(
			).toString()
		).put(
			"pageElements", columnsJSONArray
		).put(
			"type", "Row"
		);
	}

}