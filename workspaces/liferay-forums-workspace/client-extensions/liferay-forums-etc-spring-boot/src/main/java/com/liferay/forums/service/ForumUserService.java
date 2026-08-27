/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.forums.service;

import com.liferay.forums.client.LiferayApiClient;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Roselaine Marques
 */
@Service
public class ForumUserService {

	public Map<String, Long> getUserIdsByScreenName(
		Set<String> screenNames, long siteId, String authToken) {

		Map<String, Long> userIds = new HashMap<>();

		if (screenNames.isEmpty() || (siteId <= 0L)) {
			return userIds;
		}

		StringBundler sb = new StringBundler(screenNames.size() * 4);

		for (String screenName : screenNames) {
			sb.append("screenName eq '");
			sb.append(StringUtil.replace(screenName, '\'', "''"));
			sb.append("'");
			sb.append(" or ");
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		try {
			String response = _liferayApiClient.get(
				StringBundler.concat(
					"/o/c/forumusers/scopes/", siteId,
					"?fields=forumUserId,screenName&pageSize=",
					screenNames.size(), "&filter=", _encode(sb.toString())),
				authToken);

			JSONArray itemsJSONArray = new JSONObject(
				response
			).optJSONArray(
				"items"
			);

			if (itemsJSONArray == null) {
				return userIds;
			}

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.optJSONObject(i);

				if (itemJSONObject == null) {
					continue;
				}

				String screenName = itemJSONObject.optString("screenName", "");
				long forumUserId = itemJSONObject.optLong("forumUserId", 0L);

				if (!screenName.isBlank() && (forumUserId > 0L)) {
					userIds.put(screenName, forumUserId);
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to resolve forum users by screen name: " +
						exception.getMessage());
			}
		}

		return userIds;
	}

	public void upsert(
		long forumUserId, String firstName, String lastName, long siteId,
		String authToken) {

		if ((forumUserId <= 0L) || (siteId <= 0L) ||
			_exists(forumUserId, siteId, authToken)) {

			return;
		}

		String screenName = _fetchScreenName(forumUserId, authToken);

		if (screenName.isBlank()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to record forum user " + forumUserId +
						" without a screen name");
			}

			return;
		}

		JSONObject payloadJSONObject = new JSONObject();

		payloadJSONObject.put(
			"firstName", firstName
		).put(
			"forumUserId", forumUserId
		).put(
			"lastName", lastName
		).put(
			"screenName", screenName
		);

		try {
			_liferayApiClient.post(
				"/o/c/forumusers/scopes/" + siteId, authToken,
				payloadJSONObject.toString());

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Recorded forum user ", forumUserId, " as ",
						screenName));
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to record forum user ", forumUserId, ": ",
						exception.getMessage()));
			}
		}
	}

	private String _encode(String value) {
		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}

	private boolean _exists(long forumUserId, long siteId, String authToken) {
		try {
			String response = _liferayApiClient.get(
				StringBundler.concat(
					"/o/c/forumusers/scopes/", siteId,
					"?fields=forumUserId&pageSize=1&filter=",
					_encode("forumUserId eq " + forumUserId)),
				authToken);

			JSONArray itemsJSONArray = new JSONObject(
				response
			).optJSONArray(
				"items"
			);

			if ((itemsJSONArray != null) && !itemsJSONArray.isEmpty()) {
				return true;
			}

			return false;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to look up forum user ", forumUserId, ": ",
						exception.getMessage()));
			}

			return true;
		}
	}

	private String _fetchScreenName(long forumUserId, String authToken) {
		try {
			String response = _liferayApiClient.get(
				StringBundler.concat(
					"/o/headless-admin-user/v1.0/user-accounts/", forumUserId,
					"?fields=alternateName"),
				authToken);

			return new JSONObject(
				response
			).optString(
				"alternateName", ""
			);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to fetch the screen name for ", forumUserId,
						": ", exception.getMessage()));
			}

			return "";
		}
	}

	private static final Log _log = LogFactory.getLog(ForumUserService.class);

	@Autowired
	private LiferayApiClient _liferayApiClient;

}