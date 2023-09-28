/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.evp;

import java.time.Duration;

import java.util.Objects;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.util.retry.Retry;

/**
 * @author Elvison Victor
 */
public abstract class BaseRestController {

	protected JSONObject get(Consumer<String> consumer, Jwt jwt, String path) {
		return new JSONObject(
			Objects.requireNonNull(
				WebClient.create(
					lxcDXPServerProtocol + "://" + lxcDXPMainDomain
				).get(
				).uri(
					uriBuilder -> uriBuilder.path(
						path
					).build()
				).header(
					"Authorization", "Bearer " + jwt.getTokenValue()
				).retrieve(
				).bodyToMono(
					String.class
				).retryWhen(
					Retry.backoff(
						3, Duration.ofSeconds(1)
					).doAfterRetry(
						retrySignal -> _log.info("Retrying request")
					)
				).doOnNext(
					response -> consumer.accept(response)
				).subscribe()));
	}

	protected JSONObject put(Object bodyValue, Jwt jwt, String path) {
		return new JSONObject(
			Objects.requireNonNull(
				WebClient.create(
					lxcDXPServerProtocol + "://" + lxcDXPMainDomain
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
					"Authorization", "Bearer " + jwt.getTokenValue()
				).bodyValue(
					bodyValue.toString()
				).retrieve(
				).bodyToMono(
					Void.class
				).subscribe()));
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	protected String lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	protected String lxcDXPServerProtocol;

	private static final Log _log = LogFactory.getLog(
		BaseRestController.class);

}