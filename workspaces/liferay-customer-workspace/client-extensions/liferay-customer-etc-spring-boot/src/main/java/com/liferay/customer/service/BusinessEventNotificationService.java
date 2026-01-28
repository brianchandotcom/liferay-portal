/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.customer.constants.NotificationSubscriptionConstants;

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
			sendNotifications(
				_lastSuccessfulRun.get(
					_BUSINESS_EVENT_NOTIFICATIONS_LAST_SUCCESSFUL_RUN_KEY));

			_lastSuccessfulRun.put(
				_BUSINESS_EVENT_NOTIFICATIONS_LAST_SUCCESSFUL_RUN_KEY,
				ZonedDateTime.now());
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

		try {
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

			JSONArray businessEventsJSONArray = jsonObject.getJSONArray(
				"items");

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
					businessEventJSONObject.optJSONObject(
						NotificationSubscriptionConstants.
							FIELD_ACCOUNT_ENTRY_TO_BUSINESS_EVENT);

				String externalReferenceCode = accountEntryJSONObject.getString(
					"externalReferenceCode");

				if ((externalReferenceCode == null) ||
					externalReferenceCode.isEmpty()) {

					continue;
				}

				String subscriptionFilter =
					"type eq 'businessEvent' and contains(filter, '" +
						escapeFilterValue(externalReferenceCode) + "')";

				JSONArray subscriptionsJSONArray =
					_notificationSubscriptionService.
						getNotificationSubscriptionsJSONArray(
							subscriptionFilter);

				if (subscriptionsJSONArray.length() == 0) {
					continue;
				}

				String id = String.valueOf(
					businessEventJSONObject.getInt("id"));

				String businessEventVersions = "";

				try {
					String filter = String.format(
						NotificationSubscriptionConstants.
							FIELD_BUSINESS_EVENT_TO_BUSINESS_EVENT_VERSION +
								" eq '%s' and dateModified ge %s",
						id, fromDate);

					JSONObject versionsJSONObject = new JSONObject(
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

					JSONArray businessEventVersionsJSONArray =
						versionsJSONObject.getJSONArray("items");

					if (businessEventVersionsJSONArray.length() > 0) {
						StringBuilder versionsHTML = new StringBuilder();

						versionsHTML.append("<h4>Recent Updates:</h4>");
						versionsHTML.append("<ul>");

						for (int j = 0;
							 j < businessEventVersionsJSONArray.length(); j++) {

							JSONObject versionJSONObject =
								businessEventVersionsJSONArray.getJSONObject(j);

							ZonedDateTime versionDate = ZonedDateTime.parse(
								versionJSONObject.getString("dateModified"));

							if (versionDate.isBefore(zonedDateTime)) {
								continue;
							}

							versionsHTML.append("<li><strong>");
							versionsHTML.append(
								versionJSONObject.optString("name", "Update"));
							versionsHTML.append("</strong>");

							String dateModifiedString =
								versionJSONObject.optString("dateModified");

							if (!dateModifiedString.isEmpty()) {
								ZonedDateTime modifiedDateTime =
									ZonedDateTime.parse(dateModifiedString);

								String formattedDate = modifiedDateTime.format(
									_DATE_TIME_FORMATTER);

								versionsHTML.append(" (");
								versionsHTML.append(formattedDate);
								versionsHTML.append(")");
							}

							versionsHTML.append("<br/>");
							versionsHTML.append(
								versionJSONObject.optString("comment", ""));
							versionsHTML.append("</li>");
						}

						versionsHTML.append("</ul>");

						businessEventVersions = versionsHTML.toString();
					}
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							String.format(
								"Unable to fetch business event versions for " +
									"business event %s",
								id),
							exception);
					}
				}

				String activityHistoryURL = String.format(
					"%s/project/%s/business-events/%s/activity-history",
					portalUrl, externalReferenceCode, id);

				JSONObject templatePayloadJSONObject = new JSONObject();

				templatePayloadJSONObject.put(
					"BUSINESSEVENT_ACTIVITY_HISTORY_PAGE_LINK",
					activityHistoryURL
				).put(
					"BUSINESSEVENT_EVENTTYPE",
					businessEventJSONObject.optJSONObject(
						"eventType"
					).optString(
						"key", ""
					)
				).put(
					"BUSINESSEVENT_LASTCOMMENT",
					businessEventJSONObject.optString("lastComment", "")
				).put(
					"BUSINESSEVENT_NAME",
					businessEventJSONObject.optString("name", "")
				).put(
					"BUSINESSEVENT_TARGETGOLIVEDATETIME",
					businessEventJSONObject.optString(
						"targetGoLiveDateTime", "")
				).put(
					"BUSINESSEVENT_VERSIONS", businessEventVersions
				).put(
					"PROJECT_NAME", accountEntryJSONObject.optString("name", "")
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
		catch (Exception exception) {
			_log.error(
				"Unable to process business event notifications", exception);
		}
	}

	private static final String
		_BUSINESS_EVENT_NOTIFICATIONS_LAST_SUCCESSFUL_RUN_KEY =
			"businessEventNotifications";

	private static final DateTimeFormatter _DATE_TIME_FORMATTER =
		DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss");

	private static final Log _log = LogFactory.getLog(
		BusinessEventNotificationService.class);

	private final Map<String, ZonedDateTime> _lastSuccessfulRun =
		new ConcurrentHashMap<>();

	@Autowired
	private NotificationSubscriptionService _notificationSubscriptionService;

}