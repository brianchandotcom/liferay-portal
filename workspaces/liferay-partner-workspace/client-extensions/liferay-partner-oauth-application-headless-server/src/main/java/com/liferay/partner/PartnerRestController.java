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
		System.out.println("oie");

		if (_log.isInfoEnabled()) {
			_log.info("JWT Claims: " + jwt.getClaims());
			_log.info("JWT ID: " + jwt.getId());
			_log.info("JWT Subject: " + jwt.getSubject());
		}

		// _getObjectEntries("opportunity", jwt);
		// _createObjectEntry("opportunity", "1", jwt);
		// _updateObjectEntry("opportunity", 55943, "00", jwt);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void _createObjectEntry(String objectName, String type, Jwt jwt) {
		try {
			WebClient.Builder builder = WebClient.builder();

			WebClient webClient = builder.baseUrl(
					_liferayPortalURL).defaultHeader(
							HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
					.defaultHeader(
							HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.build();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", type);

			webClient.post().uri(
					"/o/c/" + objectName + "/").bodyValue(
							jsonObject)
					.header(
							HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue())
					.exchangeToMono(
							clientResponse -> {
								HttpStatus httpStatus = clientResponse.statusCode();

								if (httpStatus.is2xxSuccessful()) {
									return clientResponse.bodyToMono(String.class);
								} else if (httpStatus.is4xxClientError()) {
									return Mono.just(httpStatus.getReasonPhrase());
								}

								Mono<WebClientResponseException> mono = clientResponse.createException();

								return mono.flatMap(Mono::error);
							})
					.doOnNext(
							output -> {
								if (_log.isInfoEnabled()) {
									_log.info("Output: " + output);
								}
							})
					.subscribe();
		} catch (Exception exception) {
			_log.error("ERROR");
		}
	}

	private void _getObjectEntries(String objectName, Jwt jwt) {
		WebClient webClient = WebClient.builder().baseUrl(
				"https://".concat("dxp.lfr.dev")).defaultHeader(
						HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(
						HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();

		webClient.get().uri(
				"/o/c/" + objectName + "/").header(
						HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue())
				.exchangeToMono(
						r -> {
							if (r.statusCode().equals(
									HttpStatus.OK)) {

								return r.bodyToMono(String.class);
							} else if (r.statusCode().is4xxClientError()) {

								return Mono.just("Error response");
							}

							return r.createException().flatMap(
									Mono::error);
						})
				.doOnNext(
						System.out::println)
				.subscribe();
	}

	private void _updateObjectEntry(
			String objectName, int objectEntryId, String type, Jwt jwt) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", type);

		WebClient webClient = WebClient.builder().baseUrl(
				"https://".concat("dxp.lfr.dev")).defaultHeader(
						HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(
						HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();

		webClient.patch().uri(
				StringBundler.concat("/o/c/", objectName, "/", objectEntryId)).header(
						HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue())
				.bodyValue(
						jsonObject)
				.exchangeToMono(
						r -> {
							if (r.statusCode().equals(
									HttpStatus.OK)) {

								return r.bodyToMono(String.class);
							} else if (r.statusCode().is4xxClientError()) {

								return Mono.just("Error response");
							}

							return r.createException().flatMap(
									Mono::error);
						})
				.doOnNext(
						System.out::println)
				.subscribe();
	}

	private static final Log _log = LogFactory.getLog(
			PartnerRestController.class);

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;
}