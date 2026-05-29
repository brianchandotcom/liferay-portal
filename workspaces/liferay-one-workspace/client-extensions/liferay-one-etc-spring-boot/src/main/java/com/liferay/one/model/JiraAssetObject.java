/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.model;

import com.liferay.portal.kernel.util.Validator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public abstract class JiraAssetObject {

	protected String getAttributeKey(
		JSONObject jsonObject, String attributeName) {

		JSONArray attributesJSONArray = jsonObject.getJSONArray("attributes");

		return _getAttributeKey(
			_getAttributeValueJSONObject(attributesJSONArray, attributeName));
	}

	protected String getAttributeValue(
		JSONObject jsonObject, String attributeName) {

		JSONArray attributesJSONArray = jsonObject.getJSONArray("attributes");

		JSONObject attributeValueJSONObject = _getAttributeValueJSONObject(
			attributesJSONArray, attributeName);

		return attributeValueJSONObject.optString(
			"displayValue", _getAttributeKey(attributeValueJSONObject));
	}

	private String _getAttributeKey(JSONObject attributeValueJSONObject) {
		String key = attributeValueJSONObject.optString("value");

		if (Validator.isNull(key)) {
			JSONObject referencedObjectJSONObject =
				attributeValueJSONObject.optJSONObject("referencedObject");

			if (referencedObjectJSONObject != null) {
				key = referencedObjectJSONObject.optString("id");
			}
		}

		return key;
	}

	private JSONObject _getAttributeValueJSONObject(
		JSONArray jsonArray, String attributeName) {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject attributeJSONObject = jsonArray.getJSONObject(i);

			String name = attributeJSONObject.optString(
				"objectTypeAttributeName");

			if (Validator.isNull(name) || !attributeName.equals(name)) {
				continue;
			}

			JSONArray attributeValuesJSONArray =
				attributeJSONObject.optJSONArray("objectAttributeValues");

			if ((attributeValuesJSONArray == null) ||
				attributeValuesJSONArray.isEmpty()) {

				break;
			}

			return attributeValuesJSONArray.getJSONObject(0);
		}

		return new JSONObject();
	}

}