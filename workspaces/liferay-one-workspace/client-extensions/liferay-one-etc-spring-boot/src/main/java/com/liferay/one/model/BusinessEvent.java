/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.model;

import com.liferay.one.constants.BusinessEventConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class BusinessEvent extends JiraAssetObject {

	public BusinessEvent(
		String accountExternalReferenceCode,
		JSONObject jiraAssetObjectJSONObject) {

		_accountExternalReferenceCode = accountExternalReferenceCode;

		_businessEventId = jiraAssetObjectJSONObject.optString("id");
		_actualEventDate = getAttributeKey(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_ACTUAL_EVENT_DATE);
		_associatedTickets = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_ASSOCIATED_TICKETS);
		_authorEmailAddress = getAttributeValue(
			jiraAssetObjectJSONObject, BusinessEventConstants.ATTRIBUTE_AUTHOR);
		_currentLiferayVersionKey = getAttributeKey(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_CURRENT_VERSION);
		_currentLiferayVersionName = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_CURRENT_VERSION);
		_description = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_DESCRIPTION);
		_eventStatusName = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_EVENT_STATUS);
		_eventTypeName = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_EVENT_TYPE);
		_lastComment = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_LAST_COMMENT);
		_lastUpdatedAuthorEmailAddress = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_LAST_UPDATED_AUTHOR);
		_name = getAttributeValue(
			jiraAssetObjectJSONObject, BusinessEventConstants.ATTRIBUTE_NAME);
		_newLiferayVersionKey = getAttributeKey(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_NEW_VERSION);
		_newLiferayVersionName = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_NEW_VERSION);
		_plannedEventDate = getAttributeKey(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_PLANNED_EVENT_DATE);
		_timeZoneName = getAttributeValue(
			jiraAssetObjectJSONObject,
			BusinessEventConstants.ATTRIBUTE_TIME_ZONE);
	}

	public BusinessEvent(
		String accountExternalReferenceCode, String authorEmailAddress,
		String attributesJSON) {

		_accountExternalReferenceCode = accountExternalReferenceCode;
		_authorEmailAddress = authorEmailAddress;

		_businessEventId = StringPool.BLANK;
		_currentLiferayVersionName = StringPool.BLANK;
		_lastUpdatedAuthorEmailAddress = authorEmailAddress;
		_newLiferayVersionName = StringPool.BLANK;

		JSONObject jsonObject = new JSONObject(attributesJSON);

		_actualEventDate = jsonObject.optString("actualEventDate");
		_associatedTickets = jsonObject.optString("associatedTickets");
		_currentLiferayVersionKey = jsonObject.optString(
			"currentLiferayVersion");
		_description = jsonObject.optString("description");
		_eventStatusName = jsonObject.optString("eventStatus");
		_eventTypeName = jsonObject.optString("eventType");
		_lastComment = jsonObject.optString("lastComment");
		_name = jsonObject.optString("name");
		_newLiferayVersionKey = jsonObject.optString("newLiferayVersion");
		_plannedEventDate = jsonObject.optString("plannedEventDate");
		_timeZoneName = jsonObject.optString("timeZone");
	}

	public String getAccountExternalReferenceCode() {
		return _accountExternalReferenceCode;
	}

	public String getActivityHistoryURL(String onePortalURL) {
		return getURL(onePortalURL) + "/activity-history";
	}

	public String getActualEventDate() {
		return _actualEventDate;
	}

	public String getAssociatedTickets() {
		return _associatedTickets;
	}

	public String getAuthorEmailAddress() {
		return _authorEmailAddress;
	}

	public String getBusinessEventId() {
		return _businessEventId;
	}

	public String getCurrentLiferayVersionKey() {
		return _currentLiferayVersionKey;
	}

	public String getCurrentLiferayVersionName() {
		return _currentLiferayVersionName;
	}

	public String getDescription() {
		return _description;
	}

	public String getEditURL(String onePortalURL) {
		return getURL(onePortalURL) + "/edit";
	}

	public String getEventStatusName() {
		return _eventStatusName;
	}

	public String getEventTypeName() {
		return _eventTypeName;
	}

	public String getLastComment() {
		return _lastComment;
	}

	public String getLastUpdatedAuthorEmailAddress() {
		return _lastUpdatedAuthorEmailAddress;
	}

	public String getName() {
		return _name;
	}

	public String getNewLiferayVersionKey() {
		return _newLiferayVersionKey;
	}

	public String getNewLiferayVersionName() {
		return _newLiferayVersionName;
	}

	public String getPlannedEventDate() {
		return _plannedEventDate;
	}

	public String getTimeZoneName() {
		return _timeZoneName;
	}

	public String getURL(String onePortalURL) {
		StringBundler sb = new StringBundler(5);

		sb.append(onePortalURL);
		sb.append("/my-account/#/");
		sb.append(_accountExternalReferenceCode);
		sb.append("/business-events/");
		sb.append(_businessEventId);

		return sb.toString();
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"actualEventDate", _actualEventDate
		).put(
			"associatedTickets", _associatedTickets
		).put(
			"currentLiferayVersion",
			new JSONObject(
			).put(
				"key", _currentLiferayVersionKey
			).put(
				"name", _currentLiferayVersionName
			)
		).put(
			"description", _description
		).put(
			"eventStatus",
			new JSONObject(
			).put(
				"key", _eventStatusName
			).put(
				"name", _eventStatusName
			)
		).put(
			"eventType",
			new JSONObject(
			).put(
				"key", _eventTypeName
			).put(
				"name", _eventTypeName
			)
		).put(
			"id", _businessEventId
		).put(
			"name", _name
		).put(
			"newLiferayVersion",
			new JSONObject(
			).put(
				"key", _newLiferayVersionKey
			).put(
				"name", _newLiferayVersionName
			)
		).put(
			"plannedEventDate", _plannedEventDate
		).put(
			"timeZone",
			new JSONObject(
			).put(
				"key", _timeZoneName
			).put(
				"name", _timeZoneName
			)
		);

		return jsonObject;
	}

	private final String _accountExternalReferenceCode;
	private final String _actualEventDate;
	private final String _associatedTickets;
	private final String _authorEmailAddress;
	private final String _businessEventId;
	private final String _currentLiferayVersionKey;
	private final String _currentLiferayVersionName;
	private final String _description;
	private final String _eventStatusName;
	private final String _eventTypeName;
	private final String _lastComment;
	private final String _lastUpdatedAuthorEmailAddress;
	private final String _name;
	private final String _newLiferayVersionKey;
	private final String _newLiferayVersionName;
	private final String _plannedEventDate;
	private final String _timeZoneName;

}