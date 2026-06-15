/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.service;

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

import org.json.JSONObject;

import org.springframework.stereotype.Component;

/**
 * @author Kiana Suetani
 */
@Component
public class PageSpeedScoresService {

	public PageSpeedScores getPageSpeedScores(
			String apiKey, String strategy, String url)
		throws InterruptedException, IOException {

		HttpResponse<String> httpResponse = _httpClient.send(
			HttpRequest.newBuilder(
			).uri(
				URI.create(_buildURL(apiKey, strategy, url))
			).GET(
			).build(),
			HttpResponse.BodyHandlers.ofString());

		if (httpResponse.statusCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException(
				"Unable to fetch PageSpeed scores, HTTP " +
					httpResponse.statusCode());
		}

		return _parsePageSpeedScores(httpResponse.body());
	}

	public static class PageSpeedScores {

		public PageSpeedScores(
			int accessibility, int bestPractices, int performance, int seo) {

			_accessibility = accessibility;
			_bestPractices = bestPractices;
			_performance = performance;
			_seo = seo;
		}

		public int getAccessibility() {
			return _accessibility;
		}

		public int getBestPractices() {
			return _bestPractices;
		}

		public int getPerformance() {
			return _performance;
		}

		public int getSEO() {
			return _seo;
		}

		private final int _accessibility;
		private final int _bestPractices;
		private final int _performance;
		private final int _seo;

	}

	private String _buildURL(String apiKey, String strategy, String url) {
		StringBundler sb = new StringBundler(10);

		sb.append("https://content-pagespeedonline.googleapis.com");
		sb.append("/pagespeedonline/v5/runPagespeed");
		sb.append("?category=ACCESSIBILITY&category=BEST_PRACTICES");
		sb.append("&category=PERFORMANCE&category=SEO&fields=");
		sb.append("lighthouseResult/categories/*/score&key=");
		sb.append(URLEncoder.encode(apiKey, StandardCharsets.UTF_8));
		sb.append("&strategy=");
		sb.append(strategy);
		sb.append("&url=");
		sb.append(URLEncoder.encode(url, StandardCharsets.UTF_8));

		return sb.toString();
	}

	private int _getPageSpeedScore(JSONObject jsonObject) {
		return (int)Math.round(jsonObject.getDouble("score") * 100);
	}

	private PageSpeedScores _parsePageSpeedScores(String responseJSON) {
		JSONObject responseJSONObject = new JSONObject(responseJSON);

		JSONObject lighthouseResultJSONObject =
			responseJSONObject.getJSONObject("lighthouseResult");

		JSONObject categoriesJSONObject =
			lighthouseResultJSONObject.getJSONObject("categories");

		return new PageSpeedScores(
			_getPageSpeedScore(
				categoriesJSONObject.getJSONObject("accessibility")),
			_getPageSpeedScore(
				categoriesJSONObject.getJSONObject("best-practices")),
			_getPageSpeedScore(
				categoriesJSONObject.getJSONObject("performance")),
			_getPageSpeedScore(categoriesJSONObject.getJSONObject("seo")));
	}

	private final HttpClient _httpClient = HttpClient.newBuilder(
	).connectTimeout(
		Duration.ofSeconds(5)
	).build();

}