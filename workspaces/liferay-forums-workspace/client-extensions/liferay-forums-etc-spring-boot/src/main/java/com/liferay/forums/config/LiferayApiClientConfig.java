/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.forums.config;

import com.liferay.forums.client.LiferayApiClient;

import java.time.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Roselaine Marques
 * @author Neil Griffin
 */
@Configuration
public class LiferayApiClientConfig {

	@Bean
	public LiferayApiClient liferayApiClient(
		@Value("${liferay.api.base.url}") String baseUrl,
		@Value("${liferay.headless.api.user:}") String user,
		@Value("${liferay.headless.api.password:}") String password,
		@Value("${forums.api.request.timeout.seconds:15}") int
			requestTimeoutSeconds) {

		if (_log.isInfoEnabled()) {
			_log.info("Creating LiferayApiClient targeting: " + baseUrl);
		}

		return new LiferayApiClient(
			baseUrl, user, password, Duration.ofSeconds(requestTimeoutSeconds));
	}

	private static final Log _log = LogFactory.getLog(
		LiferayApiClientConfig.class);

}