/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.forums.client;

import com.liferay.petra.string.StringBundler;

import java.time.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.DefaultUriBuilderFactory;

import reactor.core.publisher.Mono;

/**
 * @author Roselaine Marques
 * @author Neil Griffin
 */
public class LiferayApiClient {

	public LiferayApiClient(
		String baseUrl, String user, String password, Duration requestTimeout) {

		_baseUrl = baseUrl;
		_user = user;
		_password = password;
		_requestTimeout = requestTimeout;

		DefaultUriBuilderFactory uriBuilderFactory =
			new DefaultUriBuilderFactory(baseUrl);

		uriBuilderFactory.setEncodingMode(
			DefaultUriBuilderFactory.EncodingMode.NONE);

		_webClient = WebClient.builder(
		).baseUrl(
			baseUrl
		).uriBuilderFactory(
			uriBuilderFactory
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).build();
	}

	public Mono<Void> deleteAsync(String path, String authToken) {
		return _delete(
			path, authToken
		).onErrorResume(
			WebClientResponseException.class,
			webClientResponseException -> {
				if (!_staleToken(webClientResponseException, authToken)) {
					return Mono.error(webClientResponseException);
				}

				if (_log.isWarnEnabled()) {
					_log.warn(
						"DELETE " + path +
							" → 401; retrying without the bearer token");
				}

				return _delete(path, null);
			}
		);
	}

	public String get(String path, String authToken) {
		if (_log.isDebugEnabled()) {
			_log.debug("GET " + path);
		}

		try {
			return _get(path, authToken);
		}
		catch (WebClientResponseException webClientResponseException) {
			if (_staleToken(webClientResponseException, authToken)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"GET " + path +
							" → 401; retrying without the bearer token");
				}

				return _get(path, null);
			}

			int statusCode = webClientResponseException.getStatusCode(
			).value();

			if (statusCode == 404) {
				if (_log.isDebugEnabled()) {
					_log.debug("GET " + path + " → 404 NOT_FOUND");
				}
			}
			else {
				_log.error(
					StringBundler.concat(
						"GET ", path, " failed: ",
						webClientResponseException.getStatusCode(), " ",
						webClientResponseException.getResponseBodyAsString()));
			}

			throw webClientResponseException;
		}
	}

	public String getBaseUrl() {
		return _baseUrl;
	}

	public String patch(String path, String authToken, Object jsonBody) {
		if (_log.isDebugEnabled()) {
			_log.debug("PATCH " + path);
		}

		try {
			return _patch(
				path, authToken, jsonBody
			).block(
				_requestTimeout
			);
		}
		catch (WebClientResponseException webClientResponseException) {
			if (_staleToken(webClientResponseException, authToken)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"PATCH " + path +
							" \u2192 401; retrying without the bearer token");
				}

				return _patch(
					path, null, jsonBody
				).block(
					_requestTimeout
				);
			}

			_log.error(
				StringBundler.concat(
					"PATCH ", path, " failed: ",
					webClientResponseException.getStatusCode(), " ",
					webClientResponseException.getResponseBodyAsString()));

			throw webClientResponseException;
		}
	}

	public String post(String path, String authToken, Object jsonBody) {
		if (_log.isDebugEnabled()) {
			_log.debug("POST " + path);
		}

		try {
			return _post(
				path, authToken, jsonBody
			).block(
				_requestTimeout
			);
		}
		catch (WebClientResponseException webClientResponseException) {
			if (_staleToken(webClientResponseException, authToken)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"POST " + path +
							" → 401; retrying without the bearer token");
				}

				return _post(
					path, null, jsonBody
				).block(
					_requestTimeout
				);
			}

			_log.error(
				StringBundler.concat(
					"POST ", path, " failed: ",
					webClientResponseException.getStatusCode(), " ",
					webClientResponseException.getResponseBodyAsString()));

			throw webClientResponseException;
		}
	}

	public Mono<String> postAsync(
		String path, String authToken, Object jsonBody) {

		return _post(
			path, authToken, jsonBody
		).onErrorResume(
			WebClientResponseException.class,
			webClientResponseException -> {
				if (!_staleToken(webClientResponseException, authToken)) {
					return Mono.error(webClientResponseException);
				}

				if (_log.isWarnEnabled()) {
					_log.warn(
						"POST " + path +
							" → 401; retrying without the bearer token");
				}

				return _post(path, null, jsonBody);
			}
		);
	}

	private Mono<Void> _delete(String path, String authToken) {
		return _webClient.delete(
		).uri(
			path
		).headers(
			h -> _setAuthHeader(h, authToken)
		).retrieve(
		).bodyToMono(
			Void.class
		);
	}

	private String _get(String path, String authToken) {
		return _webClient.get(
		).uri(
			path
		).headers(
			h -> _setAuthHeader(h, authToken)
		).retrieve(
		).bodyToMono(
			String.class
		).block(
			_requestTimeout
		);
	}

	private Mono<String> _patch(
		String path, String authToken, Object jsonBody) {

		return _webClient.patch(
		).uri(
			path
		).headers(
			h -> _setAuthHeader(h, authToken)
		).bodyValue(
			jsonBody
		).retrieve(
		).bodyToMono(
			String.class
		);
	}

	private Mono<String> _post(String path, String authToken, Object jsonBody) {
		return _webClient.post(
		).uri(
			path
		).headers(
			h -> _setAuthHeader(h, authToken)
		).bodyValue(
			jsonBody
		).retrieve(
		).bodyToMono(
			String.class
		);
	}

	private void _setAuthHeader(HttpHeaders headers, String authToken) {
		if ((authToken != null) && !authToken.isBlank()) {
			headers.setBearerAuth(authToken);
		}
		else if ((_user != null) && !_user.isBlank() && (_password != null) &&
				 !_password.isBlank()) {

			headers.setBasicAuth(_user, _password);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No authentication credentials were provided for request");
			}
		}
	}

	private boolean _staleToken(
		WebClientResponseException webClientResponseException,
		String authToken) {

		int statusCode = webClientResponseException.getStatusCode(
		).value();

		if (statusCode != 401) {
			return false;
		}

		if ((authToken != null) && !authToken.isBlank()) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactory.getLog(LiferayApiClient.class);

	private final String _baseUrl;
	private final String _password;
	private final Duration _requestTimeout;
	private final String _user;
	private final WebClient _webClient;

}