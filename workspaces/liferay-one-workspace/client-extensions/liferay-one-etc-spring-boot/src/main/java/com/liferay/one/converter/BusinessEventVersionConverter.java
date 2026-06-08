/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.converter;

import com.liferay.one.model.BusinessEventVersion;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class BusinessEventVersionConverter extends JiraAssetObjectConverter {

	public BusinessEventVersion toBusinessEventVersion(
		JSONObject jiraAssetObjectJSONObject) {

		return new BusinessEventVersion(
			getAttributeValue(jiraAssetObjectJSONObject, _authorAttributeId),
			getAttributeValue(jiraAssetObjectJSONObject, _changeAttributeId),
			getAttributeValue(jiraAssetObjectJSONObject, _commentAttributeId),
			getAttributeKey(jiraAssetObjectJSONObject, _createdAttributeId));
	}

	@Value(
		"${liferay.one.jira.business.event.version.asset.object.type." +
			"attribute.author}"
	)
	private String _authorAttributeId;

	@Value(
		"${liferay.one.jira.business.event.version.asset.object.type." +
			"attribute.change}"
	)
	private String _changeAttributeId;

	@Value(
		"${liferay.one.jira.business.event.version.asset.object.type." +
			"attribute.comment}"
	)
	private String _commentAttributeId;

	@Value(
		"${liferay.one.jira.business.event.version.asset.object.type." +
			"attribute.created}"
	)
	private String _createdAttributeId;

}