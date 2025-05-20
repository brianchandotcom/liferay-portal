/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.net.URI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Felipe Veloso
 */
@Component
public class OverdueBusinessEventService extends BaseService {

	@Scheduled(cron = "0 0 0 * * *")
	public void scheduled() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		int page = 1;

		while (page > 0) {
			JSONObject jsonObject = new JSONObject(
				get(
					_getAuthorization(),
					URI.create(
						StringBundler.concat(
							"/o/c/businessevents?page=", page,
							"&pageSize=500&filter=eventStatus eq 'open' and ",
							"targetGoLiveDateTime lt ",
							dateFormat.format(date)))));

			JSONArray jsonArray = jsonObject.getJSONArray("items");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject businessEventJSONObject = jsonArray.getJSONObject(i);

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
					URI.create(
						"/o/c/businessevents/" +
							businessEventJSONObject.getInt("id")));

				put(
					_getAuthorization(), StringPool.BLANK,
					URI.create(
						"/o/c/businessevents/" +
							businessEventJSONObject.getInt("id") +
								"/object-actions/overdueBusinessEventAction"));
			}

			if (jsonObject.getInt("lastPage") == page) {
				page = 0;
			}
			else {
				page++;
			}
		}
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oahs");
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}