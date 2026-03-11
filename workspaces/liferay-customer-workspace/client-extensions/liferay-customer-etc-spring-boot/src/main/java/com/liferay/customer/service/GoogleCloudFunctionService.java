/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;
import com.google.common.io.CharStreams;

import com.liferay.petra.string.StringBundler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Felipe Franca
 * @author Amos Fong
 */
@Component
public class GoogleCloudFunctionService {

	@Cacheable("accountUsage")
	public JSONObject fetchCustomerAccountUsage(String accountKey)
		throws Exception {

		return _handleRequest(
			accountKey, _gcfCustomerServiceAccountKey,
			_FUNCTION_CUSTOMER_USAGE_API_PATH,
			StringBundler.concat(
				_gcfBaseURL, _FUNCTION_CUSTOMER_USAGE_API_PATH,
				"/api/v1/customer/usage/accounts/", accountKey));
	}

	@Cacheable("accountUsage")
	public JSONObject fetchCustomerAccountUsage(String accountKey, String month)
		throws Exception {

		return _handleRequest(
			accountKey, _gcfComposableServiceAccountKey,
			_FUNCTION_COMPOSABLE_USAGE_API_PATH,
			StringBundler.concat(
				_gcfBaseURL, _FUNCTION_COMPOSABLE_USAGE_API_PATH,
				"/api/v1/accounts/", accountKey, "/usage/month/", month));
	}

	@CacheEvict(allEntries = true, value = "accountUsage")
	@Scheduled(cron = "0 0 * * * *")
	public void scheduledCacheEviction() throws Exception {
	}

	private JSONObject _handleRequest(
			String accountKey, String serviceAccountKey, String targetAudience,
			String url)
		throws Exception {

		try (InputStream inputStream = new ByteArrayInputStream(
				serviceAccountKey.getBytes())) {

			IdTokenCredentials idTokenCredential =
				IdTokenCredentials.newBuilder(
				).setIdTokenProvider(
					(IdTokenProvider)GoogleCredentials.fromStream(inputStream)
				).setTargetAudience(
					_gcfBaseURL + targetAudience
				).build();

			HttpRequest httpRequest = new NetHttpTransport(
			).createRequestFactory(
				new HttpCredentialsAdapter(idTokenCredential)
			).buildGetRequest(
				new GenericUrl(url)
			).setThrowExceptionOnExecuteError(
				false
			);

			HttpResponse httpResponse = null;

			try {
				httpResponse = httpRequest.execute();

				if (httpResponse.isSuccessStatusCode()) {
					String result = CharStreams.toString(
						new InputStreamReader(
							httpResponse.getContent(), StandardCharsets.UTF_8));

					return new JSONObject(result);
				}

				if (httpResponse.getStatusCode() ==
						HttpStatus.NOT_FOUND.value()) {

					return null;
				}

				throw new Exception(
					StringBundler.concat(
						httpResponse.getStatusCode(), " ",
						httpResponse.getStatusMessage(), " for account ",
						accountKey));
			}
			finally {
				if (httpResponse != null) {
					httpResponse.disconnect();
				}
			}
		}
	}

	private static final String _FUNCTION_COMPOSABLE_USAGE_API_PATH =
		"/composable_usage_api";

	private static final String _FUNCTION_CUSTOMER_USAGE_API_PATH =
		"/customer_usage_api";

	@Value("${liferay.customer.gcf.base.url}")
	private String _gcfBaseURL;

	@Value("${liferay.customer.gcf.composable.service.account.key}")
	private String _gcfComposableServiceAccountKey;

	@Value("${liferay.customer.gcf.customer.service.account.key}")
	private String _gcfCustomerServiceAccountKey;

}