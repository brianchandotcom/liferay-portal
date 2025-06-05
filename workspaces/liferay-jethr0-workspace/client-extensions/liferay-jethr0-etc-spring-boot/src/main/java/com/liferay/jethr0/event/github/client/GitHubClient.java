/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.client;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.petra.function.RetryableUnsafeSupplier;
import com.liferay.petra.function.UnsafeSupplier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitHubClient extends BaseRestController {

	public String requestGet(URL url) {
		String urlString = url.toString();

		if (urlString.startsWith("https://raw.githubusercontent.com")) {
			try {
				StringBuilder sb = new StringBuilder();

				String line = null;

				URLConnection urlConnection = url.openConnection();

				urlConnection.setRequestProperty(
					"Accept", MediaType.APPLICATION_JSON_VALUE);
				urlConnection.setRequestProperty(
					"Authorization", _getAuthorization());

				InputStream inputStream = urlConnection.getInputStream();

				BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));

				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
					sb.append("\n");
				}

				try {
					return sb.toString();
				}
				finally {
					bufferedReader.close();
					inputStream.close();
				}
			}
			catch (IOException ioException) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioException);
				}
			}
		}

		UnsafeSupplier<String, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to post to ", url, ". Retry attempt ",
								retryCount, " of ", maxRetries));
					}
				},
				() -> {
					String response = get(_getAuthorization(), _toUri(url));

					if (response == null) {
						throw new RuntimeException(
							"Unable to get authorization");
					}

					return response;
				});

		return unsafeSupplier.get();
	}

	public String requestPatch(URL url, JSONObject requestJSONObject) {
		UnsafeSupplier<String, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to post to ", url, ". Retry attempt ",
								retryCount, " of ", maxRetries));
					}
				},
				() -> {
					String response = patch(
						_getAuthorization(), requestJSONObject.toString(),
						_toUri(url));

					if (response == null) {
						throw new RuntimeException("No response");
					}

					return response;
				});

		return unsafeSupplier.get();
	}

	public String requestPost(URL url, JSONObject requestJSONObject) {
		UnsafeSupplier<String, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to post to ", url, ". Retry attempt ",
								retryCount, " of ", maxRetries));
					}
				},
				() -> {
					String response = post(
						_getAuthorization(), requestJSONObject.toString(),
						_toUri(url));

					if (response == null) {
						throw new RuntimeException("No response");
					}

					return response;
				});

		return unsafeSupplier.get();
	}

	public String requestPut(URL url, JSONObject requestJSONObject) {
		UnsafeSupplier<String, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to post to ", url, ". Retry attempt ",
								retryCount, " of ", maxRetries));
					}
				},
				() -> {
					String response = put(
						_getAuthorization(), requestJSONObject.toString(),
						_toUri(url));

					if (response == null) {
						throw new RuntimeException("No response");
					}

					return response;
				});

		return unsafeSupplier.get();
	}

	private String _getAuthorization() {
		return StringUtil.combine("token ", _gitHubToken);
	}

	private URI _toUri(URL url) {
		String urlString = url.toString();

		return UriComponentsBuilder.fromUriString(
			urlString.replaceAll("https://api\\.github\\.com", _gitHubProxyURL)
		).build(
		).toUri();
	}

	private static final Log _log = LogFactory.getLog(GitHubClient.class);

	@Value("${JETHR0_GITHUB_PROXY_URL:https://api.github.com}")
	private String _gitHubProxyURL;

	@Value("${JETHR0_GITHUB_TOKEN:github-token}")
	private String _gitHubToken;

}