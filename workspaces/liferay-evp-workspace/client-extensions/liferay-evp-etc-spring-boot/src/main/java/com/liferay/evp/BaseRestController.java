/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.evp;

import java.net.URI;

import java.util.function.Function;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

/**
 * @author Elvison Victor
 */
public abstract class BaseRestController {

	protected JSONObject get(Jwt jwt, Function<UriBuilder, URI> uriFunction) {
		return new JSONObject(
			getWebClient(
			).get(
			).uri(
				uriBuilder -> uriFunction.apply(uriBuilder)
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
			).retrieve(
			).bodyToMono(
				String.class
			).block());
	}

	protected WebClient getWebClient() {
		return WebClient.builder(
		).baseUrl(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain
		).exchangeStrategies(
			ExchangeStrategies.builder(
			).codecs(
				clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs(
				).maxInMemorySize(
					5 * 1024 * 1024
				)
			).build()
		).build();
	}

	protected void put(
		Object bodyValue, Jwt jwt, Function<UriBuilder, URI> uriFunction) {

		getWebClient(
		).put(
		).uri(
			uriBuilder -> uriFunction.apply(uriBuilder)
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).bodyValue(
			bodyValue.toString()
		).retrieve(
		).bodyToMono(
			Void.class
		).block();
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	protected String lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	protected String lxcDXPServerProtocol;

}