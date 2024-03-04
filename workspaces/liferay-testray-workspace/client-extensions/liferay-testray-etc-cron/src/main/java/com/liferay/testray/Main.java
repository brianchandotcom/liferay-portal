/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray;

import java.net.URL;

import java.nio.charset.Charset;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import java.util.Arrays;
import java.util.Collections;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Nilton Vieira
 */
public class Main {

	public static void main(String[] arguments) throws Exception {
		Main main = new Main(
			System.getenv("LIFERAY_TESTRAY_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID"),
			System.getenv(
				"LIFERAY_TESTRAY_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET"),
			new URL(System.getenv("LIFERAY_TESTRAY_ETC_CRON_LIFERAY_URL")));

		main.deleteTestrayArchivedBuilds();
		main.autoArchiveTestrayBuilds();
	}

	public Main(
			String liferayOAuthClientId, String liferayOAuthClientSecret,
			URL liferayURL)
		throws Exception {

		_liferayOAuthClientId = liferayOAuthClientId;
		_liferayOAuthClientSecret = liferayOAuthClientSecret;
		_liferayURL = liferayURL;
	}

	public void autoArchiveTestrayBuilds() throws Exception {
		String oAuthAuthorization = _getOAuthAuthorization();

		JSONArray testrayBuildsJSONArray = _getTestrayBuildsJSONArray(
			oAuthAuthorization,
			"archived eq false and promoted eq false and dateCreated lt " +
				_currentDateTime.minusDays(60));

		if ((testrayBuildsJSONArray == null) ||
			(testrayBuildsJSONArray.length() == 0)) {

			return;
		}

		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < testrayBuildsJSONArray.length(); i++) {
			JSONObject jsonObject = (JSONObject)testrayBuildsJSONArray.get(i);

			jsonObject.put("archived", true);
			jsonObject.put("dateArchived", _currentDateTime);

			jsonArray.put(jsonObject);
		}

		_putTestrayBuilds(oAuthAuthorization, jsonArray);
	}

	public void deleteTestrayArchivedBuilds() throws Exception {
		String oAuthAuthorization = _getOAuthAuthorization();

		JSONArray testrayBuildsJSONArray = _getTestrayBuildsJSONArray(
			oAuthAuthorization,
			"archived eq true and dateArchived lt " +
				_currentDateTime.minusDays(30));

		if ((testrayBuildsJSONArray == null) ||
			(testrayBuildsJSONArray.length() == 0)) {

			return;
		}

		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < testrayBuildsJSONArray.length(); i++) {
			JSONObject jsonObject = (JSONObject)testrayBuildsJSONArray.get(i);

			jsonArray.put(
				Collections.singletonMap("id", jsonObject.getLong("id")));
		}

		_deleteTestrayBuilds(oAuthAuthorization, jsonArray);
	}

	private JSONObject _deleteTestrayBuilds(
			String authorization, JSONArray jsonArray)
		throws Exception {

		HttpDeleteBatch httpDelete = new HttpDeleteBatch(
			_liferayURL + "/o/c/builds/batch");

		httpDelete.setHeader("Authorization", authorization);
		httpDelete.setHeader("accept", "application/json");
		httpDelete.setHeader("Content-Type", "application/json");
		httpDelete.setEntity(new StringEntity(jsonArray.toString()));

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build();
			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpDelete)) {

			return new JSONObject(
				EntityUtils.toString(
					closeableHttpResponse.getEntity(),
					Charset.defaultCharset()));
		}
	}

	private String _getOAuthAuthorization() throws Exception {
		HttpPost httpPost = new HttpPost(_liferayURL + "/o/oauth2/token");

		httpPost.setEntity(
			new UrlEncodedFormEntity(
				Arrays.asList(
					new BasicNameValuePair("client_id", _liferayOAuthClientId),
					new BasicNameValuePair(
						"client_secret", _liferayOAuthClientSecret),
					new BasicNameValuePair(
						"grant_type", "client_credentials"))));
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build();
			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost)) {

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject jsonObject = new JSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));

				return jsonObject.getString("token_type") + " " +
					jsonObject.getString("access_token");
			}

			throw new Exception("Unable to get OAuth authorization");
		}
	}

	private JSONArray _getTestrayBuildsJSONArray(
			String authorization, String filterString)
		throws Exception {

		URIBuilder uriBuilder = new URIBuilder(_liferayURL + "/o/c/builds");

		if (filterString != null) {
			uriBuilder.addParameter("filter", filterString);
		}

		uriBuilder.addParameter("pageSize", "500");

		HttpGet httpGet = new HttpGet(uriBuilder.build());

		httpGet.setHeader("Authorization", authorization);
		httpGet.setHeader("accept", "application/json");

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build();
			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpGet)) {

			return new JSONObject(
				EntityUtils.toString(
					closeableHttpResponse.getEntity(), Charset.defaultCharset())
			).getJSONArray(
				"items"
			);
		}
	}

	private JSONObject _putTestrayBuilds(
			String authorization, JSONArray jsonArray)
		throws Exception {
		
		HttpPut httpPut = new HttpPut(_liferayURL + "/o/c/builds/batch");

		httpPut.setHeader("accept", "application/json");
		httpPut.setHeader("Authorization", authorization);
		httpPut.setHeader("Content-Type", "application/json");
		httpPut.setEntity(new StringEntity(jsonArray.toString()));

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build();
			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPut)) {

			return new JSONObject(
				EntityUtils.toString(
					closeableHttpResponse.getEntity(),
					Charset.defaultCharset()));
		}
	}

	private final OffsetDateTime _currentDateTime = OffsetDateTime.now(
		ZoneOffset.UTC);
	private final String _liferayOAuthClientId;
	private final String _liferayOAuthClientSecret;
	private final URL _liferayURL;

	private class HttpDeleteBatch extends HttpPost {

		public HttpDeleteBatch(String url) {
			super(url);
		}

		@Override
		public String getMethod() {
			return "DELETE";
		}

	}

}