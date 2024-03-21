/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray;

import com.liferay.portal.kernel.util.StringUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * @author Nilton Vieira
 */
@Component
public class TestrayCommandLineRunner implements CommandLineRunner {

	public void autoArchiveTestrayBuilds() throws Exception {
		HttpResponse<String> httpResponse = _sendRequest(
			null, "GET",
			new URIBuilder(
				"/o/c/builds"
			).addParameter(
				"filter",
				"archived eq false and promoted eq false and dateCreated lt " +
					_currentDateTime.minusDays(_maxDaysOpened)
			).addParameter(
				"pageSize", "-1"
			).build());

		JSONArray testrayBuildsJSONArray = new JSONObject(
			httpResponse.body()
		).getJSONArray(
			"items"
		);

		if ((testrayBuildsJSONArray == null) ||
			testrayBuildsJSONArray.isEmpty()) {

			if (_log.isInfoEnabled()) {
				_log.info("No Testray builds found to archive");
			}

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

		if (_log.isInfoEnabled()) {
			_log.info("Archiving " + jsonArray.length() + " Testray builds");
		}

		_sendRequest(
			jsonArray.toString(), "PUT", URI.create("/o/c/builds/batch"));
	}

	public void deleteTestrayArchivedBuilds() throws Exception {
		HttpResponse<String> httpResponse = _sendRequest(
			null, "GET",
			new URIBuilder(
				"/o/c/builds"
			).addParameter(
				"fields", "id"
			).addParameter(
				"filter",
				"archived eq true and dateArchived lt " +
					_currentDateTime.minusDays(_maxDaysArchived)
			).addParameter(
				"pageSize", "-1"
			).build());

		JSONArray jsonArray = new JSONObject(
			httpResponse.body()
		).getJSONArray(
			"items"
		);

		if ((jsonArray == null) || jsonArray.isEmpty()) {
			if (_log.isInfoEnabled()) {
				_log.info("No Testray builds found to delete");
			}

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Deleting " + jsonArray.length() + " Testray builds");
		}

		_sendRequest(
			jsonArray.toString(), "DELETE", URI.create("/o/c/builds/batch"));
	}

	@Override
	public void run(String... args) throws Exception {
		deleteTestrayArchivedBuilds();
		autoArchiveTestrayBuilds();
	}

	private HttpResponse<String> _sendRequest(
			String body, String method, URI uri)
		throws Exception {

		HttpRequest.Builder httpRequest = HttpRequest.newBuilder(
		).uri(
			URIUtils.resolve(
				URI.create(_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain),
				uri)
		).headers(
			"accept", "application/json", "Authorization",
			"Bearer " + _oAuth2AccessToken.getTokenValue(), "Content-Type",
			"application/json"
		);

		if (!StringUtil.equals(method, "GET")) {
			httpRequest.method(
				method, HttpRequest.BodyPublishers.ofString(body));
		}

		HttpClient httpClient = HttpClient.newHttpClient();

		HttpResponse<String> httpResponse = httpClient.send(
			httpRequest.build(), HttpResponse.BodyHandlers.ofString());

		if (_log.isInfoEnabled()) {
			_log.info(httpResponse);
		}

		return httpResponse;
	}

	private static final Log _log = LogFactory.getLog(
		TestrayCommandLineRunner.class);

	private final OffsetDateTime _currentDateTime = OffsetDateTime.now(
		ZoneOffset.UTC
	).truncatedTo(
		ChronoUnit.SECONDS
	);

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Value("${liferay.testray.etc.cron.max.days.archived}")
	private Long _maxDaysArchived;

	@Value("${liferay.testray.etc.cron.max.days.opened}")
	private Long _maxDaysOpened;

	@Autowired
	private OAuth2AccessToken _oAuth2AccessToken;

}