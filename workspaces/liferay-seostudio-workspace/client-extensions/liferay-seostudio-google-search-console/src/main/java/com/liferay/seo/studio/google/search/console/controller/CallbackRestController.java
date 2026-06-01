/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.google.search.console.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.seo.studio.google.search.console.service.GoogleOAuth2Service;
import com.liferay.seo.studio.google.search.console.service.LiferayObjectService;
import com.liferay.seo.studio.google.search.console.service.StateTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;

import java.time.Instant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Pei-Jung Lan
 */
@RequestMapping("/callback")
@RestController
public class CallbackRestController extends BaseRestController {

	@GetMapping
	public ResponseEntity<Void> get(
		@RequestParam(required = false) String code,
		@RequestParam(required = false) String error,
		HttpServletRequest httpServletRequest,
		@RequestParam(required = false) String state) {

		if ((state == null) || ((code == null) && (error == null))) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		String redirectURL;
		long seoStudioInstanceId;

		try {
			Claims claims = _stateTokenService.verifyState(state);

			redirectURL = claims.get("redirectURL", String.class);

			Number seoStudioInstanceIdNumber = (Number)claims.get(
				"seoStudioInstanceId");

			seoStudioInstanceId = seoStudioInstanceIdNumber.longValue();
		}
		catch (JwtException jwtException) {
			_log.error("Unable to verify state JWT", jwtException);

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (error != null) {
			return _redirect("gsc_error", redirectURL, error);
		}

		try {
			LiferayObjectService.CredentialEntry credentialEntry =
				_liferayObjectService.fetchCredentialEntry(seoStudioInstanceId);

			if (credentialEntry == null) {
				return _redirect(
					"gsc_error", redirectURL, "credential_not_found");
			}

			String callbackURL = ServletUriComponentsBuilder.fromContextPath(
				httpServletRequest
			).path(
				"/callback"
			).toUriString();

			GoogleTokenResponse googleTokenResponse =
				_googleOAuth2Service.exchangeCode(
					callbackURL, credentialEntry.getClientId(),
					credentialEntry.getClientSecret(), code);

			String emailString = _googleOAuth2Service.fetchUserEmail(
				googleTokenResponse.getAccessToken());

			Long expiresInSeconds = googleTokenResponse.getExpiresInSeconds();

			Instant accessTokenExpirationTime = null;

			if (expiresInSeconds != null) {
				accessTokenExpirationTime = Instant.now(
				).plusSeconds(
					expiresInSeconds
				);
			}

			_liferayObjectService.updateTokens(
				googleTokenResponse.getAccessToken(), accessTokenExpirationTime,
				emailString, credentialEntry.getId(),
				googleTokenResponse.getRefreshToken());

			return _redirect("gsc_success", redirectURL, "true");
		}
		catch (Exception exception) {
			_log.error("Unable to process OAuth callback", exception);

			return _redirect("gsc_error", redirectURL, "server_error");
		}
	}

	private ResponseEntity<Void> _redirect(String urlString) {
		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setLocation(URI.create(urlString));

		return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
	}

	private ResponseEntity<Void> _redirect(
		String name, String redirectURL, String value) {

		UriComponents uriComponents = UriComponentsBuilder.fromUriString(
			redirectURL
		).queryParam(
			name, value
		).build();

		return _redirect(uriComponents.toUriString());
	}

	private static final Log _log = LogFactory.getLog(
		CallbackRestController.class);

	@Autowired
	private GoogleOAuth2Service _googleOAuth2Service;

	@Autowired
	private LiferayObjectService _liferayObjectService;

	@Autowired
	private StateTokenService _stateTokenService;

}