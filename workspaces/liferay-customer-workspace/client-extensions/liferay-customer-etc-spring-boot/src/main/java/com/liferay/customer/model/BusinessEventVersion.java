/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.model;

import com.liferay.customer.constants.BusinessEventVersionConstants;
import com.liferay.portal.kernel.util.Validator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Felipe Franca
 */
public class BusinessEventVersion {

	public BusinessEventVersion(JSONObject jsmJSONObject) {
		JSONArray attributesJSONArray = jsmJSONObject.getJSONArray(
			"attributes");

		_author = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventVersionConstants.ATTRIBUTE_AUTHOR
		).optString(
			"value"
		);

		_changeName = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventVersionConstants.ATTRIBUTE_CHANGE
		).optString(
			"value"
		);

		_comment = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventVersionConstants.ATTRIBUTE_COMMENT
		).optString(
			"value"
		);

		_createdDate = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventVersionConstants.ATTRIBUTE_CREATED
		).optString(
			"key"
		);
	}

	public String getAuthor() {
		return _author;
	}

	public String getChangeName() {
		return _changeName;
	}

	public String getComment() {
		return _comment;
	}

	public String getCreatedDate() {
		return _createdDate;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"author", _author
		).put(
			"change",
			new JSONObject(
			).put(
				"key", _changeName
			).put(
				"name", _changeName
			)
		).put(
			"comment", _comment
		).put(
			"createdDate", _createdDate
		);

		return jsonObject;
	}

	private JSONObject _toAttributeJSONObject(
		JSONArray jsonArray, String attributeName) {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject attributeJSONObject = jsonArray.getJSONObject(i);

			String name = attributeJSONObject.optString(
				"objectTypeAttributeName");

			if (Validator.isNull(name) || !attributeName.equals(name)) {
				continue;
			}

			JSONArray valuesJSONArray = attributeJSONObject.optJSONArray(
				"objectAttributeValues");

			if ((valuesJSONArray == null) || valuesJSONArray.isEmpty()) {
				break;
			}

			JSONObject valueJSONObject = valuesJSONArray.getJSONObject(0);

			String key = valueJSONObject.optString("value");

			if (Validator.isNull(key)) {
				JSONObject referencedObjectJSONObject =
					valueJSONObject.optJSONObject("referencedObject");

				if (referencedObjectJSONObject != null) {
					key = referencedObjectJSONObject.optString("id");
				}
			}

			String value = valueJSONObject.optString("displayValue", key);

			return new JSONObject(
			).put(
				"key", key
			).put(
				"value", value
			);
		}

		return new JSONObject();
	}

	private final String _author;
	private final String _changeName;
	private final String _comment;
	private final String _createdDate;

}