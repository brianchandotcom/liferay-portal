/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.partner;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * @author Jair Medeiros
 */
@Component
public class PartnerCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {

		OAuth2AccessToken oAuth2AccessToken = LiferayOAuth2Util.getOAuth2AccessToken(
				_authorizedClientServiceOAuth2AuthorizedClientManager,
				_liferayOAuthApplicationExternalReferenceCodes);

		if (_log.isInfoEnabled()) {
			_log.info("Issued: " + oAuth2AccessToken.getIssuedAt());
			_log.info("Expires At: " + oAuth2AccessToken.getExpiresAt());
			_log.info("Scopes: " + oAuth2AccessToken.getScopes());
			_log.info("Token: " + oAuth2AccessToken.getTokenValue());
		}
	}

	private static final Log _log = LogFactory.getLog(
			PartnerCommandLineRunner.class);

	@Autowired
	private AuthorizedClientServiceOAuth2AuthorizedClientManager _authorizedClientServiceOAuth2AuthorizedClientManager;

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;
}