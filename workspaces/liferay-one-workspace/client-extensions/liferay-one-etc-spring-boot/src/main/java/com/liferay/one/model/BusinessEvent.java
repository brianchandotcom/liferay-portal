/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.model;

import com.liferay.petra.string.StringBundler;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class BusinessEvent {

	public BusinessEvent(
		String accountExternalReferenceCode, String actualEventDate,
		String associatedTickets, String authorEmailAddress,
		String businessEventId, String currentLiferayVersionKey,
		String currentLiferayVersionName, String description,
		String eventStatusName, String eventTypeName, String lastComment,
		String lastUpdatedAuthorEmailAddress, String name,
		String newLiferayVersionKey, String newLiferayVersionName,
		String plannedEventDate, String timeZoneName) {

		_accountExternalReferenceCode = accountExternalReferenceCode;
		_actualEventDate = actualEventDate;
		_associatedTickets = associatedTickets;
		_authorEmailAddress = authorEmailAddress;
		_businessEventId = businessEventId;
		_currentLiferayVersionKey = currentLiferayVersionKey;
		_currentLiferayVersionName = currentLiferayVersionName;
		_description = description;
		_eventStatusName = eventStatusName;
		_eventTypeName = eventTypeName;
		_lastComment = lastComment;
		_lastUpdatedAuthorEmailAddress = lastUpdatedAuthorEmailAddress;
		_name = name;
		_newLiferayVersionKey = newLiferayVersionKey;
		_newLiferayVersionName = newLiferayVersionName;
		_plannedEventDate = plannedEventDate;
		_timeZoneName = timeZoneName;
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