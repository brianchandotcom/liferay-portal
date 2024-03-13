/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.util;

import java.net.URL;

import java.nio.charset.Charset;

import java.util.Arrays;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class AuthorizationUtil {

	public static String getOAuthAccessToken(
			Map<String, String> authenticationMap, URL dxpURL,
			String dxpOAuthClientId, String dxpOAuthClientSecret)
		throws Exception {

		if ((authenticationMap.get("access_token") != null) &&
			(System.currentTimeMillis() <
				(Long.valueOf(authenticationMap.get("expires_in")) - 15000))) {

			return authenticationMap.get("access_token");
		}

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		HttpPost httpPost = new HttpPost(dxpURL + "/o/oauth2/token");

		httpPost.setEntity(
			new UrlEncodedFormEntity(
				Arrays.asList(
					new BasicNameValuePair("client_id", dxpOAuthClientId),
					new BasicNameValuePair(
						"client_secret", dxpOAuthClientSecret),
					new BasicNameValuePair(
						"grant_type", "client_credentials"))));
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build();
			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost)) {

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				throw new Exception("Unable to get OAuth access token");
			}

			JSONObject jsonObject = new JSONObject(
				EntityUtils.toString(
					closeableHttpResponse.getEntity(),
					Charset.defaultCharset()));

			String accessToken =
				jsonObject.getString("token_type") + " " +
					jsonObject.getString("access_token");

			authenticationMap.put("access_token", accessToken);

			authenticationMap.put(
				"expires_in",
				String.valueOf(jsonObject.getLong("expires_in") * 1000) +
					System.currentTimeMillis());

			return accessToken;
		}
	}

}