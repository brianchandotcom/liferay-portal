/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.scanner;

import java.net.http.HttpClient;

import org.json.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

/**
 * @author Kiana Suetani
 */
public class PageSpeedScoreProviderTest {

	@Test
	public void testIsQuotaExceededWhenErrorIsNonquota() {
		JSONObject errorJSONObject = new JSONObject();

		errorJSONObject.put(
			"error",
			new JSONObject(
			).put(
				"code", 403
			).put(
				"message", "Forbidden"
			));

		PageSpeedScoreProvider.PageSpeedScoreProviderException
			pageSpeedScoreProviderException =
				new PageSpeedScoreProvider.PageSpeedScoreProviderException(
					errorJSONObject, "Forbidden");

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
				"message", "Quota exceeded"
			));

		PageSpeedScoreProvider.PageSpeedScoreProviderException
			pageSpeedScoreProviderException =
				new PageSpeedScoreProvider.PageSpeedScoreProviderException(
					errorJSONObject, "Quota exceeded");

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
				new PageSpeedScoreProvider.PageSpeedScoreProviderException(
					"Some error");

		Assertions.assertFalse(
			pageSpeedScoreProviderException.isQuotaExceeded());
	}

	@Test
	public void testIsValidConnection() {
		PageSpeedScoreProvider pageSpeedScoreProvider =
			new PageSpeedScoreProvider(
				Mockito.mock(HttpClient.class), "apiKey", "DESKTOP");

		Assertions.assertTrue(pageSpeedScoreProvider.isValidConnection());
	}

	@Test
	public void testIsValidConnectionWithEmptyAPIKey() {
		PageSpeedScoreProvider pageSpeedScoreProvider =
			new PageSpeedScoreProvider(
				Mockito.mock(HttpClient.class), "", "DESKTOP");

		Assertions.assertFalse(pageSpeedScoreProvider.isValidConnection());
	}

	@Test
	public void testIsValidConnectionWithNullAPIKey() {
		PageSpeedScoreProvider pageSpeedScoreProvider =
			new PageSpeedScoreProvider(
				Mockito.mock(HttpClient.class), null, "DESKTOP");

		Assertions.assertFalse(pageSpeedScoreProvider.isValidConnection());
	}

}