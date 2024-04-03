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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

/**
 * @author Elias Santos
 */
@RequestMapping("/statusManagement")
@RestController
public class StatusManagementController {

	@GetMapping
	public void closeCompleteRequest() {
		JSONObject mdfRequestsJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/c/mdfrequests"
			).queryParam(
				"nestedFields", "mdfReqToMDFClms"
			).queryParam(
				"page", "1"
			).queryParam(
				"pageSize", "-1"
			).build());

		JSONArray mdfRequestsJSONArray = mdfRequestsJSONObject.getJSONArray(
			"items");

		for (int i = 0; i < mdfRequestsJSONArray.length(); i++) {
			try {
				JSONObject mdfRequestJSONObject =
					mdfRequestsJSONArray.getJSONObject(i);

				String mdfRequestStatus = mdfRequestJSONObject.getJSONObject(
					"mdfRequestStatus"
				).getString(
					"key"
				);

				Double mdfRequestAmount = mdfRequestJSONObject.getDouble(
					"totalMDFRequestAmount");

				if (mdfRequestStatus.equals("approved")) {
					JSONArray mdfClaims = mdfRequestJSONObject.getJSONArray(
						"mdfReqToMDFClms");

					Double claimPaidTotal = 0.0;

					for (int j = 0; j < mdfClaims.length(); j++) {
						JSONObject mdfClaimJSONObject = mdfClaims.getJSONObject(
							j);

						String mdfClaimStatus =
							mdfClaimJSONObject.getJSONObject(
								"mdfClaimStatus"
							).getString(
								"key"
							);
						Double mdfClaimPaid = mdfClaimJSONObject.getDouble(
							"claimPaid");

						claimPaidTotal += mdfClaimPaid;

						if (mdfClaimStatus.equals("claimPaid") &&
							(claimPaidTotal >= mdfRequestAmount)) {

							String mdfRequestExternalReferenceCode =
								mdfRequestJSONObject.getString(
									"externalReferenceCode");

							JSONObject newMdfRequestStatus = new JSONObject();

							newMdfRequestStatus.put("key", "completed");
							newMdfRequestStatus.put("name", "Completed");

							JSONObject mdfRequestStatusWrapper =
								new JSONObject();

							mdfRequestStatusWrapper.put(
								"mdfRequestStatus", newMdfRequestStatus);

							_patch(
								mdfRequestStatusWrapper.toString(),
								"/o/c/mdfrequests/by-external-reference-code/" +
									mdfRequestExternalReferenceCode);
						}
					}
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private JSONObject _get(Function<UriBuilder, URI> uriFunction) {
		String response = _getWebClient(
		).get(
		).uri(
			uriBuilder -> uriFunction.apply(uriBuilder)
		).accept(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
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

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-partner-etc-spring-boot-oauth-application-headless-" +
				"server");
	}

	private WebClient _getWebClient() {
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

	private void _patch(String bodyValue, String path) {
		_getWebClient(
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
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	private static final Log _log = LogFactory.getLog(QueueListener.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}