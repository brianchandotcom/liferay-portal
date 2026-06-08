/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.converter;

import com.liferay.portal.kernel.util.Validator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class JiraAssetObjectConverter {

	protected String getAttributeKey(
		JSONObject jsonObject, String attributeId) {

		return _getAttributeKey(
			_getAttributeValueJSONObject(
				jsonObject.getJSONArray("attributes"), attributeId));
	}

	protected String getAttributeValue(
		JSONObject jsonObject, String attributeId) {

		JSONObject attributeValueJSONObject = _getAttributeValueJSONObject(
			jsonObject.getJSONArray("attributes"), attributeId);

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
		JSONArray attributesJSONArray, String attributeId) {

		for (int i = 0; i < attributesJSONArray.length(); i++) {
			JSONObject attributeJSONObject = attributesJSONArray.getJSONObject(
				i);

			String objectTypeAttributeId = attributeJSONObject.optString(
				"objectTypeAttributeId");

			if (Validator.isNull(objectTypeAttributeId) ||
				!attributeId.equals(objectTypeAttributeId)) {

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