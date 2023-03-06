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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class LiferayOAuthConfiguration {

	public String getAuthorization() {
		synchronized (_log) {
			if (_authorization != null) {
				return _authorization;
			}

			while (true) {
				try {
					String response = WebClient.create(
						_liferayPortalURL + "/o/oauth2/token"
					).post(
					).uri(
						uriBuilder -> uriBuilder.queryParam(
							"client_id", getClientID()
						).queryParam(
							"client_secret", _liferayOAuthApplicationSecret
						).queryParam(
							"grant_type", "client_credentials"
						).build()
					).contentType(
						MediaType.APPLICATION_FORM_URLENCODED
					).retrieve(
					).bodyToMono(
						String.class
					).block();

					if (response == null) {
						throw new RuntimeException("No response");
					}

					JSONObject responseJSONObject = new JSONObject(response);

					_authorization = StringUtil.combine(
						responseJSONObject.getString("token_type"), " ",
						responseJSONObject.getString("access_token"));

					return _authorization;
				}
				catch (Throwable throwable) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to get Authorization: ",
								throwable.getMessage()));
					}

					ThreadUtil.sleep(1000);
				}
			}
		}
	}

	public String getClientID() {
		synchronized (_log) {
			if (_clientID != null) {
				return _clientID;
			}

			while (true) {
				try {
					String response = WebClient.create(
						_liferayPortalURL + "/o/oauth2/application"
					).get(
					).uri(
						uriBuilder -> uriBuilder.queryParam(
							"externalReferenceCode",
							_liferayOAuthApplicationExternalReferenceCode
						).build()
					).retrieve(
					).bodyToMono(
						String.class
					).block();

					if (response == null) {
						throw new RuntimeException("No response");
					}

					JSONObject responseJSONObject = new JSONObject(response);

					_clientID = responseJSONObject.getString("client_id");

					return _clientID;
				}
				catch (Throwable throwable) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to get Client ID: ",
								throwable.getMessage()));
					}

					ThreadUtil.sleep(1000);
				}
			}
		}
	}

	private static final Log _log = LogFactory.getLog(
		LiferayOAuthConfiguration.class);

	private String _authorization;
	private String _clientID;

	@Value("${liferay.oauth.application.external.reference.code}")
	private String _liferayOAuthApplicationExternalReferenceCode;

	@Value("${liferay.oauth.application.secret}")
	private String _liferayOAuthApplicationSecret;

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

}