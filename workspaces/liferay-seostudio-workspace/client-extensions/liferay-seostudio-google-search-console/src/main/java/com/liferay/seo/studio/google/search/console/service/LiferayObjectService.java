/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.google.search.console.service;

import com.google.api.client.http.HttpStatusCodes;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.string.StringBundler;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Pei-Jung Lan
 */
@Service
public class LiferayObjectService {

	public LiferayObjectService(
		LiferayOAuth2AccessTokenManager liferayOAuth2AccessTokenManager,
		@Value("${com.liferay.lxc.dxp.mainDomain}") String mainDomain,
		@Value("${com.liferay.lxc.dxp.server.protocol}") String protocol) {

		_liferayOAuth2AccessTokenManager = liferayOAuth2AccessTokenManager;

		_httpClient = HttpClient.newHttpClient();
		_liferayBaseURL = protocol + "://" + mainDomain;
	}

	public void deleteCredentialEntry(long id)
		throws InterruptedException, IOException {

		HttpRequest httpRequest = HttpRequest.newBuilder(
		).header(
			"Authorization", _getAuthorization()
		).method(
			"DELETE", HttpRequest.BodyPublishers.noBody()
		).uri(
			URI.create(
				StringBundler.concat(
					_liferayBaseURL, _CREDENTIAL_ENTRIES_PATH, "/", id))
		).build();

		HttpResponse<Void> httpResponse = _httpClient.send(
			httpRequest, HttpResponse.BodyHandlers.discarding());

		if (httpResponse.statusCode() !=
				HttpStatusCodes.STATUS_CODE_NO_CONTENT) {

			throw new IOException(
				"Unable to delete credential entry, HTTP " +
					httpResponse.statusCode());
		}
	}

	public CredentialEntry fetchCredentialEntry(long seoStudioInstanceId)
		throws InterruptedException, IOException {

		HttpRequest httpRequest = HttpRequest.newBuilder(
		).header(
			"Authorization", _getAuthorization()
		).uri(
			UriComponentsBuilder.fromUriString(
				_liferayBaseURL + _CREDENTIAL_ENTRIES_PATH
			).queryParam(
				"filter",
				StringBundler.concat(
					"r_seoStudioInstanceToGSCCredentialEntries_",
					"seoStudioInstanceId eq '", seoStudioInstanceId, "'")
			).build(
			).toUri()
		).build();

		HttpResponse<String> httpResponse = _httpClient.send(
			httpRequest, HttpResponse.BodyHandlers.ofString());

		if (httpResponse.statusCode() ==
				HttpStatusCodes.STATUS_CODE_NOT_FOUND) {

			return null;
		}

		_checkStatus("fetch credential entry", httpResponse.statusCode());

		JSONArray jsonArray = new JSONObject(
			httpResponse.body()
		).getJSONArray(
			"items"
		);

		if (jsonArray.isEmpty()) {
			return null;
		}

		return _toCredentialEntry(jsonArray.getJSONObject(0));
	}

	public void updateTokens(
			String accessToken, Instant accessTokenExpirationTime,
			String emailAddress, long id, String refreshToken)
		throws InterruptedException, IOException {

		JSONObject jsonObject = new JSONObject(
		).put(
			"accessToken", accessToken
		).put(
			"emailAddress", emailAddress
		).put(
			"refreshToken", refreshToken
		);

		if (accessTokenExpirationTime != null) {
			jsonObject.put(
				"accessTokenExpirationTime",
				accessTokenExpirationTime.truncatedTo(
					ChronoUnit.SECONDS
				).toString());
		}

		_patchById(jsonObject.toString(), id);
	}

	public static class CredentialEntry {

		public CredentialEntry(
			String accessToken, Instant accessTokenExpirationTime,
			String clientId, String clientSecret, String emailAddress, long id,
			String refreshToken) {

			_accessToken = accessToken;
			_accessTokenExpirationTime = accessTokenExpirationTime;
			_clientId = clientId;
			_clientSecret = clientSecret;
			_emailAddress = emailAddress;
			_id = id;
			_refreshToken = refreshToken;
		}

		public String getAccessToken() {
			return _accessToken;
		}

		public Instant getAccessTokenExpirationTime() {
			return _accessTokenExpirationTime;
		}

		public String getClientId() {
			return _clientId;
		}

		public String getClientSecret() {
			return _clientSecret;
		}

		public String getEmailAddress() {
			return _emailAddress;
		}

		public long getId() {
			return _id;
		}

		public String getRefreshToken() {
			return _refreshToken;
		}

		private final String _accessToken;
		private final Instant _accessTokenExpirationTime;
		private final String _clientId;
		private final String _clientSecret;
		private final String _emailAddress;
		private final long _id;
		private final String _refreshToken;

	}

	private void _checkStatus(String operation, int statusCode)
		throws IOException {

		if (statusCode != HttpStatusCodes.STATUS_CODE_OK) {
			throw new IOException(
				StringBundler.concat(
					"Unable to ", operation, ", HTTP ", statusCode));
		}
	}

	private String _getAuthorization() throws IOException {
		String authorization =
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-seostudio-google-search-console-oahs");

		if (authorization == null) {
			throw new IOException("Unable to obtain an authorization token");
		}

		return authorization;
	}

	private void _patchById(String body, long id)
		throws InterruptedException, IOException {

		HttpRequest httpRequest = HttpRequest.newBuilder(
		).header(
			"Authorization", _getAuthorization()
		).header(
			"Content-Type", "application/json"
		).method(
			"PATCH", HttpRequest.BodyPublishers.ofString(body)
		).uri(
			URI.create(
				StringBundler.concat(
					_liferayBaseURL, _CREDENTIAL_ENTRIES_PATH, "/", id))
		).build();

		HttpResponse<Void> httpResponse = _httpClient.send(
			httpRequest, HttpResponse.BodyHandlers.discarding());

		_checkStatus("update credential entry", httpResponse.statusCode());
	}

	private CredentialEntry _toCredentialEntry(JSONObject jsonObject) {
		String accessToken = null;

		if (!jsonObject.isNull("accessToken")) {
			accessToken = jsonObject.getString("accessToken");
		}

		Instant accessTokenExpirationTime = null;

		if (!jsonObject.isNull("accessTokenExpirationTime")) {
			accessTokenExpirationTime = Instant.parse(
				jsonObject.getString("accessTokenExpirationTime"));
		}

		String clientId = null;

		if (!jsonObject.isNull("clientId")) {
			clientId = jsonObject.getString("clientId");
		}

		String clientSecret = null;

		if (!jsonObject.isNull("clientSecret")) {
			clientSecret = jsonObject.getString("clientSecret");
		}

		String emailAddress = null;

		if (!jsonObject.isNull("emailAddress")) {
			emailAddress = jsonObject.getString("emailAddress");
		}

		String refreshToken = null;

		if (!jsonObject.isNull("refreshToken")) {
			refreshToken = jsonObject.getString("refreshToken");
		}

		return new CredentialEntry(
			accessToken, accessTokenExpirationTime, clientId, clientSecret,
			emailAddress, jsonObject.getLong("id"), refreshToken);
	}

	private static final String _CREDENTIAL_ENTRIES_PATH =
		"/o/seo-studio/gsc-credential-entries";

	private final HttpClient _httpClient;
	private final String _liferayBaseURL;
	private final LiferayOAuth2AccessTokenManager
		_liferayOAuth2AccessTokenManager;

}