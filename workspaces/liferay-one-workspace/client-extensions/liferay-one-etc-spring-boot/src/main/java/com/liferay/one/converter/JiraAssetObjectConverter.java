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
		String attributeId, JSONObject jsonObject) {

		return _getAttributeKey(
			_getAttributeValueJSONObject(
				attributeId, jsonObject.getJSONArray("attributes")));
	}

	protected String getAttributeValue(
		String attributeId, JSONObject jsonObject) {

		JSONObject attributeValueJSONObject = _getAttributeValueJSONObject(
			attributeId, jsonObject.getJSONArray("attributes"));

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
		String attributeId, JSONArray attributesJSONArray) {

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