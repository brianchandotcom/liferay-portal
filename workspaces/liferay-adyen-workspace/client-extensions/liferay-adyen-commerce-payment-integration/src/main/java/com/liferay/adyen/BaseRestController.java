/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adyen;

import org.apache.commons.logging.Log;

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
 * @author Crescenzo Rega
 */
public abstract class BaseRestController {

	protected WebClient getWebClient() {
		return WebClient.builder(
		).baseUrl(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).build();
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

	protected void post(
		String authorization, String body, Log log, String path) {

		if (log.isDebugEnabled()) {
			log.debug("Call POST: " + path);
		}

		getWebClient(
		).post(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).bodyValue(
			body
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).retrieve(
		).bodyToMono(
			String.class
		).subscribe(
			successResponse -> {
				if (log.isDebugEnabled()) {
					log.debug("Successful Response: " + successResponse);
				}
			},
			error -> {
				throw new RuntimeException(error);
			}
		);
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	protected String lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	protected String lxcDXPServerProtocol;

}