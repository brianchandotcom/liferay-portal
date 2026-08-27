/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.forums.service;

import com.liferay.forums.client.LiferayApiClient;
import com.liferay.petra.string.StringBundler;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Roselaine Marques
 * @author Neil Griffin
 */
@Service
public class SubscriptionService {

	public List<Long> getSubscriberUserIds(
		long threadId, long siteId, String authToken) {

		List<Long> userIds = new ArrayList<>();

		if (siteId <= 0L) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to read subscriptions without a site scope");
			}

			return userIds;
		}

		String filter = _encodeFilter(
			"r_threadSubscriptions_c_forumThreadId eq '" + threadId + "'");

		int page = 1;

		while (true) {
			String response;

			try {
				response = _liferayApiClient.get(
					StringBundler.concat(
						"/o/c/forumsubscriptions/scopes/", siteId,
						"?fields=subscriberUserId&pageSize=", _PAGE_SIZE,
						"&page=", page, "&filter=", filter),
					authToken);
			}
			catch (Exception exception) {
				_log.error(
					StringBundler.concat(
						"Unable to fetch subscriptions for threadId=", threadId,
						": ", exception.getMessage()));

				break;
			}

			JSONObject responseJSONObject = new JSONObject(response);

			JSONArray itemsJSONArray = responseJSONObject.optJSONArray("items");

			if ((itemsJSONArray == null) || itemsJSONArray.isEmpty()) {
				break;
			}

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				long userId = itemsJSONArray.getJSONObject(
					i
				).optLong(
					"subscriberUserId", 0L
				);

				if (userId > 0L) {
					userIds.add(userId);
				}
			}

			if (page >= responseJSONObject.optLong("lastPage", 1)) {
				break;
			}

			page++;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Found ", userIds.size(), " subscriber(s) for threadId=",
					threadId));
		}

		return userIds;
	}

	private String _encodeFilter(String filter) {
		return URLEncoder.encode(
			filter, StandardCharsets.UTF_8
		).replace(
			"+", "%20"
		);
	}

	private static final int _PAGE_SIZE = 100;

	private static final Log _log = LogFactory.getLog(
		SubscriptionService.class);

	@Autowired
	private LiferayApiClient _liferayApiClient;

}