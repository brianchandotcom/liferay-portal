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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class MentionService {

	public Set<String> extractMentionedScreenNames(String bodyHtml) {
		Set<String> screenNames = new LinkedHashSet<>();

		if ((bodyHtml == null) || bodyHtml.isBlank()) {
			return screenNames;
		}

		Matcher matcher = _mentionPattern.matcher(bodyHtml);

		while (matcher.find()) {
			String screenName = matcher.group(1);

			if (!screenName.isBlank()) {
				screenNames.add(StringUtil.toLowerCase(screenName));
			}
		}

		return screenNames;
	}

	public List<Long> resolveMentions(
		Set<String> screenNames, long siteId, String authToken) {

		List<Long> mentioned = new ArrayList<>();

		if ((screenNames == null) || screenNames.isEmpty()) {
			return mentioned;
		}

		if (siteId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Refusing to resolve " + screenNames.size() +
						" mention(s) without a site scope");
			}

			return mentioned;
		}

		StringBundler sb = new StringBundler(screenNames.size() * 4);

		for (String screenName : screenNames) {
			sb.append("alternateName eq '");
			sb.append(_escape(screenName));
			sb.append("'");
			sb.append(" or ");
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		String filter = sb.toString();

		try {
			String response = _liferayApiClient.get(
				StringBundler.concat(
					"/o/headless-admin-user/v1.0/sites/", siteId,
					"/user-accounts?fields=id&pageSize=", screenNames.size(),
					"&filter=", _encodeFilter(filter)),
				authToken);

			JSONArray itemsJSONArray = new JSONObject(
				response
			).optJSONArray(
				"items"
			);

			if (itemsJSONArray != null) {
				for (int i = 0; i < itemsJSONArray.length(); i++) {
					JSONObject itemJSONObject = itemsJSONArray.optJSONObject(i);

					if (itemJSONObject == null) {
						continue;
					}

					long userId = itemJSONObject.optLong("id", 0L);

					if (userId > 0L) {
						mentioned.add(userId);
					}
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to resolve mentions for siteId=", siteId, ": ",
						exception.getMessage()));
			}

			return mentioned;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Resolved ", mentioned.size(), " of ", screenNames.size(),
					" mentioned screen name(s) within siteId=", siteId));
		}

		return mentioned;
	}

	private String _encodeFilter(String filter) {
		return URLEncoder.encode(
			filter, StandardCharsets.UTF_8
		).replace(
			"+", "%20"
		);
	}

	private String _escape(String value) {
		return StringUtil.replace(value, '\'', "''");
	}

	private static final Log _log = LogFactory.getLog(MentionService.class);

	private static final Pattern _mentionPattern = Pattern.compile(
		"(?:^|[\\s\\]>])(?:@|&#64;)(\\w+(?:[.\\-]\\w+)*)");

	@Autowired
	private LiferayApiClient _liferayApiClient;

}