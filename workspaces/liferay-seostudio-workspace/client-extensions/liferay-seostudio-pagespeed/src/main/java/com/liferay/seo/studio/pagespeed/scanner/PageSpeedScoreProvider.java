/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.scanner;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kiana Suetani
 */
public class PageSpeedScoreProvider {

	public PageSpeedScoreProvider(
		HttpClient httpClient, String pageSpeedAPIKey, String strategy) {

		_httpClient = httpClient;
		_pageSpeedAPIKey = pageSpeedAPIKey;
		_strategy = strategy;
	}

	public PageSpeedScores getScores(String url)
		throws PageSpeedScoreProviderException {

		try {
			return _getScores(url);
		}
		catch (Exception exception) {
			if (exception instanceof PageSpeedScoreProviderException) {
				throw (PageSpeedScoreProviderException)exception;
			}

			if (exception instanceof InterruptedException) {
				Thread.currentThread(
				).interrupt();
			}

			throw new PageSpeedScoreProviderException(
				"Unable to get PageSpeed scores for " + url, exception);
		}
	}

	public boolean isValidConnection() {
		return Validator.isNotNull(_pageSpeedAPIKey);
	}

	public static class PageSpeedScoreProviderException
		extends PortalException {

		public PageSpeedScoreProviderException(Exception exception) {
			super(exception);

			_googlePageSpeedErrorJSONObject = null;
		}

		public PageSpeedScoreProviderException(
			JSONObject googlePageSpeedErrorJSONObject, String message) {

			super(message);

			_googlePageSpeedErrorJSONObject = googlePageSpeedErrorJSONObject;
		}

		public PageSpeedScoreProviderException(String message) {
			super(message);

			_googlePageSpeedErrorJSONObject = null;
		}

		public PageSpeedScoreProviderException(
			String message, Throwable throwable) {

			super(message, throwable);

			_googlePageSpeedErrorJSONObject = null;
		}

		public JSONObject getGooglePageSpeedErrorJSONObject() {
			return _googlePageSpeedErrorJSONObject;
		}

		public boolean isQuotaExceeded() {
			if (_googlePageSpeedErrorJSONObject == null) {
				return false;
			}

			JSONObject errorDetailJSONObject =
				_googlePageSpeedErrorJSONObject.optJSONObject("error");

			if (errorDetailJSONObject == null) {
				return false;
			}

			if (errorDetailJSONObject.optInt("code", -1) == 429) {
				return true;
			}

			return false;
		}

		private final JSONObject _googlePageSpeedErrorJSONObject;

	}

	private String _buildGooglePageSpeedURL(String url) {
		try {
			String encodedFields = URLEncoder.encode(
				"lighthouseResult/categories/*/score", "UTF-8");
			String encodedKey = URLEncoder.encode(_pageSpeedAPIKey, "UTF-8");
			String encodedStrategy = URLEncoder.encode(_strategy, "UTF-8");
			String encodedURL = URLEncoder.encode(url, "UTF-8");

			return StringBundler.concat(
				"https://content-pagespeedonline.googleapis.com",
				"/pagespeedonline/v5/runPagespeed",
				"?category=ACCESSIBILITY&category=BEST_PRACTICES",
				"&category=PERFORMANCE&category=SEO&fields=", encodedFields,
				"&key=", encodedKey, "&strategy=", encodedStrategy, "&url=",
				encodedURL);
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
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

	private PageSpeedScores _getScores(String url) throws Exception {
		if (!isValidConnection()) {
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
		catch (Exception exception) {
			if (exception instanceof InterruptedException) {
				Thread.currentThread(
				).interrupt();
			}

			throw new PageSpeedScoreProviderException(
				"Unable to reach Google PageSpeed API for " + url, exception);
		}

		int responseCode = httpResponse.statusCode();

		if (responseCode != HttpURLConnection.HTTP_OK) {
			JSONObject errorJSONObject = null;

			try {
				errorJSONObject = new JSONObject(httpResponse.body());
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to parse error response as JSON",
						jsonException);
				}
			}

			throw new PageSpeedScoreProviderException(
				errorJSONObject, "Response code " + responseCode);
		}

		return _parseScores(httpResponse.body());
	}

	private PageSpeedScores _parseScores(String responseJSON) throws Exception {
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

	private static final Log _log = LogFactory.getLog(
		PageSpeedScoreProvider.class);

	private final HttpClient _httpClient;
	private final String _pageSpeedAPIKey;
	private final String _strategy;

}