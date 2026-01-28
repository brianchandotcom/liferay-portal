/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.customer.constants.NotificationSubscriptionConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Ryan Schuhler
 */
@Component
public class NotificationSubscriptionService extends BaseNotificationService {

	public JSONArray getNotificationSubscriptionsJSONArray(String filter)
		throws Exception {

		UriComponentsBuilder uriComponentsBuilder =
			UriComponentsBuilder.fromPath(
				"/o/c/notificationsubscriptions"
			).queryParam(
				"nestedFields",
				NotificationSubscriptionConstants.FIELD_NOTIFICATION_TARGET
			);

		if (filter != null) {
			uriComponentsBuilder.queryParam("filter", filter);
		}

		String json = get(
			getAuthorization(),
			uriComponentsBuilder.build(
			).toUri());

		if ((json == null) || json.isEmpty()) {
			return new JSONArray();
		}

		try {
			JSONObject jsonObject = new JSONObject(json);

			return jsonObject.getJSONArray("items");
		}
		catch (Exception exception) {
			_log.error("Unable to parse JSON: " + json, exception);

			return new JSONArray();
		}
	}

	private static final Log _log = LogFactory.getLog(
		NotificationSubscriptionService.class);

}