/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.converter;

import com.liferay.one.model.BusinessEvent;
import com.liferay.petra.string.StringPool;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class BusinessEventConverter extends JiraAssetObjectConverter {

	public JSONObject toAttributesJSONObject(
		String accountObjectKey, BusinessEvent businessEvent) {

		JSONObject attributesJSONObject = new JSONObject();

		attributesJSONObject.put(
			_actualEventDateAttributeId, businessEvent.getActualEventDate()
		).put(
			_associatedTicketsAttributeId, businessEvent.getAssociatedTickets()
		).put(
			_currentVersionAttributeId,
			businessEvent.getCurrentLiferayVersionKey()
		).put(
			_descriptionAttributeId, businessEvent.getDescription()
		).put(
			_eventStatusAttributeId, businessEvent.getEventStatusName()
		).put(
			_eventTypeAttributeId, businessEvent.getEventTypeName()
		).put(
			_lastCommentAttributeId, businessEvent.getLastComment()
		).put(
			_lastUpdatedAuthorAttributeId,
			businessEvent.getLastUpdatedAuthorEmailAddress()
		).put(
			_nameAttributeId, businessEvent.getName()
		).put(
			_newVersionAttributeId, businessEvent.getNewLiferayVersionKey()
		).put(
			_plannedEventDateAttributeId, businessEvent.getPlannedEventDate()
		).put(
			_timeZoneAttributeId, businessEvent.getTimeZoneName()
		);

		if (accountObjectKey != null) {
			attributesJSONObject.put(
				_accountAttributeId, accountObjectKey
			).put(
				_authorAttributeId, businessEvent.getAuthorEmailAddress()
			);
		}

		return attributesJSONObject;
	}

	public BusinessEvent toBusinessEvent(
		String accountExternalReferenceCode,
		JSONObject jiraAssetObjectJSONObject) {

		return new BusinessEvent(
			accountExternalReferenceCode,
			getAttributeKey(
				_actualEventDateAttributeId, jiraAssetObjectJSONObject),
			getAttributeValue(
				_associatedTicketsAttributeId, jiraAssetObjectJSONObject),
			getAttributeValue(_authorAttributeId, jiraAssetObjectJSONObject),
			jiraAssetObjectJSONObject.optString("id"),
			getAttributeKey(
				_currentVersionAttributeId, jiraAssetObjectJSONObject),
			getAttributeValue(
				_currentVersionAttributeId, jiraAssetObjectJSONObject),
			getAttributeValue(
				_descriptionAttributeId, jiraAssetObjectJSONObject),
			getAttributeValue(
				_eventStatusAttributeId, jiraAssetObjectJSONObject),
			getAttributeValue(_eventTypeAttributeId, jiraAssetObjectJSONObject),
			getAttributeValue(
				_lastCommentAttributeId, jiraAssetObjectJSONObject),
			getAttributeValue(
				_lastUpdatedAuthorAttributeId, jiraAssetObjectJSONObject),
			getAttributeValue(_nameAttributeId, jiraAssetObjectJSONObject),
			getAttributeKey(_newVersionAttributeId, jiraAssetObjectJSONObject),
			getAttributeValue(
				_newVersionAttributeId, jiraAssetObjectJSONObject),
			getAttributeKey(
				_plannedEventDateAttributeId, jiraAssetObjectJSONObject),
			getAttributeValue(_timeZoneAttributeId, jiraAssetObjectJSONObject));
	}

	public BusinessEvent toBusinessEvent(
		String accountExternalReferenceCode, String attributesJSON,
		String authorEmailAddress) {

		JSONObject attributesJSONObject = new JSONObject(attributesJSON);

		return new BusinessEvent(
			accountExternalReferenceCode,
			attributesJSONObject.optString("actualEventDate"),
			attributesJSONObject.optString("associatedTickets"),
			authorEmailAddress, StringPool.BLANK,
			attributesJSONObject.optString("currentLiferayVersion"),
			StringPool.BLANK, attributesJSONObject.optString("description"),
			attributesJSONObject.optString("eventStatus"),
			attributesJSONObject.optString("eventType"),
			attributesJSONObject.optString("lastComment"), authorEmailAddress,
			attributesJSONObject.optString("name"),
			attributesJSONObject.optString("newLiferayVersion"),
			StringPool.BLANK,
			attributesJSONObject.optString("plannedEventDate"),
			attributesJSONObject.optString("timeZone"));
	}

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.account}"
	)
	private String _accountAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.actual." +
			"event.date}"
	)
	private String _actualEventDateAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute." +
			"associated.tickets}"
	)
	private String _associatedTicketsAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.author}"
	)
	private String _authorAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute." +
			"current.version}"
	)
	private String _currentVersionAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute." +
			"description}"
	)
	private String _descriptionAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.event." +
			"status}"
	)
	private String _eventStatusAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.event." +
			"type}"
	)
	private String _eventTypeAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.last." +
			"comment}"
	)
	private String _lastCommentAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.last." +
			"updated.author}"
	)
	private String _lastUpdatedAuthorAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.name}"
	)
	private String _nameAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.new." +
			"version}"
	)
	private String _newVersionAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute." +
			"planned.event.date}"
	)
	private String _plannedEventDateAttributeId;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.time." +
			"zone}"
	)
	private String _timeZoneAttributeId;

}