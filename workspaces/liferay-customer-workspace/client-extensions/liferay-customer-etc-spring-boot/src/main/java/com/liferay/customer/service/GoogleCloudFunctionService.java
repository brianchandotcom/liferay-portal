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
import org.springframework.stereotype.Component;

/**
 * @author Felipe Franca
 * @author Amos Fong
 */
@Component
public class GoogleCloudFunctionService {

	public JSONObject getCustomerAccountUsage(String accountKey)
		throws Exception {

		try (InputStream inputStream = new ByteArrayInputStream(
				_gcfServiceAccountKey.getBytes())) {

			IdTokenCredentials idTokenCredential =
				IdTokenCredentials.newBuilder(
				).setIdTokenProvider(
					(IdTokenProvider)GoogleCredentials.fromStream(inputStream)
				).setTargetAudience(
					_gcfBaseUrl + _FUNCTION_CUSTOMER_USAGE_API_PATH
				).build();

			HttpRequest httpRequest = new NetHttpTransport(
			).createRequestFactory(
				new HttpCredentialsAdapter(idTokenCredential)
			).buildGetRequest(
				new GenericUrl(
					StringBundler.concat(
						_gcfBaseUrl, _FUNCTION_CUSTOMER_USAGE_API_PATH,
						"/api/v1/customer/usage/accounts/", accountKey))
			);

			HttpResponse httpResponse = httpRequest.execute();

			try {
				String result = CharStreams.toString(
					new InputStreamReader(
						httpResponse.getContent(), StandardCharsets.UTF_8));

				return new JSONObject(result);
			}
			finally {
				httpResponse.disconnect();
			}
		}
	}

	private static final String _FUNCTION_CUSTOMER_USAGE_API_PATH =
		"/customer_usage_api";

	@Value("${liferay.customer.gcf.base.url}")
	private String _gcfBaseUrl;

	@Value("${liferay.customer.gcf.service.account.key}")
	private String _gcfServiceAccountKey;

}