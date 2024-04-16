/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;

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
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

/**
 * @author Nilton Vieira
 */
@Component
public class TestrayCommandLineRunner implements CommandLineRunner {

	public void archiveTestrayBuilds() throws Exception {
		JSONArray jsonArray = _get(
			uriBuilder -> uriBuilder.path(
				"/o/c/builds"
			).queryParam(
				"filter",
				"archived eq false and promoted eq false and dateCreated lt " +
					_currentDateTime.minusDays(_maxDaysOpened)
			).queryParam(
				"nestedFields", "buildToTasks"
			).queryParam(
				"pageSize", "-1"
			).build()
		).getJSONArray(
			"items"
		);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (!jsonObject.getJSONArray(
					"buildToTasks"
				).isEmpty()) {

				continue;
			}

			jsonObject.put(
				"archived", true
			).put(
				"dateArchived", _currentDateTime
			);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Archiving " + jsonArray.length() + " Testray builds");
		}

		_put(jsonArray.toString(), "/o/c/builds/batch");
	}

	public void deleteTestrayBuilds() throws Exception {
		JSONArray jsonArray = _get(
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

		if (_log.isInfoEnabled()) {
			_log.info("Deleting " + jsonArray.length() + " Testray builds");
		}

		_delete(jsonArray.toString(), "/o/c/builds/batch");
	}

	@Override
	public void run(String... args) throws Exception {
		deleteTestrayBuilds();

		archiveTestrayBuilds();
	}

	private void _delete(String bodyValue, String path) {
		_getWebClient(
		).method(
			HttpMethod.DELETE
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).contentType(
			MediaType.APPLICATION_JSON
		).accept(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	private JSONObject _get(Function<UriBuilder, URI> uriFunction) {
		return new JSONObject(
			_getWebClient(
			).get(
			).uri(
				uriBuilder -> uriFunction.apply(uriBuilder)
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, _getAuthorization()
			).retrieve(
			).bodyToMono(
				String.class
			).block());
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			_liferayOAuthApplicationExternalReferenceCodes);
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

	private void _put(String bodyValue, String path) {
		_getWebClient(
		).put(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			Void.class
		).block();
	}

	private static final Log _log = LogFactory.getLog(
		TestrayCommandLineRunner.class);

	private final OffsetDateTime _currentDateTime = OffsetDateTime.now(
		ZoneOffset.UTC
	).truncatedTo(
		ChronoUnit.SECONDS
	);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Value("${liferay.testray.etc.cron.max.days.archived}")
	private Long _maxDaysArchived;

	@Value("${liferay.testray.etc.cron.max.days.opened}")
	private Long _maxDaysOpened;

}