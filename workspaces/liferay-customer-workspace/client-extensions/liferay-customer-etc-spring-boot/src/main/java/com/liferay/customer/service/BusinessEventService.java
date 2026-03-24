/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Felipe Veloso
 */
@Component
public class BusinessEventService extends BaseService {

	@Scheduled(cron = "${liferay.customer.business.event.cron}")
	public void scheduled() {
		try {
			_updateOverdueBusinessEvents();
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		_businessEventNotificationService.sendNotifications();
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oahs");
	}

	private void _updateOverdueBusinessEvents() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		int page = 1;

		while (page > 0) {
			JSONObject jsonObject = new JSONObject(
				get(
					_getAuthorization(),
					UriComponentsBuilder.fromPath(
						"/o/c/businessevents"
					).queryParam(
						"filter",
						"eventStatus eq 'open' and targetGoLiveDateTime lt " +
							dateFormat.format(date)
					).queryParam(
						"page", page
					).queryParam(
						"pageSize", 500
					).build(
					).toUri()));

			JSONArray jsonArray = jsonObject.getJSONArray("items");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject businessEventJSONObject = jsonArray.getJSONObject(i);

				try {
					patch(
						_getAuthorization(),
						new JSONObject(
						).put(
							"eventStatus",
							new JSONObject(
							).put(
								"key", "overdue"
							).put(
								"name", "Overdue"
							)
						).toString(),
						UriComponentsBuilder.fromPath(
							"/o/c/businessevents/" +
								businessEventJSONObject.getInt("id")
						).build(
						).toUri());
				}
				catch (Exception exception) {
					_log.error(
						"Unable to update business event:\n" +
							businessEventJSONObject.toString(),
						exception);
				}
			}

			if (jsonObject.getInt("lastPage") == page) {
				page = 0;
			}
			else {
				page++;
			}
		}
	}

	private static final Log _log = LogFactory.getLog(
		BusinessEventService.class);

	@Autowired
	private BusinessEventNotificationService _businessEventNotificationService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}