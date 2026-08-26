/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.forums;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.forums.client.LiferayApiClient;
import com.liferay.forums.service.ForumNotificationService;
import com.liferay.forums.service.MentionService;
import com.liferay.forums.service.SubscriptionService;
import com.liferay.petra.string.StringBundler;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roselaine Marques
 * @author Neil Griffin
 */
@RestController
public class ForumNotificationRestController extends BaseRestController {

	@PostMapping("/object-action/new-reply")
	public ResponseEntity<String> onNewReply(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		String authToken = _authToken(jwt);

		if (jwt != null) {
			log(jwt, _log, json);
		}
		else if (_log.isInfoEnabled()) {
			_log.info(json);
		}

		_forumNotificationExecutor.execute(
			() -> _fanOut(
				"new-reply", () -> _processNewReply(json, authToken)));

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	@PostMapping("/object-action/updated-reply")
	public ResponseEntity<String> onUpdatedReply(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		String authToken = _authToken(jwt);

		if (jwt != null) {
			log(jwt, _log, json);
		}
		else if (_log.isInfoEnabled()) {
			_log.info(json);
		}

		_forumNotificationExecutor.execute(
			() -> _fanOut(
				"updated-reply", () -> _processUpdatedReply(json, authToken)));

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private String _authToken(Jwt jwt) {
		if (jwt == null) {
			return null;
		}

		return jwt.getTokenValue();
	}

	private Set<String> _capMentions(Set<String> mentionedScreenNames) {
		if (mentionedScreenNames.size() > _MAX_MENTIONS) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Post mentions ", mentionedScreenNames.size(),
						" users; honoring only the first ", _MAX_MENTIONS));
			}

			Set<String> cappedScreenNames = new LinkedHashSet<>();

			for (String mentionedScreenName : mentionedScreenNames) {
				if (cappedScreenNames.size() >= _MAX_MENTIONS) {
					break;
				}

				cappedScreenNames.add(mentionedScreenName);
			}

