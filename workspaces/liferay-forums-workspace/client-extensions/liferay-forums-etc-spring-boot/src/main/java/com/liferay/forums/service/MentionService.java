/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.forums.service;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

		Map<String, Long> userIdsByScreenName =
			_forumUserService.getUserIdsByScreenName(
				screenNames, siteId, authToken);

		mentioned.addAll(userIdsByScreenName.values());

		if (_log.isDebugEnabled() &&
			(userIdsByScreenName.size() < screenNames.size())) {

			_log.debug(
				StringBundler.concat(
					"Resolved ", userIdsByScreenName.size(), " of ",
					screenNames.size(),
					" mention(s); the rest have never posted here"));
		}

		return mentioned;
	}

	private static final Log _log = LogFactory.getLog(MentionService.class);

	private static final Pattern _mentionPattern = Pattern.compile(
		"(?:^|[\\s\\]>])(?:@|&#64;)(\\w+(?:[.\\-]\\w+)*)");

	@Autowired
	private ForumUserService _forumUserService;

}