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

package com.liferay.delectable.bonsai;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
@RequestMapping("/workflow/action/applicationreview")
@RestController
public class WorkflowActionApplicationReviewRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		String transition = "auto-approve";

		JSONObject payload = new JSONObject(json);

		JSONObject entryDTO = payload.getJSONObject("entryDTO");

		JSONObject applicationState = entryDTO.getJSONObject("applicationState");

		String applicationStateKey = applicationState.getString("key");

		if (Objects.equals("approved", applicationStateKey) || Objects.equals("denied", applicationStateKey)) {
			transition = "review";
		}

		try {
			WebClient.Builder builder = WebClient.builder();

			WebClient webClient = builder.baseUrl(
				lxcDXPServerProtocol + "://" + lxcDXPMainDomain
			).defaultHeader(
				HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
			).defaultHeader(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
			).build();

			webClient.post(
			).uri(
				payload.getString("transitionURL")
			).bodyValue(
				"{\"transitionName\": \"" + transition + "\"}"
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
		WorkflowActionApplicationReviewRestController.class);

}