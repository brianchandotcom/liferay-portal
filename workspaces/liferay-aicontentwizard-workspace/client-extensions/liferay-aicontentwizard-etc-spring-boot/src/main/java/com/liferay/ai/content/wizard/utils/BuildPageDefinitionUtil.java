/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.utils;

import java.util.Objects;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class BuildPageDefinitionUtil {

	public static JSONObject createPageDefinitionJSONObject(
		String json, String title) {

		JSONArray pageElementsJSONArray = new JSONArray();

		JSONArray componentsJSONArray = new JSONArray(json);

		for (int i = 0; i < componentsJSONArray.length(); i++) {
			JSONObject componentJSONObject = componentsJSONArray.getJSONObject(
				i);

			if (componentJSONObject.has("name")) {
				JSONObject pageElementJSONObject = getPageElementJSONObject(
					componentJSONObject.getString("name"));

				if (pageElementJSONObject != null) {
					pageElementsJSONArray.put(pageElementJSONObject);
				}
			}
			else if (componentJSONObject.has("components")) {
				JSONArray innerComponentsJSONArray =
					componentJSONObject.getJSONArray("components");
				JSONArray innerPageElementsJSONArray = new JSONArray();

				for (int j = 0; j < innerComponentsJSONArray.length(); j++) {
					JSONObject innerComponentJSONObject =
						innerComponentsJSONArray.getJSONObject(j);

					JSONObject pageElementJSONObject = getPageElementJSONObject(
						innerComponentJSONObject.getString("name"));

					if (pageElementJSONObject != null) {
						innerPageElementsJSONArray.put(pageElementJSONObject);
					}
				}

				pageElementsJSONArray.put(
					PageDefinitionUtils.getRowDefinitionJSONObject(
						innerPageElementsJSONArray));
			}
		}

		return getPageBodyJSONObject(pageElementsJSONArray, title);
	}

	public static JSONObject getPageBodyJSONObject(
		JSONArray pageElementsJSONArray, String title) {

		return new JSONObject(
		).put(
			"pageDefinition",
			new JSONObject(
			).put(
				"pageElement",
				new JSONObject(
				).put(
					"id",
					UUID.randomUUID(
					).toString()
				).put(
					"pageElements", pageElementsJSONArray
				).put(
					"type", "Root"
				)
			)
		).put(
			"title", title
		);
	}

	public static JSONObject getPageElementJSONObject(String name) {
		if (Objects.equals(name, "button")) {
			return PageDefinitionUtils.getFragmentDefinitionJSONObject(
				"BASIC_COMPONENT-button");
		}

		if (Objects.equals(name, "card")) {
			return PageDefinitionUtils.getFragmentDefinitionJSONObject(
				"BASIC_COMPONENT-card");
		}

		if (Objects.equals(name, "carousel")) {
			return PageDefinitionUtils.getFragmentDefinitionJSONObject(
				"BASIC_COMPONENT-slider");
		}

		if (Objects.equals(name, "footer")) {
			return PageDefinitionUtils.getFragmentDefinitionJSONObject(
				"FOOTERS-footer-nav-light");
		}

		if (Objects.equals(name, "header")) {
			return PageDefinitionUtils.getFragmentDefinitionJSONObject(
				"NAVIGATION_BARS-header-light");
		}

		if (Objects.equals(name, "heading")) {
			return PageDefinitionUtils.getFragmentDefinitionJSONObject(
				"BASIC_COMPONENT-heading");
		}

		if (Objects.equals(name, "hero banner")) {
			return PageDefinitionUtils.getBannerDefinitionJSONObject();
		}

		if (Objects.equals(name, "image")) {
			return PageDefinitionUtils.getFragmentDefinitionJSONObject(
				"BASIC_COMPONENT-image");
		}

		if (Objects.equals(name, "paragraph")) {
			return PageDefinitionUtils.getFragmentDefinitionJSONObject(
				"BASIC_COMPONENT-paragraph");
		}

		if (Objects.equals(name, "social")) {
			return PageDefinitionUtils.getFragmentDefinitionJSONObject(
				"BASIC_COMPONENT-social");
		}

		if (Objects.equals(name, "video")) {
			return PageDefinitionUtils.getFragmentDefinitionJSONObject(
				"BASIC_COMPONENT-external-video");
		}

		return null;
	}

}