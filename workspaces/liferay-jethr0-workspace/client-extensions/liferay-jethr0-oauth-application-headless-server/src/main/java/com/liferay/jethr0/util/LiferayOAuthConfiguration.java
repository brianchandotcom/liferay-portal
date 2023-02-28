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

import com.liferay.petra.http.invoker.HttpInvoker;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class LiferayOAuthConfiguration {

	public String getAuthorization() {
		synchronized (_liferayPortalURL) {
			if (_authorization != null) {
				return _authorization;
			}

			while (true) {
				try {
					HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

					httpInvoker.header(
						"Content-Type", "application/x-www-form-urlencoded");

					httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

					httpInvoker.parameter("client_id", getClientID());
					httpInvoker.parameter(
						"client_secret", _liferayOAuthApplicationSecret);
					httpInvoker.parameter("grant_type", "client_credentials");

					httpInvoker.path(_liferayPortalURL + "/o/oauth2/token");

					HttpInvoker.HttpResponse httpResponse =
						httpInvoker.invoke();

					JSONObject jsonObject = new JSONObject(
						httpResponse.getContent());

					_authorization =
						jsonObject.getString("token_type") + " " +
							jsonObject.getString("access_token");

					return _authorization;
				}
				catch (Throwable throwable) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get Authorization: " +
								throwable.getMessage());
					}

					ThreadUtil.sleep(1000);
				}
			}
		}
	}

	public String getClientID() {
		synchronized (_liferayPortalURL) {
			if (_clientID != null) {
				return _clientID;
			}

			while (true) {
				try {
					HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

					httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
					httpInvoker.parameter(
						"externalReferenceCode",
						_liferayOAuthApplicationExternalReferenceCode);
					httpInvoker.path(
						_liferayPortalURL + "/o/oauth2/application");

					HttpInvoker.HttpResponse httpResponse =
						httpInvoker.invoke();

					JSONObject responseJSONObject = new JSONObject(
						httpResponse.getContent());

					_clientID = responseJSONObject.getString("client_id");

					return _clientID;
				}
				catch (Throwable throwable) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get Client ID: " +
								throwable.getMessage());
					}

					ThreadUtil.sleep(1000);
				}
			}
		}
	}

	public URL getJWKSetURL() {
		try {
			return new URL(_liferayPortalURL + "/o/oauth2/jwks");
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
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