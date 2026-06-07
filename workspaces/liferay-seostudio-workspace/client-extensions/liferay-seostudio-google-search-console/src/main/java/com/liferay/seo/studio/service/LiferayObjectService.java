/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Pei-Jung Lan
 */
@Component
public class LiferayObjectService extends BaseService {

	public void deleteCredentialEntry(long id) {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			_CREDENTIAL_ENTRIES_PATH + "/" + id
		).build();

		delete(_getAuthorization(), StringPool.BLANK, uriComponents.toUri());
	}

	public CredentialEntry fetchCredentialEntry(long seoStudioDomainId) {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			_CREDENTIAL_ENTRIES_PATH
		).queryParam(
			"filter",
			StringBundler.concat(
				"r_seoStudioDomainToSEOStudioGSCCEntries_seoStudioDomainId eq ",
				"'", seoStudioDomainId, "'")
		).build();

		JSONObject jsonObject = new JSONObject(
			get(_getAuthorization(), uriComponents.toUri()));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		if (jsonArray.isEmpty()) {
			return null;
		}

		return _toCredentialEntry(jsonArray.getJSONObject(0));
	}

	public void updateTokens(
		String accessToken, Instant accessTokenExpirationTime,
		String emailAddress, long id, String refreshToken) {

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

		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			_CREDENTIAL_ENTRIES_PATH + "/" + id
		).build();

		patch(
			_getAuthorization(), jsonObject.toString(), uriComponents.toUri());
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

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-seostudio-google-search-console-oahs");
	}

	private CredentialEntry _toCredentialEntry(JSONObject jsonObject) {
		String accessToken = null;

		if (jsonObject.has("accessToken") &&
			!jsonObject.isNull("accessToken")) {

			accessToken = jsonObject.getString("accessToken");
		}

		Instant accessTokenExpirationTime = null;

		if (jsonObject.has("accessTokenExpirationTime") &&
			!jsonObject.isNull("accessTokenExpirationTime")) {

			accessTokenExpirationTime = Instant.parse(
				jsonObject.getString("accessTokenExpirationTime"));
		}

		String clientId = null;

		if (jsonObject.has("clientId") && !jsonObject.isNull("clientId")) {
			clientId = jsonObject.getString("clientId");
		}

		String clientSecret = null;

		if (jsonObject.has("clientSecret") &&
			!jsonObject.isNull("clientSecret")) {

			clientSecret = jsonObject.getString("clientSecret");
		}

		String emailAddress = null;

		if (jsonObject.has("emailAddress") &&
			!jsonObject.isNull("emailAddress")) {

			emailAddress = jsonObject.getString("emailAddress");
		}

		String refreshToken = null;

		if (jsonObject.has("refreshToken") &&
			!jsonObject.isNull("refreshToken")) {

			refreshToken = jsonObject.getString("refreshToken");
		}

		return new CredentialEntry(
			accessToken, accessTokenExpirationTime, clientId, clientSecret,
			emailAddress, jsonObject.getLong("id"), refreshToken);
	}

	private static final String _CREDENTIAL_ENTRIES_PATH =
		"/o/seo-studio/gsc-credential-entries";

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}