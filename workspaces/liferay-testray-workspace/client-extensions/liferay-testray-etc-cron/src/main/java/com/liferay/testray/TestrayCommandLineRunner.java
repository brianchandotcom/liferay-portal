/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * @author Nilton Vieira
 */
@Component
public class TestrayCommandLineRunner
	extends BaseRestController implements CommandLineRunner {

	public void archiveTestrayBuilds() throws Exception {
		JSONArray jsonArray = new JSONObject(
			get(
				_getAuthorization(),
				_defaultUriBuilderFactory.builder(
				).path(
					"/o/c/builds"
				).queryParam(
					"filter",
					"archived eq false and promoted eq false and (not (" +
						"buildToTasks/id ne '0')) and dateCreated lt " +
							_currentDateTime.minusDays(_maxDaysOpened)
				).queryParam(
					"pageSize", "-1"
				).build(
				).toString())
		).getJSONArray(
			"items"
		);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			jsonObject.put(
				"archived", true
			).put(
				"dateArchived", _currentDateTime
			);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Archiving " + jsonArray.length() + " Testray builds");
		}

		put(_getAuthorization(), jsonArray.toString(), "/o/c/builds/batch");
	}

	public void deleteTestrayBuilds() throws Exception {
		JSONArray jsonArray = new JSONObject(
			get(
				_getAuthorization(),
				_defaultUriBuilderFactory.builder(
				).path(
					"/o/c/builds"
				).queryParam(
					"fields", "id"
				).queryParam(
					"filter",
					"archived eq true and dateArchived lt " +
						_currentDateTime.minusDays(_maxDaysArchived)
				).queryParam(
					"pageSize", "-1"
				).build(
				).toString())
		).getJSONArray(
			"items"
		);

		if (_log.isInfoEnabled()) {
			_log.info("Deleting " + jsonArray.length() + " Testray builds");
		}

		delete(_getAuthorization(), jsonArray.toString(), "/o/c/builds/batch");
	}

	@Override
	public void run(String... args) throws Exception {
		deleteTestrayBuilds();

		archiveTestrayBuilds();
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			_liferayOAuthApplicationExternalReferenceCodes);
	}

	private static final Log _log = LogFactory.getLog(
		TestrayCommandLineRunner.class);

	private final OffsetDateTime _currentDateTime = OffsetDateTime.now(
		ZoneOffset.UTC
	).truncatedTo(
		ChronoUnit.SECONDS
	);
	private final DefaultUriBuilderFactory _defaultUriBuilderFactory =
		new DefaultUriBuilderFactory();

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;

	@Value("${liferay.testray.etc.cron.max.days.archived}")
	private Long _maxDaysArchived;

	@Value("${liferay.testray.etc.cron.max.days.opened}")
	private Long _maxDaysOpened;

}