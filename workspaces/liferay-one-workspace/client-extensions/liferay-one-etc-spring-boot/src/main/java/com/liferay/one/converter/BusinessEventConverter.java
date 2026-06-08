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
		BusinessEvent businessEvent, String accountObjectKey) {

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
				jiraAssetObjectJSONObject, _actualEventDateAttributeId),
			getAttributeValue(
				jiraAssetObjectJSONObject, _associatedTicketsAttributeId),
			getAttributeValue(jiraAssetObjectJSONObject, _authorAttributeId),
			jiraAssetObjectJSONObject.optString("id"),
			getAttributeKey(
				jiraAssetObjectJSONObject, _currentVersionAttributeId),
			getAttributeValue(
				jiraAssetObjectJSONObject, _currentVersionAttributeId),
			getAttributeValue(
				jiraAssetObjectJSONObject, _descriptionAttributeId),
			getAttributeValue(
				jiraAssetObjectJSONObject, _eventStatusAttributeId),
			getAttributeValue(jiraAssetObjectJSONObject, _eventTypeAttributeId),
			getAttributeValue(
				jiraAssetObjectJSONObject, _lastCommentAttributeId),
			getAttributeValue(
				jiraAssetObjectJSONObject, _lastUpdatedAuthorAttributeId),
			getAttributeValue(jiraAssetObjectJSONObject, _nameAttributeId),
			getAttributeKey(jiraAssetObjectJSONObject, _newVersionAttributeId),
			getAttributeValue(
				jiraAssetObjectJSONObject, _newVersionAttributeId),
			getAttributeKey(
				jiraAssetObjectJSONObject, _plannedEventDateAttributeId),
			getAttributeValue(jiraAssetObjectJSONObject, _timeZoneAttributeId));
	}

	public BusinessEvent toBusinessEvent(
		String accountExternalReferenceCode, String authorEmailAddress,
		String attributesJSON) {

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