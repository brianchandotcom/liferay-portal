/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.scanner;

import com.liferay.petra.string.StringBundler;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;

import java.time.Duration;

import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Kiana Suetani
 */
public class PageSpeedScoreProvider {

	public PageSpeedScoreProvider(
		HttpClient httpClient, String pageSpeedAPIKey, String strategy) {

		_httpClient = httpClient;
		_pageSpeedAPIKey = pageSpeedAPIKey;

		if (Objects.equals(strategy, "DESKTOP") ||
			Objects.equals(strategy, "MOBILE")) {

			_strategy = strategy;
		}
		else {
			_strategy = "DESKTOP";
		}
	}

	public PageSpeedScores getScores(String url)
		throws PageSpeedScoreProviderException {

		try {
			return _getScores(url);
		}
		catch (InterruptedException interruptedException) {
			Thread thread = Thread.currentThread();

			thread.interrupt();

			throw new PageSpeedScoreProviderException(
				"Unable to get PageSpeed scores for " + url,
				interruptedException);
		}
		catch (PageSpeedScoreProviderException
					pageSpeedScoreProviderException) {

			throw pageSpeedScoreProviderException;
		}
		catch (Exception exception) {
			throw new PageSpeedScoreProviderException(
				"Unable to get PageSpeed scores for " + url, exception);
		}
	}

	public static class PageSpeedScoreProviderException extends Exception {

		public PageSpeedScoreProviderException(String message) {
			super(message);
		}

		public PageSpeedScoreProviderException(
			String message, Throwable throwable) {

			super(message, throwable);
		}

	}

	private String _buildGooglePageSpeedURL(String url) {
		StringBundler sb = new StringBundler(11);

		sb.append("https://content-pagespeedonline.googleapis.com");
		sb.append("/pagespeedonline/v5/runPagespeed");
		sb.append("?category=ACCESSIBILITY&category=BEST_PRACTICES");
		sb.append("&category=PERFORMANCE&category=SEO&fields=");
		sb.append(
			URLEncoder.encode(
				"lighthouseResult/categories/*/score", StandardCharsets.UTF_8));
		sb.append("&key=");
		sb.append(URLEncoder.encode(_pageSpeedAPIKey, StandardCharsets.UTF_8));
		sb.append("&strategy=");
		sb.append(URLEncoder.encode(_strategy, StandardCharsets.UTF_8));
		sb.append("&url=");
		sb.append(URLEncoder.encode(url, StandardCharsets.UTF_8));

		return sb.toString();
	}

	private int _getScore(JSONObject categoriesJSONObject, String category) {
		JSONObject categoryJSONObject = categoriesJSONObject.optJSONObject(
			category);

		if (categoryJSONObject == null) {
			return 0;
		}

		double score = categoryJSONObject.optDouble("score", 0);

		return (int)Math.round(score * 100);
	}

	private PageSpeedScores _getScores(String url)
		throws InterruptedException, PageSpeedScoreProviderException {

		if (_pageSpeedAPIKey == null) {
			throw new PageSpeedScoreProviderException("Invalid Connection");
		}

		HttpResponse<String> httpResponse;

		try {
			HttpRequest httpRequest = HttpRequest.newBuilder(
			).GET(
			).timeout(
				Duration.ofSeconds(120)
			).uri(
				URI.create(_buildGooglePageSpeedURL(url))
			).build();

			httpResponse = _httpClient.send(
				httpRequest, HttpResponse.BodyHandlers.ofString());
		}
		catch (IOException ioException) {
			throw new PageSpeedScoreProviderException(
				"Unable to reach Google PageSpeed API for " + url, ioException);
		}

		int statusCode = httpResponse.statusCode();

		if (statusCode != HttpURLConnection.HTTP_OK) {
			throw new PageSpeedScoreProviderException(
				"Response code " + statusCode);
		}

		return _parseScores(httpResponse.body());
	}

	private PageSpeedScores _parseScores(String responseJSON)
		throws PageSpeedScoreProviderException {

		JSONObject responseJSONObject = new JSONObject(responseJSON);

		JSONObject lighthouseResultJSONObject =
			responseJSONObject.optJSONObject("lighthouseResult");

		if (lighthouseResultJSONObject == null) {
			throw new PageSpeedScoreProviderException(
				"Missing \"lighthouseResult\" in response");
		}

		JSONObject categoriesJSONObject =
			lighthouseResultJSONObject.optJSONObject("categories");

		if (categoriesJSONObject == null) {
			throw new PageSpeedScoreProviderException(
				"Missing \"categories\" in \"lighthouseResult\"");
		}

		return new PageSpeedScores(
			_getScore(categoriesJSONObject, "accessibility"),
			_getScore(categoriesJSONObject, "best-practices"),
			_getScore(categoriesJSONObject, "performance"),
			_getScore(categoriesJSONObject, "seo"));
	}

	private final HttpClient _httpClient;
	private final String _pageSpeedAPIKey;
	private final String _strategy;

}