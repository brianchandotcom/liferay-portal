/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Keven Leone
 */
@RequestMapping("/console")
@RestController
public class ConsoleRestController extends BaseRestController {

	@GetMapping("projects-usage")
	public String getProjectsUsage(
		@AuthenticationPrincipal Jwt jwt,
		@RequestParam(required = false) String email) {

		if (email == null) {
			email = jwt.getClaims(
			).get(
				"username"
			)
		}

		return WebClient.create(
			_consoleAuthURL
		).get(
		).uri(
			uriBuilder -> uriBuilder.path(
				"/admin/user-projects-plan-usage"
			).queryParam(
				"userEmail", email
			).build()
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + _getAuthorization()
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	private String _getAuthorization() {
		if ((_accessToken != null) &&
			(System.currentTimeMillis() < (_tokenExpirationMillis - 30000))) {

			return _accessToken;
		}

		String response = WebClient.create(
			_consoleAuthURL
		).post(
		).uri(
			"/login"
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).bodyValue(
			new JSONObject(
			).put(
				"email", _consoleAuthEmail
			).put(
				"password", _consoleAuthPassword
			).toString()
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		if (response == null) {
			return "";
		}

		_accessToken = new JSONObject(
			response
		).getString(
			"token"
		);

		_tokenExpirationMillis = System.currentTimeMillis() + 900000;

		return _accessToken;
	}

	private String _accessToken;

	@Value("${liferay.marketplace.console.auth.email}")
	private String _consoleAuthEmail;

	@Value("${liferay.marketplace.console.auth.password}")
	private String _consoleAuthPassword;

	@Value("${liferay.marketplace.console.auth.url}")
	private String _consoleAuthURL;

	private long _tokenExpirationMillis;

}