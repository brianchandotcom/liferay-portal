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

package com.liferay.evp;

import java.util.Objects;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import org.json.JSONObject;
import java.time.Duration;

import reactor.util.retry.Retry;
import org.springframework.http.MediaType;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
@RequestMapping("/object/action/1")
@RestController
public class ObjectAction1RestController extends BaseRestController {

	public long evpRequestId;
	String token;

	@PostMapping
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject request = new JSONObject(json);

		JSONObject objectEntryDTOEVPRequest = request.getJSONObject("objectEntryDTOEVPRequest");

		JSONObject properties = objectEntryDTOEVPRequest.getJSONObject("properties");

		evpRequestId = objectEntryDTOEVPRequest.getLong("id");

		long organizationId = properties.getLong("r_organization_c_evpOrganizationId");
		token = jwt.getTokenValue();
		_get(organizationId, token);

		return new ResponseEntity<>(json, HttpStatus.OK);

	}

	private static final Log _log = LogFactory.getLog(
			ObjectAction1RestController.class);

	private JSONObject _get(long organizationId, String token) {

		return new JSONObject(
				Objects.requireNonNull(WebClient.create(
						_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain)
						.get()
						.uri("/o/c/evporganizations/" + organizationId)
						.header(
								"Authorization", "Bearer " + token)
						.retrieve().bodyToMono(
								String.class)
						.retryWhen(
								Retry.backoff(
										3, Duration.ofSeconds(1))
										.doAfterRetry(retrySignal -> _log.info("Retrying request")))
						.doOnNext(
								output -> {

									JSONObject organization = new JSONObject(output);

									JSONObject organizationStatus = organization.getJSONObject("organizationStatus");

									String statusKeyOrganization = organizationStatus.getString("key");

									JSONObject itemsStatus = new JSONObject();
									JSONObject requestStatus = new JSONObject();

									String path = "/o/c/evprequests/" + evpRequestId;
									if (statusKeyOrganization.equals("awaitingApprovalOnEvp")) {
										itemsStatus
												.put("key", "awaitOrganizationReview")
												.put("name", "Awaiting Organization Review");
										requestStatus.put("requestStatus", itemsStatus);

										_put(requestStatus.toString(), path);
									}
								})
						.subscribe()));
	}

	private void _put(String bodyValue, String path) {
		WebClient.create(
				_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain)
				.patch()
				.uri(uriBuilder -> uriBuilder.path(path).build())
				.accept(
						MediaType.APPLICATION_JSON)
				.contentType(
						MediaType.APPLICATION_JSON)
				.header(
						"Authorization", "Bearer " + token)
				.bodyValue(
						bodyValue)
				.retrieve().bodyToMono(
						Void.class)
				.subscribe();
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}