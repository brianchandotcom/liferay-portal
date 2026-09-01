/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.forums;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.forums.service.ForumModerationService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roselaine Marques
 */
@RequestMapping("/object-validation-rule")
@RestController
public class ObjectValidationRuleRestController extends BaseRestController {

	// The composer disables its submit button for a banned member, which stops
	// nothing outside the browser. This runs before the entry is written.

	@PostMapping("/ban")
	public ResponseEntity<String> ban(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (jwt != null) {
			log(jwt, _log, json);
		}

		JSONObject payloadJSONObject = new JSONObject(json);

		long creatorUserId = _resolveCreatorUserId(payloadJSONObject);

		boolean banned = _forumModerationService.isBanned(
			creatorUserId, _serviceAuthToken());

		if (banned && _log.isInfoEnabled()) {
			_log.info("Refused a post from banned user " + creatorUserId);
		}

		return _respond(payloadJSONObject, !banned);
	}

	// Priority is offered by the composer only to somebody who may add a ban,
	// so that is what decides it here too rather than a rule invented for the
	// server.

	@PostMapping("/priority")
	public ResponseEntity<String> priority(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (jwt != null) {
			log(jwt, _log, json);
		}

		JSONObject payloadJSONObject = new JSONObject(json);

		double priority = payloadJSONObject.optDouble("priority", 0);

		if (priority <= 0) {
			return _respond(payloadJSONObject, true);
		}

		String authToken = _serviceAuthToken();

		if (_forumModerationService.isThreadPriorityUnchanged(
				payloadJSONObject.optString("externalReferenceCode"), priority,
				authToken)) {

			return _respond(payloadJSONObject, true);
		}

		long creatorUserId = _resolveCreatorUserId(payloadJSONObject);

		boolean allowed = _forumModerationService.canAddForumBan(
			creatorUserId, authToken);

		if (!allowed && _log.isInfoEnabled()) {
			_log.info("Refused a thread priority set by user " + creatorUserId);
		}

		return _respond(payloadJSONObject, allowed);
	}

	private long _resolveCreatorUserId(JSONObject payloadJSONObject) {
		JSONObject creatorJSONObject = payloadJSONObject.optJSONObject(
			"creator");

		if (creatorJSONObject != null) {
			return creatorJSONObject.optLong("id", 0L);
		}

		return 0L;
	}

	private ResponseEntity<String> _respond(
		JSONObject payloadJSONObject, boolean validationCriteriaMet) {

		payloadJSONObject.put("validationCriteriaMet", validationCriteriaMet);

		return new ResponseEntity<>(
			payloadJSONObject.toString(), HttpStatus.OK);
	}

	private String _serviceAuthToken() {
		return _liferayOAuth2AccessTokenManager.getTokenValue(
			_OAUTH_APPLICATION_HEADLESS_SERVER_ERC);
	}

	private static final String _OAUTH_APPLICATION_HEADLESS_SERVER_ERC =
		"liferay-forums-etc-spring-boot-oahs";

	private static final Log _log = LogFactory.getLog(
		ObjectValidationRuleRestController.class);

	@Autowired
	private ForumModerationService _forumModerationService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}