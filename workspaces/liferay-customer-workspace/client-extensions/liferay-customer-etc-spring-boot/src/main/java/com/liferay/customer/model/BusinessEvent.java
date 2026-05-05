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
		_accountExternalKey = jsonObject.optString("accountExternalKey");

		_actualEventDate = jsonObject.optString("actualEventDate");

		_associatedTickets = jsonObject.optString("associatedTickets");

		_author = jsonObject.optString("author");

		_businessEventId = StringPool.BLANK;

		_currentLiferayVersionKey = jsonObject.optString(
			"currentLiferayVersion");

		_currentLiferayVersionName = StringPool.BLANK;

		_description = jsonObject.optString("description");

		_eventStatusName = jsonObject.optString("eventStatus");

		_eventTypeName = jsonObject.optString("eventType");

		_lastComment = jsonObject.optString("lastComment");

		_lastUpdatedAuthor = jsonObject.optString("author");

		_name = jsonObject.optString("name");

		_newLiferayVersionKey = jsonObject.optString("newLiferayVersion");

		_newLiferayVersionName = StringPool.BLANK;

		_plannedEventDate = jsonObject.optString("plannedEventDate");

		_timeZoneName = jsonObject.optString("timeZone");
	}

	public BusinessEvent(
		String accountExternalReferenceCode, JSONObject jsmJSONObject) {

		if (Validator.isNotNull(accountExternalReferenceCode)) {
			_accountExternalKey = accountExternalReferenceCode;
		}
		else {
			_accountExternalKey = StringPool.BLANK;
		}

		_businessEventId = jsmJSONObject.optString("id");

		JSONArray attributesJSONArray = jsmJSONObject.getJSONArray(
			"attributes");

		_actualEventDate = _toAttributeJSONObject(
			attributesJSONArray,
			BusinessEventConstants.ATTRIBUTE_ACTUAL_EVENT_DATE
		).optString(
			"key"
		);

		_associatedTickets = _toAttributeJSONObject(
			attributesJSONArray,
			BusinessEventConstants.ATTRIBUTE_ASSOCIATED_TICKETS
		).optString(
			"value"
		);

		_author = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventConstants.ATTRIBUTE_AUTHOR
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

		_eventStatusName = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventConstants.ATTRIBUTE_EVENT_STATUS
		).optString(
			"value"
		);

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

		_lastUpdatedAuthor = _toAttributeJSONObject(
			attributesJSONArray,
			BusinessEventConstants.ATTRIBUTE_LAST_UPDATED_AUTHOR
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
			BusinessEventConstants.ATTRIBUTE_PLANNED_EVENT_DATE
		).optString(
			"key"
		);

		_timeZoneName = _toAttributeJSONObject(
			attributesJSONArray, BusinessEventConstants.ATTRIBUTE_TIME_ZONE
		).optString(
			"value"
		);
	}

	public String getAccountExternalKey() {
		return _accountExternalKey;
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

	public String getAuthor() {
		return _author;
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

	public String getEditURL(String customerPortalURL) {
		return getURL(customerPortalURL) + "/edit";
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

	public String getLastUpdatedAuthor() {
		return _lastUpdatedAuthor;
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

	public String getURL(String customerPortalURL) {
		StringBundler sb = new StringBundler(5);

		sb.append(customerPortalURL);
		sb.append("/project/#/");
		sb.append(_accountExternalKey);
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

	private final String _accountExternalKey;
	private final String _actualEventDate;
	private final String _associatedTickets;
	private final String _author;
	private final String _businessEventId;
	private final String _currentLiferayVersionKey;
	private final String _currentLiferayVersionName;
	private final String _description;
	private final String _eventStatusName;
	private final String _eventTypeName;
	private final String _lastComment;
	private final String _lastUpdatedAuthor;
	private final String _name;
	private final String _newLiferayVersionKey;
	private final String _newLiferayVersionName;
	private final String _plannedEventDate;
	private final String _timeZoneName;

}