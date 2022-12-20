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

 package com.liferay.sample.minimal.workspace.easy;

import java.util.function.BiFunction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * @author Raymond Augé
 */
@Component
public class EasyWorkflowHelper {

	public EasyWorkflowHelper(
		@Value("${liferay.portal.url}") String _portalURL) {

		_webClient = WebClient.builder().baseUrl(
			_portalURL
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).build();
	}

	/**
	 * Use the transitionFunction function to calculate a transition from the
	 * JSONObject which contains the workflow details and the JSONArray which
	 * contains the next transition names.
	 *
	 * @param json               the JSON payload of the request which contains
	 *                           the workflow details
	 * @param jwtToken           the JWT token sent by the DXP server
	 * @param transitionFunction the user supplied function to calculate the
	 *                           transition
	 * @throws JSONException if the JSON payload could not be parsed
	 */
	public void transitionTheTask(
			String json, String jwtToken,
			BiFunction<JSONObject, JSONArray, String> transitionFunction)
		throws JSONException {

		JSONObject jsonObject = new JSONObject(json);

		if (_log.isInfoEnabled()) {
			_log.info("\n\n" + jsonObject.toString(4) + "\n");
		}

		JSONArray nextTransitionNamesJSONArray = jsonObject.getJSONArray(
			"nextTransitionNames");

		if (_log.isInfoEnabled()) {
			_log.info("Available transitions: " + nextTransitionNamesJSONArray);
		}

		String transitionName = transitionFunction.apply(
			jsonObject, nextTransitionNamesJSONArray);

		_webClient.post().uri(
			jsonObject.getString("transitionURL")
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken
		).bodyValue(
			new JSONObject().put("transitionName", transitionName).toString()
		).exchangeToMono(
			r -> {
				HttpStatus httpStatus = r.statusCode();

				if (httpStatus.is2xxSuccessful()) {
					return r.bodyToMono(String.class);
				}
				else if (httpStatus.is4xxClientError()) {
					return Mono.just(httpStatus.getReasonPhrase());
				}
				else {
					return r.createException().flatMap(Mono::error);
				}
			}
		).doOnNext(
			output -> {
				if (_log.isInfoEnabled()) {
					_log.info("Transition Output: " + output);
				}
			}
		).subscribe();
	}

	private static final Log _log = LogFactory.getLog(
		EasyRestController.class);

	private final WebClient _webClient;

}