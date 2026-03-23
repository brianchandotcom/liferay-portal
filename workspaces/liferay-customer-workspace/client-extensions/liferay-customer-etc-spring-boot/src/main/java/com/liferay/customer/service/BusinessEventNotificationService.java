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
			_sendNotifications(_lastSuccessfulRunZonedDateTime);

			_lastSuccessfulRunZonedDateTime = ZonedDateTime.now();
		}
		catch (Exception exception) {
			_log.error("Error sending business event notifications", exception);
		}
	}

	private JSONObject _fetchKoroneikiAccountJSONObject(
		String externalReferenceCode) {

		try {
			return new JSONObject(
				get(
					getAuthorization(),
					UriComponentsBuilder.fromPath(
						"/o/c/koroneikiaccounts/by-external-reference-code/" +
							externalReferenceCode
					).build(
					).toUri()));
		}
		catch (Exception exception) {
			_log.error(
				"No koroneiki account found for external reference code " +
					externalReferenceCode,
				exception);

			return null;
		}
	}

	private String _getBusinessEventVersions(
		String fromDate, long businessEventId) {

		String filterString = String.format(
			NotificationSubscriptionConstants.
				FIELD_BUSINESS_EVENT_TO_BUSINESS_EVENT_VERSION +
					" eq '%s' and dateModified gt %s",
			businessEventId, fromDate);

		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObject(
				get(
					getAuthorization(),
					UriComponentsBuilder.fromPath(
						"/o/c/businesseventversions"
					).queryParam(
						"filter", filterString
					).queryParam(
						"sort", "dateModified:desc"
					).build(
					).toUri()));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch business event versions for business " +
						"event " + businessEventId,
					exception);
			}

			return StringPool.BLANK;
		}

		JSONArray businessEventVersionsJSONArray = jsonObject.getJSONArray(
			"items");

		if (businessEventVersionsJSONArray.length() == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		sb.append("<h4>Recent Updates:</h4><ul>");

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

	private JSONArray _getSubscriptionsJSONArray(
			JSONObject koroneikiAccountJSONObject)
		throws Exception {

		StringBundler sb = new StringBundler(11);

		sb.append("active eq true and type eq 'businessEvent' and ");
		sb.append("(contains(filter, '");
		sb.append(
			escapeFilterValue(
				koroneikiAccountJSONObject.getString("accountKey")));
		sb.append("')");

		String region = koroneikiAccountJSONObject.getString("region");

		if (Validator.isNotNull(region)) {
			region = region.toUpperCase(
			).replace(
				StringPool.SPACE, StringPool.UNDERLINE
			);

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
		}

		sb.append(")");

		return _notificationSubscriptionService.
			getNotificationSubscriptionsJSONArray(sb.toString());
	}

	private boolean _hasTAMServiceSubscription(
			String accountExternalReferenceCode)
		throws Exception {

		JSONObject jsonObject = new JSONObject(
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

		JSONArray accountSubscriptionsJSONArray = jsonObject.getJSONArray(
			"items");

		if (accountSubscriptionsJSONArray.length() > 0) {
			return true;
		}

		return false;
	}

	private void _sendNotifications(ZonedDateTime zonedDateTime)
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

		for (int i = 0; i < businessEventsJSONArray.length(); i++) {
			JSONObject businessEventJSONObject =
				businessEventsJSONArray.getJSONObject(i);

			String accountExternalReferenceCode =
				businessEventJSONObject.getString(
					NotificationSubscriptionConstants.
						FIELD_ACCOUNT_ENTRY_TO_BUSINESS_EVENT);

			JSONObject koroneikiAccountJSONObject =
				_fetchKoroneikiAccountJSONObject(accountExternalReferenceCode);

			if (koroneikiAccountJSONObject == null) {
				continue;
			}

			JSONArray subscriptionsJSONArray = _getSubscriptionsJSONArray(
				koroneikiAccountJSONObject);

			if (subscriptionsJSONArray.length() == 0) {
				continue;
			}

			JSONObject templatePayloadJSONObject = new JSONObject();

			templatePayloadJSONObject.put(
				"BUSINESSEVENT_ACTIVITY_HISTORY_PAGE_LINK",
				String.format(
					"%s/project/#/%s/business-events/%s/activity-history",
					portalURL, accountExternalReferenceCode,
					businessEventJSONObject.getLong("id"))
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
				"BUSINESSEVENT_VERSIONS",
				_getBusinessEventVersions(
					fromDate, businessEventJSONObject.getLong("id"))
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

	private static final DateTimeFormatter _DATE_TIME_FORMATTER =
		DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss");

	private static final Log _log = LogFactory.getLog(
		BusinessEventNotificationService.class);

	private ZonedDateTime _lastSuccessfulRunZonedDateTime;

	@Autowired
	private NotificationSubscriptionService _notificationSubscriptionService;

}