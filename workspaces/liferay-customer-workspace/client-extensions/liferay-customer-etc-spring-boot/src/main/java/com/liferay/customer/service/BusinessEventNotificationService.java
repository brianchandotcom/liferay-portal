/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.customer.constants.NotificationSubscriptionConstants;
import com.liferay.customer.constants.NotificationTemplateConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Ryan Schuhler
 */
@Component
public class BusinessEventNotificationService extends BaseNotificationService {

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
					"filter", "dateModified gt " + fromDate
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

			String accountExternalReferenceCode =
				businessEventJSONObject.getString(
					NotificationSubscriptionConstants.
						FIELD_ACCOUNT_ENTRY_TO_BUSINESS_EVENT);

			JSONObject koroneikiAccountJSONObject = null;

			try {
				koroneikiAccountJSONObject = _getKoroneikiAccountJSONObject(
					accountExternalReferenceCode);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}

				continue;
			}

			String regionFilter = StringPool.BLANK;

			try {
				regionFilter = _getRegionFilter(koroneikiAccountJSONObject);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}

			String subscriptionFilter = StringBundler.concat(
				"type eq 'businessEvent' and (contains(filter, '",
				escapeFilterValue(accountExternalReferenceCode), "')",
				regionFilter, ") and active eq true");

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
					fromDate, businessEventId);
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
					"%s/project/#/%s/business-events/%s/activity-history",
					portalURL, accountExternalReferenceCode, businessEventId)
			).put(
				"BUSINESSEVENT_EVENTTYPE",
				businessEventJSONObject.getJSONObject(
					"eventType"
				).optString(
					"key"
				)
			).put(
				"BUSINESSEVENT_LASTCOMMENT",
				HtmlUtil.escape(
					businessEventJSONObject.optString("lastComment"))
			).put(
				"BUSINESSEVENT_NAME", businessEventJSONObject.optString("name")
			).put(
				"BUSINESSEVENT_TARGETGOLIVEDATETIME",
				businessEventJSONObject.optString("targetGoLiveDateTime")
			).put(
				"BUSINESSEVENT_VERSIONS", businessEventVersions
			).put(
				"PROJECT_NAME", koroneikiAccountJSONObject.optString("name")
			);

			sendNotifications(
				subscriptionsJSONArray,
				NotificationTemplateConstants.
					EXTERNAL_REFERENCE_CODE_UPDATED_BUSINESS_EVENTS,
				templatePayloadJSONObject);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Sent " + businessEventsJSONArray.length() +
					" business event notifications");
		}
	}

	private String _getBusinessEventVersions(
			String fromDate, long businessEventId)
		throws Exception {

		String filter = String.format(
			NotificationSubscriptionConstants.
				FIELD_BUSINESS_EVENT_TO_BUSINESS_EVENT_VERSION +
					" eq '%s' and dateModified gt %s",
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

		StringBundler sb = new StringBundler();

		sb.append("<h4>Recent Updates:</h4>");
		sb.append("<ul>");

		for (int i = 0; i < businessEventVersionsJSONArray.length(); i++) {
			JSONObject businessEventVersionJSONObject =
				businessEventVersionsJSONArray.getJSONObject(i);

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
			sb.append(
				HtmlUtil.escape(
					businessEventVersionJSONObject.optString("comment")));
			sb.append("</li>");
		}

		sb.append("</ul>");

		return sb.toString();
	}

	private JSONObject _getKoroneikiAccountJSONObject(
			String externalReferenceCode)
		throws Exception {

		JSONObject koroneikiAccountJSONObject = new JSONObject(
			get(
				getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/c/koroneikiaccounts/by-external-reference-code/" +
						externalReferenceCode
				).build(
				).toUri()));

		if (koroneikiAccountJSONObject.isEmpty()) {
			throw new Exception(
				"No koroneiki account found for external reference code " +
					externalReferenceCode);
		}

		return koroneikiAccountJSONObject;
	}

	private String _getRegionFilter(JSONObject koroneikiAccountJSONObject)
		throws Exception {

		String region = koroneikiAccountJSONObject.getString("region");

		region = region.toUpperCase(
		).replace(
			" ", StringPool.UNDERLINE
		);

		StringBundler sb = new StringBundler(6);

		sb.append(" or contains(filter, '");
		sb.append(region);
		sb.append(":RSM')");

		boolean hasTAMServiceSubscription = _hasTAMServiceSubscription(
			koroneikiAccountJSONObject.getString("accountKey"));

		if (hasTAMServiceSubscription) {
			sb.append(" or contains(filter, '");
			sb.append(region);
			sb.append(":CX_LEAD')");
		}

		return sb.toString();
	}

	private boolean _hasTAMServiceSubscription(
			String accountExternalReferenceCode)
		throws Exception {

		JSONObject accountSubscriptionsJSONObject = new JSONObject(
			get(
				getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/c/accountsubscriptions"
				).queryParam(
					"filter",
					StringBundler.concat(
						"accountKey eq '", accountExternalReferenceCode,
						"' and contains(name, 'Technical Account Management ",
						"Services')")
				).build(
				).toUri()));

		JSONArray accountSubscriptionsJSONArray =
			accountSubscriptionsJSONObject.getJSONArray("items");

		if (accountSubscriptionsJSONArray.length() > 0) {
			return true;
		}

		return false;
	}

	private static final DateTimeFormatter _DATE_TIME_FORMATTER =
		DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss");

	private static final Log _log = LogFactory.getLog(
		BusinessEventNotificationService.class);

	private ZonedDateTime _lastSuccessfulRunZonedDateTime;

	@Autowired
	private NotificationSubscriptionService _notificationSubscriptionService;

}