			return cappedScreenNames;
		}

		return mentionedScreenNames;
	}

	private String _constructDisplayPageUrl(
		JSONObject payloadJSONObject, JSONObject dtoJSONObject,
		JSONObject siteJSONObject, String authToken) {

		if ((payloadJSONObject == null) || (dtoJSONObject == null)) {
			return "";
		}

		String siteFriendlyUrl = (siteJSONObject != null) ?
			siteJSONObject.optString("friendlyUrlPath", "") : "";

		String siteFallbackUrl =
			siteFriendlyUrl.isBlank() ? "" : "/web" + siteFriendlyUrl;

		try {
			long objectDefinitionId = payloadJSONObject.optLong(
				"objectDefinitionId", 0L);
			String entryFriendlyUrl = dtoJSONObject.optString(
				"friendlyUrlPath", "");

			if (siteFriendlyUrl.isBlank() || (objectDefinitionId == 0L) ||
				entryFriendlyUrl.isBlank()) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to construct display page URL; falling back " +
							"to site URL");
				}

				return siteFallbackUrl;
			}

			String objDefResponse = _liferayApiClient.get(
				"/o/object-admin/v1.0/object-definitions/" +
					objectDefinitionId + "?fields=friendlyURLSeparator",
				authToken);

			String urlSeparator = new JSONObject(
				objDefResponse
			).optString(
				"friendlyURLSeparator", ""
			);

			if (urlSeparator.isBlank()) {
				return siteFallbackUrl;
			}

			return StringBundler.concat(
				"/web", siteFriendlyUrl, "/", urlSeparator, "/",
				entryFriendlyUrl);
		}
		catch (Exception exception) {
			_log.error(
				"Failed to construct display page URL: " +
					exception.getMessage());

			return siteFallbackUrl;
		}
	}

	private String _encodePathSegment(String value) {
		return URLEncoder.encode(
			value, StandardCharsets.UTF_8
		).replace(
			"+", "%20"
		);
	}

	private Set<String> _extractCappedMentions(String rawBody) {
		return _capMentions(
			_mentionService.extractMentionedScreenNames(rawBody));
	}

	private void _fanOut(String handler, Runnable task) {
		long start = System.currentTimeMillis();

		try {
			task.run();
		}
		catch (Throwable throwable) {
			_log.error(
				"Unhandled failure in " + handler + " fan-out", throwable);
		}
		finally {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						handler, " fan-out finished in ",
						System.currentTimeMillis() - start, " ms"));
			}
		}
	}

	private String _fetchMessageTitle(long threadId, String authToken) {
		try {
			String response = _liferayApiClient.get(
				"/o/c/forumthreads/" + threadId + "?fields=messageTitle",
				authToken);

			return new JSONObject(
				response
			).optString(
				"messageTitle", null
			);
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to fetch ForumThread title for id=", threadId, ": ",
					exception.getMessage()));

			return null;
		}
	}

	private JSONObject _fetchSite(JSONObject dtoJSONObject, String authToken) {
		if (dtoJSONObject == null) {
			return null;
		}

		JSONObject systemPropertiesJSONObject = dtoJSONObject.optJSONObject(
			"systemProperties");

		JSONObject scopeJSONObject = (systemPropertiesJSONObject != null) ?
			systemPropertiesJSONObject.optJSONObject("scope") : null;

		if (scopeJSONObject == null) {
			return null;
		}

		String siteErc = scopeJSONObject.optString("externalReferenceCode", "");

		if (siteErc.isBlank()) {
			return null;
		}

		try {
			String siteResponse = _liferayApiClient.get(
				"/o/headless-admin-site/v1.0/sites/" +
					_encodePathSegment(siteErc) + "?fields=id,friendlyUrlPath",
				authToken);

			return new JSONObject(siteResponse);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to fetch site for ERC ", siteErc, ": ",
						exception.getMessage()));
			}

			return null;
		}
	}

	private void _notifyMentions(
		Set<String> mentionedScreenNames, String messageTitle, String author,
		String bodyPreview, String url, List<Long> alreadyNotified,
		long authorUserId, long siteId, String authToken) {

		if (mentionedScreenNames.isEmpty()) {
			return;
		}

		List<Long> mentioned = _mentionService.resolveMentions(
			mentionedScreenNames, siteId, authToken);

		List<Long> recipients = new ArrayList<>();

		for (Long userId : mentioned) {
			if (alreadyNotified.contains(userId) ||
				((authorUserId > 0) && (userId == authorUserId))) {

				continue;
			}

			recipients.add(userId);
		}

		if (recipients.isEmpty()) {
			return;
		}

		_forumNotificationService.notifyAll(
			recipients, siteId, author + " mentioned you in: " + messageTitle,
			StringBundler.concat(
				author, " mentioned you in \"", messageTitle, "\": ",
				_truncate(bodyPreview, 300)),
			url, authToken);
	}

	private void _processNewReply(String json, String authToken) {
		JSONObject payloadJSONObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = payloadJSONObject.optJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = (objectEntryJSONObject != null) ?
			objectEntryJSONObject.optJSONObject("values") : null;

		long threadId = 0L;
		String replyBody = "";
		String rawReplyBody = "";

		if (valuesJSONObject != null) {
			threadId = valuesJSONObject.optLong(
				"r_threadMessages_c_forumThreadId", 0L);

			rawReplyBody = valuesJSONObject.optString("body", "");

			replyBody = _stripHtml(rawReplyBody);
		}

		if (threadId == 0L) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"onNewReply: missing r_threadMessages_c_forumThreadId in " +
						"payload");
			}

			return;
		}

		JSONObject dtoJSONObject = payloadJSONObject.optJSONObject(
			"objectEntryDTOForumMessage");

		JSONObject creatorJSONObject =
			(dtoJSONObject != null) ? dtoJSONObject.optJSONObject("creator") :
				null;

		String replyAuthor = _resolveAuthorName(creatorJSONObject);
		long authorUserId = _resolveCreatorUserId(creatorJSONObject);

		JSONObject siteJSONObject = null;
		long siteId = _resolveSiteId(dtoJSONObject, null);

		if (siteId <= 0L) {
			siteJSONObject = _fetchSite(dtoJSONObject, authToken);

			siteId = _resolveSiteId(dtoJSONObject, siteJSONObject);
		}

		List<Long> subscribers = _subscriptionService.getSubscriberUserIds(
			threadId, siteId, authToken);

		subscribers.removeIf(userId -> userId == authorUserId);

		Set<String> mentionedScreenNames = _extractCappedMentions(rawReplyBody);

		if (subscribers.isEmpty() && mentionedScreenNames.isEmpty()) {
			return;
		}

		String messageTitle = _fetchMessageTitle(threadId, authToken);

		if (messageTitle == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"onNewReply: unable to fetch title for threadId=" +
						threadId);
			}

			messageTitle = "Forum Discussion";
		}

		if (siteJSONObject == null) {
			siteJSONObject = _fetchSite(dtoJSONObject, authToken);
		}

		String url = _constructDisplayPageUrl(
			payloadJSONObject, dtoJSONObject, siteJSONObject, authToken);

		if (_log.isInfoEnabled()) {
			_log.info("Constructed Display Page URL for Reply: " + url);
		}

		_forumNotificationService.notifyAll(
			subscribers, siteId, "Re: " + messageTitle,
			StringBundler.concat(
				replyAuthor, " posted a new reply to \"", messageTitle, "\": ",
				_truncate(replyBody, 300)),
			url, authToken);

		_notifyMentions(
			mentionedScreenNames, messageTitle, replyAuthor, replyBody, url,
			subscribers, authorUserId, siteId, authToken);
	}

	private void _processUpdatedReply(String json, String authToken) {
		JSONObject payloadJSONObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = payloadJSONObject.optJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = (objectEntryJSONObject != null) ?
			objectEntryJSONObject.optJSONObject("values") : null;

		JSONObject originalObjectEntryJSONObject =
			payloadJSONObject.optJSONObject("originalObjectEntry");

		JSONObject originalValuesJSONObject =
			(originalObjectEntryJSONObject != null) ?
				originalObjectEntryJSONObject.optJSONObject("values") : null;

		long threadId = 0L;
		String rawReplyBody = "";
		String rawOriginalBody = "";

		if (valuesJSONObject != null) {
			threadId = valuesJSONObject.optLong(
				"r_threadMessages_c_forumThreadId", 0L);
			rawReplyBody = valuesJSONObject.optString("body", "");
		}

		if (originalValuesJSONObject != null) {
			rawOriginalBody = originalValuesJSONObject.optString("body", "");
		}

		if (threadId == 0L) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"onUpdatedReply: missing " +
						"r_threadMessages_c_forumThreadId in payload");
			}

			return;
		}

		Set<String> addedMentions = _mentionService.extractMentionedScreenNames(
			rawReplyBody);

		addedMentions.removeAll(
			_mentionService.extractMentionedScreenNames(rawOriginalBody));

		addedMentions = _capMentions(addedMentions);

		if (addedMentions.isEmpty()) {
			return;
		}

		JSONObject dtoJSONObject = payloadJSONObject.optJSONObject(
			"objectEntryDTOForumMessage");

		JSONObject creatorJSONObject =
			(dtoJSONObject != null) ? dtoJSONObject.optJSONObject("creator") :
				null;

		String replyAuthor = _resolveAuthorName(creatorJSONObject);
		long authorUserId = _resolveCreatorUserId(creatorJSONObject);

		String replyBody = _stripHtml(rawReplyBody);

		String messageTitle = _fetchMessageTitle(threadId, authToken);

		if (messageTitle == null) {
			messageTitle = "Forum Discussion";
		}

		JSONObject siteJSONObject = _fetchSite(dtoJSONObject, authToken);

		String url = _constructDisplayPageUrl(
			payloadJSONObject, dtoJSONObject, siteJSONObject, authToken);
		long siteId = _resolveSiteId(dtoJSONObject, siteJSONObject);

		if (_log.isInfoEnabled()) {
			_log.info("Constructed Display Page URL for Edited Reply: " + url);
		}

		_notifyMentions(
			addedMentions, messageTitle, replyAuthor, replyBody, url, List.of(),
			authorUserId, siteId, authToken);
	}

	private String _resolveAuthorName(JSONObject creatorJSONObject) {
		if (creatorJSONObject != null) {
			String given = creatorJSONObject.optString("givenName", "");
			String family = creatorJSONObject.optString("familyName", "");

			if (!family.isBlank() && !family.equals("User")) {
				String fullName = given + " " + family;

				return fullName.trim();
			}

			if (!given.isBlank()) {
				return given;
			}

			String name = creatorJSONObject.optString("name", "");

			if (!name.isBlank()) {
				return name;
			}
		}

		return "A community member";
	}

	private long _resolveCreatorUserId(JSONObject creatorJSONObject) {
		if (creatorJSONObject != null) {
			return creatorJSONObject.optLong("id", 0L);
		}

		return 0L;
	}

	private long _resolveSiteId(
		JSONObject dtoJSONObject, JSONObject siteJSONObject) {

		if (dtoJSONObject != null) {
			JSONObject systemPropertiesJSONObject = dtoJSONObject.optJSONObject(
				"systemProperties");

			JSONObject scopeJSONObject = (systemPropertiesJSONObject != null) ?
				systemPropertiesJSONObject.optJSONObject("scope") : null;

			if (scopeJSONObject != null) {
				long scopeId = scopeJSONObject.optLong("id", 0L);

				if (scopeId > 0L) {
					return scopeId;
				}
			}
		}

		if (siteJSONObject != null) {
			return siteJSONObject.optLong("id", 0L);
		}

		return 0L;
	}

	private String _stripHtml(String html) {
		if ((html == null) || html.isBlank()) {
			return "";
		}

		return html.replaceAll(
			"<[^>]+>", " "
		).replaceAll(
			"\\s{2,}", " "
		).trim();
	}

	private String _truncate(String text, int maxLength) {
		if (text == null) {
			return "";
		}

		if (text.length() <= maxLength) {
			return text;
		}

		return text.substring(0, maxLength) + "...";
	}

	private static final int _MAX_MENTIONS = 25;

	private static final Log _log = LogFactory.getLog(
		ForumNotificationRestController.class);

	@Autowired
	@Qualifier("forumNotificationExecutor")
	private ThreadPoolTaskExecutor _forumNotificationExecutor;

	@Autowired
	private ForumNotificationService _forumNotificationService;

	@Autowired
	private LiferayApiClient _liferayApiClient;

	@Autowired
	private MentionService _mentionService;

	@Autowired
	private SubscriptionService _subscriptionService;

}