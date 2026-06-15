/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;

import java.net.URL;

import java.time.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.util.retry.Retry;

/**
 * @author Keven Leone
 */
@Component
public class AIHubService extends BaseService {

	public JSONObject provision(JSONObject jsonObject) {
		try {
			String response = post(
				_liferayOAuth2AccessTokenManager.getAuthorization(
					"external-ai-hub"),
				jsonObject.toString(),
				UriComponentsBuilder.fromUriString(
					_externalAIHubHomePageURL.toString()
				).path(
					"/o/ai-hub/v1.0/provisioning"
				).build(
				).toUri());

			if (_log.isInfoEnabled()) {
				_log.info("AI Hub provisioned " + jsonObject);
			}

			return new JSONObject(response);
		}
		catch (WebClientResponseException webClientResponseException) {
			_log.error(
				"Unable to provision AI Hub" +
					webClientResponseException.getResponseBodyAsString());

			return null;
		}
	}

	public void purchaseQuotaPrepaidBlock(
		int accountId, JSONObject jsonObject) {

		post(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"external-ai-hub"),
			jsonObject.toString(),
			UriComponentsBuilder.fromUriString(
				_externalAIHubHomePageURL.toString()
			).path(
				"/o/ai-hub-pricing/v1.0/accounts/" + accountId +
					"/quota-blocks/purchase"
			).build(
			).toUri());

		if (_log.isInfoEnabled()) {
			_log.info("AI Hub prepaid block  " + jsonObject);
		}
	}

	@Override
	protected ExchangeFilterFunction getWebClientExchangeFilterFunction() {
		return (clientRequest, exchangeFunction) -> exchangeFunction.exchange(
			clientRequest
		).retryWhen(
			Retry.fixedDelay(
				3, Duration.ofSeconds(5)
			).doBeforeRetry(
				retrySignal -> {
					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Retrying ", clientRequest.url(),
								retrySignal.totalRetries() + 1));
					}
				}
			)
		);
	}

	private static final Log _log = LogFactory.getLog(AIHubService.class);

	@Value("${external.ai.hub.oauth2.headless.server.home.page.url}")
	private URL _externalAIHubHomePageURL;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}