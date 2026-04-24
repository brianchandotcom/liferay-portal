/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.model;

import com.liferay.customer.constants.BusinessEventConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

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

		_actualEventDate = propertiesJSONObject.optString("actualEventDate");
		_associatedTickets = propertiesJSONObject.optString(
			"associatedTickets");

		JSONObject currentLiferayVersionJSONObject = _toKeyValueJSONObject(
			propertiesJSONObject, "currentLiferayVersion");

		_currentLiferayVersionKey = currentLiferayVersionJSONObject.optString(
			"key");
		_currentLiferayVersionName = currentLiferayVersionJSONObject.optString(
			"value");

		_description = propertiesJSONObject.optString("description");

		_eventStatusKey = _toKeyValueJSONObject(
			propertiesJSONObject, "eventStatus"
		).optString(
			"key"
		);
		_eventTypeName = _toKeyValueJSONObject(
			propertiesJSONObject, "eventType"
		).optString(
			"value"
		);

		_lastComment = propertiesJSONObject.optString("lastComment");
		_name = propertiesJSONObject.optString("name");

		JSONObject newLiferayVersionJSONObject = _toKeyValueJSONObject(
			propertiesJSONObject, "newLiferayVersion");

		_newLiferayVersionKey = newLiferayVersionJSONObject.optString("key");
		_newLiferayVersionName = newLiferayVersionJSONObject.optString("value");

		_plannedEventDate = propertiesJSONObject.optString("plannedEventDate");
		_timeZoneName = _toKeyValueJSONObject(
			propertiesJSONObject, "timeZone"
		).optString(
			"value"
		);
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

		_actualEventDate = _toAttributeJSONObject(
			attributesJSONArray,
			BusinessEventConstants.ATTRIBUTE_ACTUAL_GO_LIVE_DATE
		).optString(
			"key"
		);
		_associatedTickets = _toAttributeJSONObject(
			attributesJSONArray,
			BusinessEventConstants.ATTRIBUTE_ASSOCIATED_TICKETS
		).optString(
			"value"
		);

		JSONObject currentLiferayVersionJSONObject = _toAttributeJSONObject(
			attributesJSONArray,
			BusinessEventConstants.ATTRIBUTE_CURRENT_VERSION);

		_currentLiferayVersionKey = currentLiferayVersionJSONObject.optString(
			"key");
		_currentLiferayVersionName = currentLiferayVersionJSONObject.optString(
			"value");

		_description = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventConstants.ATTRIBUTE_DESCRIPTION
		).optString(
			"value"
		);

		String eventStatusKey = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventConstants.ATTRIBUTE_EVENT_STATUS
		).optString(
			"value"
		);

		if (Validator.isNull(eventStatusKey)) {
			eventStatusKey = _toAttributeJSONObject(
				attributesJSONArray, BusinessEventConstants.ATTRIBUTE_STATUS
			).optString(
				"value"
			);
		}

		_eventStatusKey = eventStatusKey;

		_eventTypeName = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventConstants.ATTRIBUTE_EVENT_TYPE
		).optString(
			"value"
		);
		_lastComment = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventConstants.ATTRIBUTE_LAST_COMMENT
		).optString(
			"value"
		);
		_name = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventConstants.ATTRIBUTE_NAME
		).optString(
			"value"
		);

		JSONObject newLiferayVersionJSONObject = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventConstants.ATTRIBUTE_NEW_VERSION);

		_newLiferayVersionKey = newLiferayVersionJSONObject.optString("key");
		_newLiferayVersionName = newLiferayVersionJSONObject.optString("value");

		_plannedEventDate = _toAttributeJSONObject(
			attributesJSONArray,
			BusinessEventConstants.ATTRIBUTE_TARGET_EVENT_DATE
		).optString(
			"key"
		);
		_timeZoneName = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventConstants.ATTRIBUTE_TIME_ZONE
		).optString(
			"value"
		);
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

	public String getActualEventDate() {
		return _actualEventDate;
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

	public String getCurrentLiferayVersionKey() {
		return _currentLiferayVersionKey;
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

	public String getNewLiferayVersionKey() {
		return _newLiferayVersionKey;
	}

	public String getNewLiferayVersionName() {
		return _newLiferayVersionName;
	}

	public String getPlannedEventDate() {
		return _plannedEventDate;
	}

	public String getPlannedEventDateOnly() {
		return _plannedEventDate.split("T")[0];
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

	private JSONObject _toAttributeJSONObject(
		JSONArray jsonArray, String attributeName) {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject attributeJSONObject = jsonArray.getJSONObject(i);

			String name = attributeJSONObject.getJSONObject(
				"objectTypeAttribute"
			).getString(
				"name"
			);

			if (!attributeName.equals(name)) {
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

	private JSONObject _toKeyValueJSONObject(
		JSONObject jsonObject, String fieldName) {

		JSONObject keyValueJSONObject = jsonObject.optJSONObject(fieldName);

		if (keyValueJSONObject != null) {
			String key = keyValueJSONObject.optString("key");

			String value = keyValueJSONObject.optString("name", key);

			if (Validator.isNull(key)) {
				key = value;
			}

			return new JSONObject(
			).put(
				"key", key
			).put(
				"value", value
			);
		}

		String value = jsonObject.optString(fieldName);

		return new JSONObject(
		).put(
			"key", value
		).put(
			"value", value
		);
	}

	private final String _accountExternalReferenceCode;
	private final long _accountId;
	private final String _actualEventDate;
	private final String _associatedTickets;
	private final String _businessEventId;
	private final String _creatorGivenName;
	private final long _creatorId;
	private final String _currentLiferayVersionKey;
	private final String _currentLiferayVersionName;
	private final String _description;
	private final String _eventStatusKey;
	private final String _eventTypeName;
	private final String _lastComment;
	private final String _name;
	private final String _newLiferayVersionKey;
	private final String _newLiferayVersionName;
	private final String _plannedEventDate;
	private final String _timeZoneName;

}