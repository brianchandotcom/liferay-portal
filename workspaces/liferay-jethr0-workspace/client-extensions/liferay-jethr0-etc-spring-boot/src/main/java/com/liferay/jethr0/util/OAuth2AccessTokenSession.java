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

package com.liferay.jethr0.util;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2Util;

import java.time.Instant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class OAuth2AccessTokenSession {

	public String getAuthorization() {
		OAuth2AccessToken oAuth2AccessToken = _getOAuth2AccessToken();

		return "Bearer " + oAuth2AccessToken.getTokenValue();
	}

	private OAuth2AccessToken _getOAuth2AccessToken() {
		synchronized (_log) {
			if (_oAuth2AccessToken == null) {
				_oAuth2AccessToken = LiferayOAuth2Util.getOAuth2AccessToken(
					_authorizedClientServiceOAuth2AuthorizedClientManager,
					_liferayOAuthApplicationExternalReferenceCodes);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Updated OAuth2 Access Token at " +
							_oAuth2AccessToken.getIssuedAt());
				}

				return _oAuth2AccessToken;
			}

			Instant instant = Instant.now();

			Instant expiresAtInstant = _oAuth2AccessToken.getExpiresAt();

			if ((expiresAtInstant == null) ||
				expiresAtInstant.isBefore(instant.minusSeconds(60))) {

				_oAuth2AccessToken = LiferayOAuth2Util.getOAuth2AccessToken(
					_authorizedClientServiceOAuth2AuthorizedClientManager,
					_liferayOAuthApplicationExternalReferenceCodes);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Updated OAuth2 Access Token at " +
							_oAuth2AccessToken.getIssuedAt());
				}
			}

			return _oAuth2AccessToken;
		}
	}

	private static final Log _log = LogFactory.getLog(
		OAuth2AccessTokenSession.class);

	@Autowired
	private AuthorizedClientServiceOAuth2AuthorizedClientManager
		_authorizedClientServiceOAuth2AuthorizedClientManager;

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;

	private OAuth2AccessToken _oAuth2AccessToken;

}