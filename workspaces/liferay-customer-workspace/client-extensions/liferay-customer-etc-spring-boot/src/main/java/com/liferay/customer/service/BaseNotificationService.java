/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.customer.constants.NotificationSubscriptionConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Ryan Schuhler
 */
public abstract class BaseNotificationService extends BaseService {

	protected String escapeFilterValue(String value) {
		if (value == null) {
			return StringPool.BLANK;
		}

		return StringUtil.replace(value, '\'', "''");
	}

	protected String getAuthorization() {
		return liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oahs");
	}

	protected void sendNotifications(
			JSONArray subscriptionsJSONArray, String templateName,
			JSONObject templatePayloadJSONObject)
		throws Exception {

		for (int i = 0; i < subscriptionsJSONArray.length(); i++) {
			JSONObject subscriptionJSONObject =
				subscriptionsJSONArray.getJSONObject(i);

			if (!subscriptionJSONObject.getBoolean("active")) {
				continue;
			}

			JSONObject notificationTargetJSONObject =
				subscriptionJSONObject.getJSONObject(
					NotificationSubscriptionConstants.
						FIELD_NOTIFICATION_TARGET);

			JSONObject processedTemplateJSONObject =
				notificationTemplateService.getAndProcessTemplate(
					templateName, templatePayloadJSONObject);

			String body = processedTemplateJSONObject.getString("body");
			String subject = processedTemplateJSONObject.getString("subject");

			String target = notificationTargetJSONObject.getString("target");

			notificationQueueEntryService.addNotificationQueueEntry(
				fromEmail, fromName, target, subject, body);
		}
	}

	@Value("${liferay.customer.notification.subscription.from.email}")
	protected String fromEmail;

	@Value("${liferay.customer.notification.subscription.from.name}")
	protected String fromName;

	@Autowired
	protected LiferayOAuth2AccessTokenManager liferayOAuth2AccessTokenManager;

	@Autowired
	protected NotificationQueueEntryService notificationQueueEntryService;

	@Autowired
	protected NotificationTemplateService notificationTemplateService;

	@Value("${liferay.customer.portal.url}")
	protected String portalUrl;

}