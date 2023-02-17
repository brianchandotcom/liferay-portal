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

package com.liferay.jethr0.server;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseServer implements Server {

	@Override
	public String httpRequest(
		String uriPath, Map<String, String> headers, Method method,
		Map<String, String> parameters, String requestData) {

		Mono<String> stringMono = httpRequestMono(
			uriPath, headers, method, parameters, requestData);

		return stringMono.block();
	}

	@Override
	public Mono<String> httpRequestMono(
		String uriPath, Map<String, String> headers, Method method,
		Map<String, String> parameters, String requestData) {

		WebClient.Builder webClientBuilder = WebClient.builder();

		if (headers != null) {
			webClientBuilder.defaultHeaders(
				httpHeaders -> {
					for (Map.Entry<String, String> header :
							headers.entrySet()) {

						httpHeaders.add(header.getKey(), header.getValue());
					}
				});
		}

		WebClient webClient = webClientBuilder.build();

		WebClient.RequestBodyUriSpec requestBodyUriSpec = webClient.method(
			HttpMethod.valueOf(method.toString()));

		StringBuilder sb = new StringBuilder();

		sb.append(getURL());

		if (uriPath != null) {
			if (!uriPath.startsWith("/")) {
				sb.append("/");
			}

			sb.append(uriPath);
		}

		if ((parameters != null) && !parameters.isEmpty()) {
			sb.append("?");

			for (Map.Entry<String, String> parameter : parameters.entrySet()) {
				try {
					sb.append(URLEncoder.encode(parameter.getKey(), "UTF-8"));
					sb.append("=");
					sb.append(URLEncoder.encode(parameter.getValue(), "UTF-8"));
					sb.append("&");
				}
				catch (UnsupportedEncodingException
							unsupportedEncodingException) {

					throw new RuntimeException(unsupportedEncodingException);
				}
			}

			sb.setLength(sb.length() - 1);
		}

		WebClient.RequestBodySpec requestBodySpec = requestBodyUriSpec.uri(
			sb.toString());

		if (requestData != null) {
			requestBodySpec.body(BodyInserters.fromValue(requestData));
		}

		WebClient.ResponseSpec responseSpec = requestBodySpec.retrieve();

		return responseSpec.bodyToMono(String.class);
	}

}