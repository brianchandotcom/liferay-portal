/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Gregory Amerson
 */
@Component
public class SampleCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		String dadJoke = WebClient.create(
		).get(
		).uri(
			_liferaySampleEtcSpringBootURL + "/dad/joke"
		).header(
			"Authorization", "Bearer " + _oAuth2AccessToken.getTokenValue()
		).accept(
			MediaType.TEXT_PLAIN
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		if (_log.isInfoEnabled()) {
			_log.info("Dad joke: " + dadJoke);
		}
	}

	private static final Log _log = LogFactory.getLog(
		SampleCommandLineRunner.class);

	@Value("${liferay.sample.etc.spring.boot.url}")
	private String _liferaySampleEtcSpringBootURL;

	@Autowired
	private OAuth2AccessToken _oAuth2AccessToken;

}