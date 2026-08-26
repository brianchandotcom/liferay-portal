/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.forums.service;

import com.liferay.forums.client.LiferayApiClient;
import com.liferay.petra.string.StringBundler;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.time.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Roselaine Marques
 * @author Neil Griffin
 */
@Service
public class ForumNotificationService {

	public void notifyAll(
		List<Long> recipientUserIds, long siteId, String subject, String body,
		String url, String authToken) {

		if (recipientUserIds.isEmpty()) {
			return;
		}

		if (siteId <= 0L) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to send forum notifications without a site scope");
			}

			return;
		}

		Map<Long, String> emailAddresses = _resolveEmailAddresses(
			recipientUserIds, authToken);

		String path = "/o/c/forumnotifications/scopes/" + siteId;
		String fullUrl = _siteBaseUrl + url;

		long successCount = Flux.fromIterable(
			recipientUserIds
		).flatMap(
			userId -> _liferayApiClient.postAsync(
				path, authToken,
				_toPayload(
					userId, emailAddresses.get(userId), subject, body, fullUrl)
			).flatMap(
				response -> _purge(response, authToken)
			).onErrorResume(
				throwable -> {
					_log.error(
						StringBundler.concat(
							"Unable to notify user ", userId, ": ",
							throwable.getMessage()));

					return Mono.empty();
				}
			),
			_MAX_SEND_CONCURRENCY
		).count(
		).blockOptional(
			Duration.ofSeconds(_notificationTimeoutSeconds)
		).orElse(
			0L
		);

		if (successCount == 0) {
			_log.error(
				StringBundler.concat(
					"Forum notification reached none of ",
					recipientUserIds.size(), " recipient(s): subject=\"",
					subject, "\""));
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Forum notification sent to ", successCount, "/",
						recipientUserIds.size(), " recipient(s): subject=\"",
						subject, "\""));
			}
		}
	}

	private String _encodeFilter(String filter) {
		return URLEncoder.encode(
			filter, StandardCharsets.UTF_8
		).replace(
			"+", "%20"
		);
	}

	private Mono<Long> _purge(String response, String authToken) {
		long entryId = new JSONObject(
			response
		).optLong(
			"id", 0L
		);

		if (!_purgeEnabled || (entryId <= 0L)) {
			return Mono.just(entryId);
		}

		return _liferayApiClient.deleteAsync(
			"/o/c/forumnotifications/" + entryId, authToken
		).thenReturn(
			entryId
		).onErrorResume(
			throwable -> {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to purge forum notification ", entryId,
							": ", throwable.getMessage()));
				}

				return Mono.just(entryId);
			}
		);
	}

	private void _resolveEmailAddresses(
		List<Long> userIds, Map<Long, String> emailAddresses,
		String authToken) {

		StringBundler sb = new StringBundler(userIds.size() * 4);

		for (Long userId : userIds) {
			sb.append("id eq '");
			sb.append(userId);
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
					"/o/headless-admin-user/v1.0/user-accounts?fields=id,",
					"emailAddress&pageSize=", userIds.size(), "&filter=",
					_encodeFilter(filter)),
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
					String emailAddress = itemJSONObject.optString(
						"emailAddress", "");

					if ((userId > 0L) && !emailAddress.isBlank()) {
						emailAddresses.put(userId, emailAddress);
					}
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to resolve recipient email addresses: " +
						exception.getMessage());
			}
		}
	}

	private Map<Long, String> _resolveEmailAddresses(
		List<Long> userIds, String authToken) {

		Map<Long, String> emailAddresses = new HashMap<>();

		for (int start = 0; start < userIds.size();
			 start += _EMAIL_LOOKUP_BATCH_SIZE) {

			_resolveEmailAddresses(
				userIds.subList(
					start,
					Math.min(start + _EMAIL_LOOKUP_BATCH_SIZE, userIds.size())),
				emailAddresses, authToken);
		}

		return emailAddresses;
	}

	private String _toPayload(
		long recipientUserId, String emailAddress, String subject, String body,
		String url) {

		JSONObject payloadJSONObject = new JSONObject();

		payloadJSONObject.put(
			"notificationBody", body
		).put(
			"notificationSubject", subject
		).put(
			"notificationUrl", url
		).put(
			"recipientUserId", recipientUserId
		);

		if ((emailAddress != null) && !emailAddress.isBlank()) {
			payloadJSONObject.put("recipientEmailAddress", emailAddress);
		}

		return payloadJSONObject.toString();
	}

	private static final int _EMAIL_LOOKUP_BATCH_SIZE = 50;

	private static final int _MAX_SEND_CONCURRENCY = 8;

	private static final Log _log = LogFactory.getLog(
		ForumNotificationService.class);

	@Autowired
	private LiferayApiClient _liferayApiClient;

	@Value("${forums.notification.timeout.seconds:60}")
	private int _notificationTimeoutSeconds;

	@Value("${forums.notification.purge:true}")
	private boolean _purgeEnabled;

	@Value("${forums.site.base.url:https://www.example.xyz}")
	private String _siteBaseUrl;

}