/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.notification.rest.client.dto.v1_0.NotificationQueueEntry;
import com.liferay.notification.rest.client.resource.v1_0.NotificationQueueEntryResource;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class NotificationQueueEntryService extends BaseService {

	public void addNotificationQueueEntry(
			String fromEmail, String fromName, String toEmail, String subject,
			String body)
		throws Exception {

		NotificationQueueEntryResource notificationQueueEntryResource =
			NotificationQueueEntryResource.builder(
			).endpoint(
				lxcDXPMainDomain, lxcDXPServerProtocol
			).header(
				HttpHeaders.AUTHORIZATION,
				_liferayOAuth2AccessTokenManager.getAuthorization(
					"liferay-customer-etc-spring-boot-oahs")
			).build();

		NotificationQueueEntry notificationQueueEntry =
			new NotificationQueueEntry();

		notificationQueueEntry.setBody(() -> body);
		notificationQueueEntry.setRecipients(
			() -> new Object[] {
				new TreeMap<>(
					HashMapBuilder.put(
						"from", fromEmail
					).put(
						"fromName", fromName
					).put(
						"to", toEmail
					).build())
			});
		notificationQueueEntry.setSubject(() -> subject);
		notificationQueueEntry.setType(() -> "email");

		notificationQueueEntryResource.postNotificationQueueEntry(
			notificationQueueEntry);
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}