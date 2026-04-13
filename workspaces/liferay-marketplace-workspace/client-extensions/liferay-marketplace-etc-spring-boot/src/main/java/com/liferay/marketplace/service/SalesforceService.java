/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.marketplace.model.SalesforceOpportunity;
import com.liferay.petra.string.StringBundler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Keven Leone
 */
@Component
public class SalesforceService extends BaseService {

	public JSONObject postSalesforceOpportunity(
			SalesforceOpportunity salesforceOpportunity)
		throws Exception {

		try {
			String response = post(
				_getAuthorization(), salesforceOpportunity.toString(),
				UriComponentsBuilder.fromUriString(
					_gcfBaseUrl
				).path(
					"/marketplace-api/v1/opportunities"
				).build(
				).toUri());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Created Salesforce opportunity " + salesforceOpportunity);
			}

			return new JSONObject(response);
		}
		catch (WebClientResponseException webClientResponseException) {
			_log.error(
				StringBundler.concat(
					"Unable to create Salesforce opportunity ",
					salesforceOpportunity, "\n",
					webClientResponseException.getResponseBodyAsString()));

			return null;
		}
	}

	private String _getAuthorization() throws Exception {
		if (_accessToken != null) {
			Date expirationTime = _accessToken.getExpirationTime();

			if (System.currentTimeMillis() < expirationTime.getTime()) {
				return _authorization;
			}
		}

		try (InputStream inputStream = new ByteArrayInputStream(
				_gcfServiceAccountKey.getBytes())) {

			IdTokenCredentials idTokenCredential =
				IdTokenCredentials.newBuilder(
				).setIdTokenProvider(
					(IdTokenProvider)GoogleCredentials.fromStream(inputStream)
				).setTargetAudience(
					_gcfAudience
				).build();

			AccessToken accessToken = idTokenCredential.refreshAccessToken();

			if (accessToken == null) {
				throw new Exception("Unable to get access token");
			}

			_accessToken = accessToken;

			_authorization = "Bearer " + accessToken.getTokenValue();

			return _authorization;
		}
	}

	private static final Log _log = LogFactory.getLog(SalesforceService.class);

	private AccessToken _accessToken;
	private String _authorization;

	@Value("${liferay.marketplace.salesforce.gcf.audience}")
	private String _gcfAudience;

	@Value("${liferay.marketplace.salesforce.gcf.base.url}")
	private String _gcfBaseUrl;

	@Value("${liferay.marketplace.salesforce.gcf.service.account.key}")
	private String _gcfServiceAccountKey;

}