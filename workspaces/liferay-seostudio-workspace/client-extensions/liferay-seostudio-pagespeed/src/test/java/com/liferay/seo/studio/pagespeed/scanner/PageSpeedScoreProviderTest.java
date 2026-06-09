/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.scanner;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.json.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

/**
 * @author Kiana Suetani
 */
public class PageSpeedScoreProviderTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testGetScores() throws Exception {
		HttpClient httpClient = Mockito.mock(HttpClient.class);

		HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);

		JSONObject responseJSONObject = new JSONObject();

		responseJSONObject.put(
			"lighthouseResult",
			new JSONObject(
			).put(
				"categories",
				new JSONObject(
				).put(
					"accessibility",
					new JSONObject(
					).put(
						"score", 0.92
					)
				).put(
					"best-practices",
					new JSONObject(
					).put(
						"score", 0.85
					)
				).put(
					"performance",
					new JSONObject(
					).put(
						"score", 0.78
					)
				).put(
					"seo",
					new JSONObject(
					).put(
						"score", 0.95
					)
				)
			));

		Mockito.when(
			httpResponse.body()
		).thenReturn(
			responseJSONObject.toString()
		);

		Mockito.when(
			httpResponse.statusCode()
		).thenReturn(
			200
		);

		Mockito.doReturn(
			httpResponse
		).when(
			httpClient
		).send(
			Mockito.any(), Mockito.any()
		);

		PageSpeedScoreProvider pageSpeedScoreProvider =
			new PageSpeedScoreProvider(httpClient, "", "DESKTOP");

		PageSpeedScores pageSpeedScores = pageSpeedScoreProvider.getScores("");

		Assertions.assertEquals(92, pageSpeedScores.getAccessibility());
		Assertions.assertEquals(85, pageSpeedScores.getBestPractices());
		Assertions.assertEquals(78, pageSpeedScores.getPerformance());
		Assertions.assertEquals(95, pageSpeedScores.getSEO());
	}

	@Test
	public void testIsQuotaExceededWhenErrorIsNonquota() {
		JSONObject errorJSONObject = new JSONObject();

		errorJSONObject.put(
			"error",
			new JSONObject(
			).put(
				"code", 403
			).put(
				"message", ""
			));

		PageSpeedScoreProvider.PageSpeedScoreProviderException
			pageSpeedScoreProviderException =
				new PageSpeedScoreProvider.PageSpeedScoreProviderException(
					errorJSONObject, "");

		Assertions.assertFalse(
			pageSpeedScoreProviderException.isQuotaExceeded());
	}

	@Test
	public void testIsQuotaExceededWhenErrorJSONIsPresent() {
		JSONObject errorJSONObject = new JSONObject();

		errorJSONObject.put(
			"error",
			new JSONObject(
			).put(
				"code", 429
			).put(
				"message", ""
			));

		PageSpeedScoreProvider.PageSpeedScoreProviderException
			pageSpeedScoreProviderException =
				new PageSpeedScoreProvider.PageSpeedScoreProviderException(
					errorJSONObject, "");

		Assertions.assertEquals(
			errorJSONObject,
			pageSpeedScoreProviderException.
				getGooglePageSpeedErrorJSONObject());
		Assertions.assertTrue(
			pageSpeedScoreProviderException.isQuotaExceeded());
	}

	@Test
	public void testIsQuotaExceededWhenJSONIsNull() {
		PageSpeedScoreProvider.PageSpeedScoreProviderException
			pageSpeedScoreProviderException =
				new PageSpeedScoreProvider.PageSpeedScoreProviderException("");

		Assertions.assertFalse(
			pageSpeedScoreProviderException.isQuotaExceeded());
	}

}