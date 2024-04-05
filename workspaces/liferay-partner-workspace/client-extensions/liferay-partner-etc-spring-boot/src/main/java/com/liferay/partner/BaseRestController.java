/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;

import java.net.URI;

import java.time.Duration;

import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

/**
 * @author Elias Santos
 */
public abstract class BaseRestController {

	protected void delete(String path) {
		getWebClient(
		).delete(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).accept(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, getAuthorization()
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	protected JSONObject get(Function<UriBuilder, URI> uriFunction) {
		String response = getWebClient(
		).get(
		).uri(
			uriBuilder -> uriFunction.apply(uriBuilder)
		).accept(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, getAuthorization()
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		try {
			return new JSONObject(response);
		}
		catch (JSONException jsonException) {
			_log.error("Unable to create JSON object for: " + response);

			throw jsonException;
		}
	}

	protected String getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-partner-etc-spring-boot-oauth-application-headless-" +
				"server");
	}

	protected WebClient getWebClient() {
		return WebClient.builder(
		).clientConnector(
			new ReactorClientHttpConnector(
				HttpClient.create(
					ConnectionProvider.builder(
						"fixed"
					).evictInBackground(
						Duration.ofSeconds(120)
					).maxConnections(
						500
					).maxIdleTime(
						Duration.ofSeconds(20)
					).maxLifeTime(
						Duration.ofSeconds(60)
					).pendingAcquireTimeout(
						Duration.ofSeconds(60)
					).build()
				).followRedirect(
					true
				))
		).baseUrl(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).exchangeStrategies(
			ExchangeStrategies.builder(
			).codecs(
				clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs(
				).maxInMemorySize(
					16 * 1024 * 1024
				)
			).build()
		).build();
	}

	protected void patch(String bodyValue, String path) {
		getWebClient(
		).patch(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, getAuthorization()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	protected void post(String bodyValue, String path) {
		getWebClient(
		).post(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, getAuthorization()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	protected JSONObject put(String bodyValue, String path) {
		return new JSONObject(
			getWebClient(
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
				HttpHeaders.AUTHORIZATION, getAuthorization()
			).bodyValue(
				bodyValue
			).retrieve(
			).bodyToMono(
				String.class
			).block());
	}

	private static final Log _log = LogFactory.getLog(QueueListener.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}