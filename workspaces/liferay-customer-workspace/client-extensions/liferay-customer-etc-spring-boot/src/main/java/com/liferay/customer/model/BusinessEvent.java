/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.model;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class BusinessEvent {

	public BusinessEvent(JSONObject jsonObject) {
		JSONObject propertiesJSONObject = jsonObject.optJSONObject(
			"properties");

		if (propertiesJSONObject == null) {
			propertiesJSONObject = jsonObject;
		}

		_accountExternalReferenceCode = propertiesJSONObject.optString(
			"accountEntryToBusinessEventsERC");
		_accountId = propertiesJSONObject.optLong(
			"r_accountEntryToBusinessEvents_accountEntryId");

		_businessEventId = jsonObject.optString("id");

		JSONObject creatorJSONObject = jsonObject.optJSONObject("creator");

		if (creatorJSONObject != null) {
			_creatorGivenName = creatorJSONObject.optString("givenName");
			_creatorId = creatorJSONObject.optLong("id");
		}
		else {
			_creatorGivenName = StringPool.BLANK;
			_creatorId = 0;
		}

		_actualGoLiveDateTime = propertiesJSONObject.optString(
			"actualGoLiveDateTime");
		_associatedTickets = propertiesJSONObject.optString(
			"associatedTickets");

		JSONObject currentLiferayVersionJSONObject =
			propertiesJSONObject.optJSONObject("currentLiferayVersion");

		if (currentLiferayVersionJSONObject != null) {
			String currentLiferayVersionName =
				currentLiferayVersionJSONObject.optString("name");

			if (Validator.isNull(currentLiferayVersionName)) {
				currentLiferayVersionName =
					currentLiferayVersionJSONObject.optString("key");
			}

			_currentLiferayVersionName = currentLiferayVersionName;
		}
		else {
			_currentLiferayVersionName = propertiesJSONObject.optString(
				"currentLiferayVersion");
		}

		_description = propertiesJSONObject.optString("description");

		JSONObject eventStatusJSONObject = propertiesJSONObject.optJSONObject(
			"eventStatus");

		if (eventStatusJSONObject != null) {
			String eventStatusKey = eventStatusJSONObject.optString("key");

			if (Validator.isNull(eventStatusKey)) {
				eventStatusKey = eventStatusJSONObject.optString("name");
			}

			_eventStatusKey = eventStatusKey;
		}
		else {
			_eventStatusKey = propertiesJSONObject.optString("eventStatus");
		}

		JSONObject eventTypeJSONObject = propertiesJSONObject.optJSONObject(
			"eventType");

		if (eventTypeJSONObject != null) {
			String eventTypeName = eventTypeJSONObject.optString("name");

			if (Validator.isNull(eventTypeName)) {
				eventTypeName = eventTypeJSONObject.optString("key");
			}

			_eventTypeName = eventTypeName;
		}
		else {
			_eventTypeName = propertiesJSONObject.optString("eventType");
		}

		_lastComment = propertiesJSONObject.optString("lastComment");
		_name = propertiesJSONObject.optString("name");

		JSONObject newLiferayVersionJSONObject =
			propertiesJSONObject.optJSONObject("newLiferayVersion");

		if (newLiferayVersionJSONObject != null) {
			String newLiferayVersionName =
				newLiferayVersionJSONObject.optString("name");

			if (Validator.isNull(newLiferayVersionName)) {
				newLiferayVersionName = newLiferayVersionJSONObject.optString(
					"key");
			}

			_newLiferayVersionName = newLiferayVersionName;
		}
		else {
			_newLiferayVersionName = propertiesJSONObject.optString(
				"newLiferayVersion");
		}

		_targetGoLiveDateTime = propertiesJSONObject.optString(
			"targetGoLiveDateTime");

		JSONObject timeZoneJSONObject = propertiesJSONObject.optJSONObject(
			"timeZone");

		if (timeZoneJSONObject != null) {
			String timeZoneName = timeZoneJSONObject.optString("name");

			if (Validator.isNull(timeZoneName)) {
				timeZoneName = timeZoneJSONObject.optString("key");
			}

			_timeZoneName = timeZoneName;
		}
		else {
			_timeZoneName = propertiesJSONObject.optString("timeZone");
		}
	}

	public BusinessEvent(
		String accountExternalReferenceCode, JSONObject jsmJSONObject) {

		_accountExternalReferenceCode =
			(accountExternalReferenceCode != null) ?
				accountExternalReferenceCode : StringPool.BLANK;

		_accountId = 0;
		_businessEventId = jsmJSONObject.optString("id");
		_creatorGivenName = StringPool.BLANK;
		_creatorId = 0;

		JSONArray attributesJSONArray = jsmJSONObject.getJSONArray(
			"attributes");

		Map<String, String> attributes = new HashMap<>();

		for (int i = 0; i < attributesJSONArray.length(); i++) {
			JSONObject attributeJSONObject = attributesJSONArray.getJSONObject(
				i);

			String name = attributeJSONObject.getJSONObject(
				"objectTypeAttribute"
			).getString(
				"name"
			);

			JSONArray valuesJSONArray = attributeJSONObject.optJSONArray(
				"objectAttributeValues");

			if ((valuesJSONArray != null) && !valuesJSONArray.isEmpty()) {
				JSONObject valueJSONObject = valuesJSONArray.getJSONObject(0);

				String value = valueJSONObject.optString("displayValue");

				if (Validator.isNull(value)) {
					value = valueJSONObject.optString("value");
				}

				attributes.put(name, value);
			}
		}

		_actualGoLiveDateTime = attributes.getOrDefault(
			"Actual Go-Live Date", StringPool.BLANK);
		_associatedTickets = attributes.getOrDefault(
			"Associated Tickets", StringPool.BLANK);
		_currentLiferayVersionName = attributes.getOrDefault(
			"Current Version", StringPool.BLANK);
		_description = attributes.getOrDefault("Description", StringPool.BLANK);
		_eventStatusKey = attributes.getOrDefault(
			"Event Status",
			attributes.getOrDefault("Status", StringPool.BLANK));
		_eventTypeName = attributes.getOrDefault(
			"Event Type", StringPool.BLANK);
		_lastComment = attributes.getOrDefault(
			"Last Comment", StringPool.BLANK);
		_name = attributes.getOrDefault("Name", StringPool.BLANK);
		_newLiferayVersionName = attributes.getOrDefault(
			"New Version", StringPool.BLANK);
		_targetGoLiveDateTime = attributes.getOrDefault(
			"Target Event Date", StringPool.BLANK);
		_timeZoneName = attributes.getOrDefault("Time Zone", StringPool.BLANK);
	}

	public String getAccountExternalReferenceCode() {
		return _accountExternalReferenceCode;
	}

	public long getAccountId() {
		return _accountId;
	}

	public String getActivityHistoryURL(String customerPortalURL) {
		return getURL(customerPortalURL) + "/activity-history";
	}

	public String getActualGoLiveDateTime() {
		return _actualGoLiveDateTime;
	}

	public String getAssociatedTickets() {
		return _associatedTickets;
	}

	public String getBusinessEventId() {
		return _businessEventId;
	}

	public String getCreatorGivenName() {
		return _creatorGivenName;
	}

	public long getCreatorId() {
		return _creatorId;
	}

	public String getCurrentLiferayVersionName() {
		return _currentLiferayVersionName;
	}

	public String getDescription() {
		return _description;
	}

	public String getEditURL(String customerPortalURL) {
		return getURL(customerPortalURL) + "/edit";
	}

	public String getEventStatusKey() {
		return _eventStatusKey;
	}

	public String getEventTypeName() {
		return _eventTypeName;
	}

	public String getLastComment() {
		return _lastComment;
	}

	public String getName() {
		return _name;
	}

	public String getNewLiferayVersionName() {
		return _newLiferayVersionName;
	}

	public String getTargetGoLiveDate() {
		return _targetGoLiveDateTime.split("T")[0];
	}

	public String getTargetGoLiveDateTime() {
		return _targetGoLiveDateTime;
	}

	public String getTimeZoneName() {
		return _timeZoneName;
	}

	public String getURL(String customerPortalURL) {
		StringBundler sb = new StringBundler(5);

		sb.append(customerPortalURL);
		sb.append("/project/#/");
		sb.append(_accountExternalReferenceCode);
		sb.append("/business-events/");
		sb.append(_businessEventId);

		return sb.toString();
	}

	public boolean isCanceled() {
		return StringUtil.equals(_eventStatusKey, "canceled");
	}

	public boolean isCompleted() {
		return StringUtil.equals(_eventStatusKey, "completed");
	}

	public boolean isOverdue() {
		return StringUtil.equals(_eventStatusKey, "overdue");
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"actualGoLiveDateTime", _actualGoLiveDateTime
		).put(
			"associatedTickets", _associatedTickets
		).put(
			"currentLiferayVersion",
			new JSONObject(
			).put(
				"key", _currentLiferayVersionName
			).put(
				"name", _currentLiferayVersionName
			)
		).put(
			"description", _description
		).put(
			"eventStatus",
			new JSONObject(
			).put(
				"key", _eventStatusKey
			).put(
				"name", _eventStatusKey
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
			"lastComment", _lastComment
		).put(
			"name", _name
		).put(
			"newLiferayVersion",
			new JSONObject(
			).put(
				"key", _newLiferayVersionName
			).put(
				"name", _newLiferayVersionName
			)
		).put(
			"targetGoLiveDateTime", _targetGoLiveDateTime
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
	private final long _accountId;
	private final String _actualGoLiveDateTime;
	private final String _associatedTickets;
	private final String _businessEventId;
	private final String _creatorGivenName;
	private final long _creatorId;
	private final String _currentLiferayVersionName;
	private final String _description;
	private final String _eventStatusKey;
	private final String _eventTypeName;
	private final String _lastComment;
	private final String _name;
	private final String _newLiferayVersionName;
	private final String _targetGoLiveDateTime;
	private final String _timeZoneName;

}