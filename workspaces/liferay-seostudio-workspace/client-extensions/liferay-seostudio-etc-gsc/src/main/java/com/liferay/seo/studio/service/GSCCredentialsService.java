/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
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
public class GSCCredentialsService extends BaseService {

	public void deleteCredentials(long id) {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			_CREDENTIALS_PATH + "/" + id
		).build();

		delete(_getAuthorization(), StringPool.BLANK, uriComponents.toUri());
	}

	public Credentials fetchCredentials(long seoStudioDomainId) {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			_CREDENTIALS_PATH
		).queryParam(
			"filter",
			"r_seoStudioDomainToSEOStudioGSCCredentials_seoStudioDomainId eq " +
				seoStudioDomainId
		).build();

		JSONObject jsonObject = new JSONObject(
			get(_getAuthorization(), uriComponents.toUri()));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		if (jsonArray.isEmpty()) {
			return null;
		}

		return _toCredentials(jsonArray.getJSONObject(0));
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
			_CREDENTIALS_PATH + "/" + id
		).build();

		patch(
			_getAuthorization(), jsonObject.toString(), uriComponents.toUri());
	}

	public static class Credentials {

		public Credentials(
			String accessToken, String clientId, String clientSecret,
			String emailAddress, long id, String refreshToken) {

			_accessToken = accessToken;
			_clientId = clientId;
			_clientSecret = clientSecret;
			_emailAddress = emailAddress;
			_id = id;
			_refreshToken = refreshToken;
		}

		public String getAccessToken() {
			return _accessToken;
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
		private final String _clientId;
		private final String _clientSecret;
		private final String _emailAddress;
		private final long _id;
		private final String _refreshToken;

	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-seostudio-etc-gsc-oahs");
	}

	private Credentials _toCredentials(JSONObject jsonObject) {
		return new Credentials(
			jsonObject.optString("accessToken", null),
			jsonObject.optString("clientId", null),
			jsonObject.optString("clientSecret", null),
			jsonObject.optString("emailAddress", null),
			jsonObject.getLong("id"),
			jsonObject.optString("refreshToken", null));
	}

	private static final String _CREDENTIALS_PATH =
		"/o/seo-studio/gsc-credentials";

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}