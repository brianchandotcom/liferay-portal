/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.customer.constants.NotificationSubscriptionConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Ryan Schuhler
 */
@Component
public class BusinessEventNotificationService extends BaseNotificationService {

	@Scheduled(
		cron = "${liferay.customer.notification.subscription.business.event.cron:-}"
	)
	public void sendNotifications() {
		try {
			sendNotifications(_lastSuccessfulRunZonedDateTime);

			_lastSuccessfulRunZonedDateTime = ZonedDateTime.now();
		}
		catch (Exception exception) {
			_log.error("Error sending business event notifications", exception);
		}
	}

	public void sendNotifications(ZonedDateTime zonedDateTime)
		throws Exception {

		if (zonedDateTime == null) {
			zonedDateTime = ZonedDateTime.now(
				ZoneOffset.UTC
			).minusDays(
				1
			);
		}

		String fromDate = zonedDateTime.withNano(
			0
		).toInstant(
		).toString();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Checking for business event notifications since " + fromDate);
		}

		JSONObject jsonObject = new JSONObject(
			get(
				getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/c/businessevents"
				).queryParam(
					"filter", "dateModified ge " + fromDate
				).queryParam(
					"nestedFields",
					NotificationSubscriptionConstants.
						FIELD_ACCOUNT_ENTRY_TO_BUSINESS_EVENT
				).build(
				).toUri()));

		JSONArray businessEventsJSONArray = jsonObject.getJSONArray("items");

		if (businessEventsJSONArray.length() == 0) {
			if (_log.isInfoEnabled()) {
				_log.info("No new business events to notify");
			}

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Found " + businessEventsJSONArray.length() +
					" business events to notify");
		}

		for (int i = 0; i < businessEventsJSONArray.length(); i++) {
			JSONObject businessEventJSONObject =
				businessEventsJSONArray.getJSONObject(i);

			JSONObject accountEntryJSONObject =
				businessEventJSONObject.getJSONObject(
					NotificationSubscriptionConstants.
						FIELD_ACCOUNT_ENTRY_TO_BUSINESS_EVENT);

			String externalReferenceCode = accountEntryJSONObject.getString(
				"externalReferenceCode");

			if (Validator.isNull(externalReferenceCode)) {
				continue;
			}

			String subscriptionFilter =
				"type eq 'businessEvent' and contains(filter, '" +
					escapeFilterValue(externalReferenceCode) + "')";

			JSONArray subscriptionsJSONArray =
				_notificationSubscriptionService.
					getNotificationSubscriptionsJSONArray(subscriptionFilter);

			if (subscriptionsJSONArray.length() == 0) {
				continue;
			}

			long businessEventId = businessEventJSONObject.getLong("id");

			String businessEventVersions = StringPool.BLANK;

			try {
				businessEventVersions = _getBusinessEventVersions(
					zonedDateTime, fromDate, businessEventId);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to fetch business event versions for " +
							"business event " + businessEventId,
						exception);
				}
			}

			JSONObject templatePayloadJSONObject = new JSONObject();

			templatePayloadJSONObject.put(
				"BUSINESSEVENT_ACTIVITY_HISTORY_PAGE_LINK",
				String.format(
					"%s/project/%s/business-events/%s/activity-history",
					portalUrl, externalReferenceCode, businessEventId)
			).put(
				"BUSINESSEVENT_EVENTTYPE",
				businessEventJSONObject.getJSONObject(
					"eventType"
				).optString(
					"key"
				)
			).put(
				"BUSINESSEVENT_LASTCOMMENT",
				businessEventJSONObject.optString("lastComment")
			).put(
				"BUSINESSEVENT_NAME", businessEventJSONObject.optString("name")
			).put(
				"BUSINESSEVENT_TARGETGOLIVEDATETIME",
				businessEventJSONObject.optString("targetGoLiveDateTime")
			).put(
				"BUSINESSEVENT_VERSIONS", businessEventVersions
			).put(
				"PROJECT_NAME", accountEntryJSONObject.optString("name")
			);

			sendNotifications(
				subscriptionsJSONArray, "UPDATED-BUSINESS-EVENTS",
				templatePayloadJSONObject);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Sent " + businessEventsJSONArray.length() +
					" business event notifications");
		}
	}

	private String _getBusinessEventVersions(
			ZonedDateTime zonedDateTime, String fromDate, long businessEventId)
		throws Exception {

		String filter = String.format(
			NotificationSubscriptionConstants.
				FIELD_BUSINESS_EVENT_TO_BUSINESS_EVENT_VERSION +
					" eq '%s' and dateModified ge %s",
			businessEventId, fromDate);

		JSONObject jsonObject = new JSONObject(
			get(
				getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/c/businesseventversions"
				).queryParam(
					"filter", filter
				).queryParam(
					"sort", "dateModified:desc"
				).build(
				).toUri()));

		JSONArray businessEventVersionsJSONArray = jsonObject.getJSONArray(
			"items");

		if (businessEventVersionsJSONArray.length() == 0) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<h4>Recent Updates:</h4>");
		sb.append("<ul>");

		for (int i = 0; i < businessEventVersionsJSONArray.length(); i++) {
			JSONObject businessEventVersionJSONObject =
				businessEventVersionsJSONArray.getJSONObject(i);

			ZonedDateTime curZonedDateTime = ZonedDateTime.parse(
				businessEventVersionJSONObject.getString("dateModified"));

			if (curZonedDateTime.isBefore(zonedDateTime)) {
				continue;
			}

			sb.append("<li><strong>");
			sb.append(
				businessEventVersionJSONObject.optString("name", "Update"));
			sb.append("</strong>");

			String dateModified = businessEventVersionJSONObject.optString(
				"dateModified");

			if (Validator.isNotNull(dateModified)) {
				ZonedDateTime modifiedZonedDateTime = ZonedDateTime.parse(
					dateModified);

				sb.append(" (");
				sb.append(modifiedZonedDateTime.format(_DATE_TIME_FORMATTER));
				sb.append(")");
			}

			sb.append("<br/>");
			sb.append(businessEventVersionJSONObject.optString("comment"));
			sb.append("</li>");
		}

		sb.append("</ul>");

		return sb.toString();
	}

	private static final DateTimeFormatter _DATE_TIME_FORMATTER =
		DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss");

	private static final Log _log = LogFactory.getLog(
		BusinessEventNotificationService.class);

	private final ZonedDateTime _lastSuccessfulRunZonedDateTime;

	@Autowired
	private NotificationSubscriptionService _notificationSubscriptionService;

}