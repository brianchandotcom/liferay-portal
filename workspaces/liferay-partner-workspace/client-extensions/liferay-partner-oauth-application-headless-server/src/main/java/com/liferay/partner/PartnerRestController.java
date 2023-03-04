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

package com.liferay.partner;

import com.liferay.petra.string.StringBundler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

/**
 * @author Jair Medeiros
 * @author Thaynam Lázaro
 * @author Raymond Augé
 */
@RestController
public class PartnerRestController {

	@GetMapping("/")
	public ResponseEntity<String> trigger(@AuthenticationPrincipal Jwt jwt) {
		if (_log.isInfoEnabled()) {
			_log.info("JWT Claims: " + jwt.getClaims());
			_log.info("JWT ID: " + jwt.getId());
			_log.info("JWT Subject: " + jwt.getSubject());
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "Testing 4444");

		_addOrUpdateObjectEntry(
			"opportunities", "testing1", jsonObject.toString(), jwt);

		_getObjectEntries("opportunities", jwt);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void _addOrUpdateObjectEntry(
		String objectDefinitionName, String externalReferenceCode,
		String bodyValue, Jwt jwt) {

		try {
			WebClient.Builder builder = WebClient.builder();

			WebClient webClient = builder.baseUrl(
				_liferayPortalURL
			).defaultHeader(
				HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
			).defaultHeader(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
			).build();

			webClient.put(
			).uri(
				StringBundler.concat(
					"/o/c/", objectDefinitionName,
					"/by-external-reference-code/", externalReferenceCode)
			).bodyValue(
				bodyValue
			).header(
				HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
			).exchangeToMono(
				clientResponse -> {
					HttpStatus httpStatus = clientResponse.statusCode();

					if (httpStatus.is2xxSuccessful()) {
						return clientResponse.bodyToMono(String.class);
					}
					else if (httpStatus.is4xxClientError()) {
						return Mono.just(httpStatus.getReasonPhrase());
					}

					Mono<WebClientResponseException> mono =
						clientResponse.createException();

					return mono.flatMap(Mono::error);
				}
			).doOnNext(
				output -> {
					if (_log.isInfoEnabled()) {
						_log.info("Output: " + output);
					}
				}
			).subscribe();
		}
		catch (WebClientResponseException webClientResponseException) {
			_log.error("ERROR" + webClientResponseException);
		}
	}

	private void _getObjectEntries(String objectDefinitionName, Jwt jwt) {
		try {
			WebClient.Builder builder = WebClient.builder();

			WebClient webClient = builder.baseUrl(
				_liferayPortalURL
			).defaultHeader(
				HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
			).build();

			webClient.get(
			).uri(
				"/o/c/" + objectDefinitionName + "/"
			).header(
				HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
			).exchangeToMono(
				clientResponse -> {
					HttpStatus httpStatus = clientResponse.statusCode();

					if (httpStatus.is2xxSuccessful()) {
						return clientResponse.bodyToMono(String.class);
					}
					else if (httpStatus.is4xxClientError()) {
						return Mono.just(httpStatus.getReasonPhrase());
					}

					Mono<WebClientResponseException> mono =
						clientResponse.createException();

					return mono.flatMap(Mono::error);
				}
			).doOnNext(
				output -> {
					if (_log.isInfoEnabled()) {
						_log.info("Output: " + output);
					}
				}
			).subscribe();
		}
		catch (WebClientResponseException webClientResponseException) {
			_log.error("ERROR" + webClientResponseException);
		}
	}

	private static final Log _log = LogFactory.getLog(
		PartnerRestController.class);

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

}