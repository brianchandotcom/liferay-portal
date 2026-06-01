/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.google.search.console.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;

import java.util.List;

import org.json.JSONObject;

import org.springframework.stereotype.Service;

/**
 * @author Pei-Jung Lan
 */
@Service
public class GoogleOAuth2Service {

	public String buildAuthorizationURL(
		String clientId, String callbackURI, String state) {

		GoogleAuthorizationCodeRequestUrl googleAuthorizationCodeRequestUrl =
			new GoogleAuthorizationCodeRequestUrl(
				clientId, callbackURI, _scopes);

		googleAuthorizationCodeRequestUrl.set("prompt", "consent");
		googleAuthorizationCodeRequestUrl.setAccessType("offline");
		googleAuthorizationCodeRequestUrl.setState(state);

		return googleAuthorizationCodeRequestUrl.build();
	}

	public GoogleTokenResponse exchangeCode(
			String clientId, String clientSecret, String code,
			String callbackURI)
		throws IOException {

		GoogleAuthorizationCodeTokenRequest
			googleAuthorizationCodeTokenRequest =
				new GoogleAuthorizationCodeTokenRequest(
					_netHttpTransport, GsonFactory.getDefaultInstance(),
					clientId, clientSecret, code, callbackURI);

		return googleAuthorizationCodeTokenRequest.execute();
	}

	public String fetchUserEmail(String accessToken)
		throws InterruptedException, IOException {

		HttpRequest httpRequest = HttpRequest.newBuilder(
		).header(
			"Authorization", "Bearer " + accessToken
		).uri(
			URI.create(_USERINFO_URL)
		).build();

		HttpResponse<String> httpResponse = _httpClient.send(
			httpRequest, HttpResponse.BodyHandlers.ofString());

		if (httpResponse.statusCode() != HttpStatusCodes.STATUS_CODE_OK) {
			throw new IOException(
				"Unable to fetch user email, HTTP " +
					httpResponse.statusCode());
		}

		JSONObject jsonObject = new JSONObject(httpResponse.body());

		return jsonObject.getString("email");
	}

	public void revokeToken(String token)
		throws InterruptedException, IOException {

		HttpRequest httpRequest = HttpRequest.newBuilder(
		).header(
			"Content-Type", "application/x-www-form-urlencoded"
		).method(
			"POST",
			HttpRequest.BodyPublishers.ofString(
				"token=" + URLEncoder.encode(token, StandardCharsets.UTF_8))
		).uri(
			URI.create(_REVOKE_URL)
		).build();

		HttpResponse<Void> httpResponse = _httpClient.send(
			httpRequest, HttpResponse.BodyHandlers.discarding());

		int statusCode = httpResponse.statusCode();

		if ((statusCode != HttpStatusCodes.STATUS_CODE_OK) &&
			(statusCode != HttpStatusCodes.STATUS_CODE_BAD_REQUEST)) {

			throw new IOException("Unable to revoke token, HTTP " + statusCode);
		}
	}

	private static final String _REVOKE_URL =
		"https://oauth2.googleapis.com/revoke";

	private static final String _USERINFO_URL =
		"https://www.googleapis.com/oauth2/v1/userinfo?alt=json";

	private static final List<String> _scopes = List.of(
		"email", "https://www.googleapis.com/auth/webmasters.readonly",
		"openid");

	private final HttpClient _httpClient = HttpClient.newHttpClient();
	private final NetHttpTransport _netHttpTransport = new NetHttpTransport();

}