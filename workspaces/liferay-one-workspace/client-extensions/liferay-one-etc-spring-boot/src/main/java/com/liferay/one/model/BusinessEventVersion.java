/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.model;

import com.liferay.one.constants.BusinessEventVersionConstants;

import org.json.JSONObject;

/**
 * @author Felipe Franca
 */
public class BusinessEventVersion extends JiraAssetObject {

	public BusinessEventVersion(JSONObject jiraAssetObjectJSONObject) {
		_authorEmailAddress = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventVersionConstants.ATTRIBUTE_AUTHOR);
		_changeName = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventVersionConstants.ATTRIBUTE_CHANGE);
		_comment = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventVersionConstants.ATTRIBUTE_COMMENT);
		_createdDate = getAttributeKey(
			jiraAssetObjectJSONObject,
			BusinessEventVersionConstants.ATTRIBUTE_CREATED);
	}

	public String getAuthorEmailAddress() {
		return _authorEmailAddress;
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
			"author", _authorEmailAddress
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

	private final String _authorEmailAddress;
	private final String _changeName;
	private final String _comment;
	private final String _createdDate;

}