/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import java.util.Collections;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;

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
			System.getenv("LIFERAY_TESTRAY_ETC_CRON_LIFERAY_URL"));

		String oAuthAuthorization = main.getOAuthAuthorization();

		main.deleteTestrayArchivedBuilds(oAuthAuthorization);
		main.autoArchiveTestrayBuilds(oAuthAuthorization);
	}

	public Main(
			String liferayOAuthClientId, String liferayOAuthClientSecret,
			String liferayURL)
		throws Exception {

		_liferayOAuthClientId = liferayOAuthClientId;
		_liferayOAuthClientSecret = liferayOAuthClientSecret;
		_liferayURL = liferayURL;
	}

	public void autoArchiveTestrayBuilds(String oAuthAuthorization)
		throws Exception {

		JSONArray testrayBuildsJSONArray = _getTestrayBuildsJSONArray(
			oAuthAuthorization,
			"archived eq false and promoted eq false and dateCreated lt " +
				_currentDateTime.minusDays(60));

		if ((testrayBuildsJSONArray == null) ||
			testrayBuildsJSONArray.isEmpty()) {

			return;
		}

		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < testrayBuildsJSONArray.length(); i++) {
			JSONObject jsonObject = (JSONObject)testrayBuildsJSONArray.get(i);

			jsonArray.put(
				jsonObject.put(
					"archived", true
				).put(
					"dateArchived", _currentDateTime
				));
		}

		_sendRequest(
			oAuthAuthorization, jsonArray.toString(), "application/json", "PUT",
			URI.create(_liferayURL + "/o/c/builds/batch"));
	}

	public void deleteTestrayArchivedBuilds(String oAuthAuthorization)
		throws Exception {

		JSONArray testrayBuildsJSONArray = _getTestrayBuildsJSONArray(
			oAuthAuthorization,
			"archived eq true and dateArchived lt " +
				_currentDateTime.minusDays(30));

		if ((testrayBuildsJSONArray == null) ||
			testrayBuildsJSONArray.isEmpty()) {

			return;
		}

		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < testrayBuildsJSONArray.length(); i++) {
			JSONObject jsonObject = (JSONObject)testrayBuildsJSONArray.get(i);

			jsonArray.put(
				Collections.singletonMap("id", jsonObject.getLong("id")));
		}

		_sendRequest(
			oAuthAuthorization, jsonArray.toString(), "application/json",
			"DELETE", URI.create(_liferayURL + "/o/c/builds/batch"));
	}

	public String getOAuthAuthorization() throws Exception {
		String urlEncoded = "";

		for (Map.Entry<String, String> entry :
				HashMapBuilder.put(
					"client_id", _liferayOAuthClientId
				).put(
					"client_secret", _liferayOAuthClientSecret
				).put(
					"grant_type", "client_credentials"
				).build(
				).entrySet()) {

			if (Validator.isNotNull(urlEncoded)) {
				urlEncoded += "&";
			}

			urlEncoded +=
				URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) +
					"=" +
						URLEncoder.encode(
							entry.getValue(), StandardCharsets.UTF_8);
		}

		HttpResponse<String> httpResponse = _sendRequest(
			null, urlEncoded, "application/x-www-form-urlencoded", "POST",
			URI.create(_liferayURL + "/o/oauth2/token"));

		if (httpResponse.statusCode() == 200) {
			JSONObject jsonObject = new JSONObject(httpResponse.body());

			return jsonObject.getString("token_type") + " " +
				jsonObject.getString("access_token");
		}

		throw new Exception("Unable to get OAuth authorization");
	}

	private JSONArray _getTestrayBuildsJSONArray(
			String authorization, String filterString)
		throws Exception {

		HttpResponse<String> httpResponse = _sendRequest(
			authorization, null, "application/json", "GET",
			new URIBuilder(
				_liferayURL + "/o/c/builds"
			).addParameter(
				"filter", filterString
			).addParameter(
				"pageSize", "-1"
			).build());

		return new JSONObject(
			httpResponse.body()
		).getJSONArray(
			"items"
		);
	}

	private HttpResponse<String> _sendRequest(
			String authorization, String body, String contentType,
			String method, URI uri)
		throws Exception {

		HttpRequest.Builder httpRequest = HttpRequest.newBuilder(
		).uri(
			uri
		).header(
			"accept", "application/json"
		);

		if (authorization != null) {
			httpRequest.header("Authorization", authorization);
		}

		if (contentType != null) {
			httpRequest.header("Content-Type", contentType);
		}

		if (!StringUtil.equals(method, "GET")) {
			httpRequest.method(
				method, HttpRequest.BodyPublishers.ofString(body));
		}

		HttpClient httpClient = HttpClient.newHttpClient();

		return httpClient.send(
			httpRequest.build(), HttpResponse.BodyHandlers.ofString());
	}

	private final OffsetDateTime _currentDateTime = OffsetDateTime.now(
		ZoneOffset.UTC);
	private final String _liferayOAuthClientId;
	private final String _liferayOAuthClientSecret;
	private final String _liferayURL;

}