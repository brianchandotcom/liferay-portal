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

package com.liferay.sample;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
@RestController
public class SampleRestController {

	@GetMapping("/dad/joke")
	public ResponseEntity<String> getDadJoke(@AuthenticationPrincipal Jwt jwt) {
		if (_log.isInfoEnabled()) {
			_log.info("JWT Claims: " + jwt.getClaims());
			_log.info("JWT ID: " + jwt.getId());
			_log.info("JWT Subject: " + jwt.getSubject());
		}

		String dadJoke = WebClient.create(
		).get(
		).uri(
			"https://icanhazdadjoke.com"
		).accept(
			MediaType.TEXT_PLAIN
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		if (_log.isInfoEnabled()) {
			_log.info("Dad joke: " + dadJoke);
		}

		return new ResponseEntity<>(dadJoke, HttpStatus.OK);
	}

	@PostMapping("/sample/notification/type/1")
	public ResponseEntity<String> postSampleNotificationType(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (_log.isInfoEnabled()) {
			_log.info("JWT Claims: " + jwt.getClaims());
			_log.info("JWT ID: " + jwt.getId());
			_log.info("JWT Subject: " + jwt.getSubject());

			try {
				JSONObject jsonObject = new JSONObject(json);

				_log.info("\n\n" + jsonObject.toString(4) + "\n");
			}
			catch (Exception exception) {
				_log.error("JSON: " + json, exception);
			}
		}

		return new ResponseEntity<>(json, HttpStatus.CREATED);
	}

	@PostMapping("/sample/object/action/1")
	public ResponseEntity<String> postSampleObjectAction1(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (_log.isInfoEnabled()) {
			_log.info("JWT Claims: " + jwt.getClaims());
			_log.info("JWT ID: " + jwt.getId());
			_log.info("JWT Subject: " + jwt.getSubject());

			try {
				JSONObject jsonObject = new JSONObject(json);

				_log.info("\n\n" + jsonObject.toString(4) + "\n");
			}
			catch (Exception exception) {
				_log.error("JSON: " + json, exception);
			}
		}

		return new ResponseEntity<>(json, HttpStatus.CREATED);
	}

	@PostMapping("/sample/object/action/2")
	public ResponseEntity<String> postSampleObjectAction2(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (_log.isInfoEnabled()) {
			_log.info("JWT Claims: " + jwt.getClaims());
			_log.info("JWT ID: " + jwt.getId());
			_log.info("JWT Subject: " + jwt.getSubject());

			try {
				JSONObject jsonObject = new JSONObject(json);

				_log.info("\n\n" + jsonObject.toString(4) + "\n");
			}
			catch (Exception exception) {
				_log.error("JSON: " + json, exception);
			}
		}

		return new ResponseEntity<>(json, HttpStatus.CREATED);
	}

	@PostMapping("/sample/workflow/action/1")
	public ResponseEntity<String> postSampleWorkflowAction1(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (_log.isInfoEnabled()) {
			_log.info("JWT Claims: " + jwt.getClaims());
			_log.info("JWT ID: " + jwt.getId());
			_log.info("JWT Subject: " + jwt.getSubject());
		}

		try {
			WebClient.Builder builder = WebClient.builder();

			WebClient webClient = builder.baseUrl(
				_serverProtocol + "://" + _mainDomain
			).defaultHeader(
				HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
			).defaultHeader(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
			).build();

			JSONObject jsonObject = new JSONObject(json);

			webClient.post(
			).uri(
				jsonObject.getString("transitionURL")
			).bodyValue(
				"{transitionName: \"approve\"}"
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
		catch (Exception exception) {
			_log.error("JSON: " + json, exception);

			return new ResponseEntity<>(json, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		SampleRestController.class);

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _mainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _serverProtocol;

}