/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.paypal;

import com.liferay.client.extension.util.spring.boot.BaseRestController;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.tomcat.util.codec.binary.Base64;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
public class PayPalBaseRestController extends BaseRestController {

	protected String getAuthorization(
		String clientId, String clientSecret, String mode) {

		String authorization = clientId + ":" + clientSecret;

		JSONObject authorizationRequestJSONObject = new JSONObject(
			WebClient.create(
				getEnvironmentURL(mode)
			).post(
			).uri(
				"/v1/oauth2/token"
			).accept(
				MediaType.APPLICATION_JSON
			).contentType(
				MediaType.APPLICATION_FORM_URLENCODED
			).header(
				HttpHeaders.AUTHORIZATION,
				"Basic " + Base64.encodeBase64String(authorization.getBytes())
			).bodyValue(
				"grant_type=client_credentials"
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		return authorizationRequestJSONObject.getString("access_token");
	}

	protected String getEnvironmentURL(String mode) {
		if (mode.equals("live")) {
			return "https://api-m.paypal.com";
		}

		return "https://api-m.sandbox.paypal.com";
	}

	protected void log(Jwt jwt, Log log) {
		if (log.isInfoEnabled()) {
			log.info("JWT Claims: " + jwt.getClaims());
			log.info("JWT ID: " + jwt.getId());
			log.info("JWT Subject: " + jwt.getSubject());
		}
	}

	protected void log(Jwt jwt, Log log, Map<String, String> parameters) {
		if (log.isInfoEnabled()) {
			log.info("JWT Claims: " + jwt.getClaims());
			log.info("JWT ID: " + jwt.getId());
			log.info("JWT Subject: " + jwt.getSubject());
			log.info("Parameters: " + parameters);
		}
	}

	protected void log(Jwt jwt, Log log, String json) {
		if (log.isInfoEnabled()) {
			try {
				JSONObject jsonObject = new JSONObject(json);

				log.info("JSON: " + jsonObject.toString(4));
			}
			catch (Exception exception) {
				log.error("JSON: " + json, exception);
			}

			log.info("JWT Claims: " + jwt.getClaims());
			log.info("JWT ID: " + jwt.getId());
			log.info("JWT Subject: " + jwt.getSubject());
		}
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	protected String lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	protected String lxcDXPServerProtocol;

}