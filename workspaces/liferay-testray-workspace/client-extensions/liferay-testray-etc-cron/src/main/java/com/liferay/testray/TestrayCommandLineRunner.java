/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray;

import java.net.URI;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

/**
 * @author Nilton Vieira
 */
@Component
public class TestrayCommandLineRunner implements CommandLineRunner {

	public void autoArchiveTestrayBuilds() throws Exception {
		JSONArray testrayBuildsJSONArray = _sendRequest(
			"", HttpMethod.GET,
			uriBuilder -> uriBuilder.path(
				"/o/c/builds"
			).queryParam(
				"filter",
				"archived eq false and promoted eq false and dateCreated lt " +
					_currentDateTime.minusDays(_maxDaysOpened)
			).queryParam(
				"pageSize", "-1"
			).build()
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
			JSONObject testrayBuildsJSONObject =
				(JSONObject)testrayBuildsJSONArray.get(i);

			jsonArray.put(
				testrayBuildsJSONObject.put(
					"archived", true
				).put(
					"dateArchived", _currentDateTime
				));
		}

		if (_log.isInfoEnabled()) {
			_log.info("Archiving " + jsonArray.length() + " Testray builds");
		}

		_sendRequest(
			jsonArray.toString(), HttpMethod.PUT,
			uriBuilder -> uriBuilder.path(
				"/o/c/builds/batch"
			).build());
	}

	public void deleteTestrayArchivedBuilds() throws Exception {
		JSONArray jsonArray = _sendRequest(
			"", HttpMethod.GET,
			uriBuilder -> uriBuilder.path(
				"/o/c/builds"
			).queryParam(
				"fields", "id"
			).queryParam(
				"filter",
				"archived eq true and dateArchived lt " +
					_currentDateTime.minusDays(_maxDaysArchived)
			).queryParam(
				"pageSize", "-1"
			).build()
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
			jsonArray.toString(), HttpMethod.DELETE,
			uriBuilder -> uriBuilder.path(
				"/o/c/builds/batch"
			).build());
	}

	@Override
	public void run(String... args) throws Exception {
		deleteTestrayArchivedBuilds();
		autoArchiveTestrayBuilds();
	}

	private WebClient _getWebClient() {
		return WebClient.builder(
		).baseUrl(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).exchangeStrategies(
			ExchangeStrategies.builder(
			).codecs(
				clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs(
				).maxInMemorySize(
					5 * 1024 * 1024
				)
			).build()
		).build();
	}

	private JSONObject _sendRequest(
		String bodyValue, HttpMethod httpMethod,
		Function<UriBuilder, URI> uriFunction) {

		return new JSONObject(
			_getWebClient(
			).method(
				httpMethod
			).uri(
				uriBuilder -> uriFunction.apply(uriBuilder)
			).contentType(
				MediaType.APPLICATION_JSON
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION,
				"Bearer " + _oAuth2AccessToken.getTokenValue()
			).bodyValue(
				bodyValue
			).retrieve(
			).bodyToMono(
				String.class
			).block());
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