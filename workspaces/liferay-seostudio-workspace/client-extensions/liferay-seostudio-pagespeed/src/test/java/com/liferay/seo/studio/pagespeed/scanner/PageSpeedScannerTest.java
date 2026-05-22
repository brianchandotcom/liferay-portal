/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.scanner;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

/**
 * @author Kiana Suetani
 */
public class PageSpeedScannerTest {

	@Test
	public void testScanURLsWithAllPagesFailed() throws Exception {
		PageSpeedScoreProvider pageSpeedScoreProvider = Mockito.mock(
			PageSpeedScoreProvider.class);

		Mockito.when(
			pageSpeedScoreProvider.getScores(Mockito.anyString())
		).thenThrow(
			new PageSpeedScoreProvider.PageSpeedScoreProviderException(
				"Connection error")
		);

		PageSpeedScanResult pageSpeedScanResult = _scanURLs(
			pageSpeedScoreProvider, _createURLs(3));

		Assertions.assertEquals(
			PageSpeedScanResult.STATUS_FAILED, pageSpeedScanResult.getStatus());
		Assertions.assertEquals(0, pageSpeedScanResult.getPagesScanned());
		Assertions.assertEquals(3, pageSpeedScanResult.getPagesErrored());
		Assertions.assertEquals(
			"All pages failed to scan", pageSpeedScanResult.getErrorMessage());
	}

	@Test
	public void testScanURLsWithMixedResults() throws Exception {
		PageSpeedScoreProvider pageSpeedScoreProvider = Mockito.mock(
			PageSpeedScoreProvider.class);

		Mockito.when(
			pageSpeedScoreProvider.getScores("https://example.com/page1")
		).thenReturn(
			new PageSpeedScores(80, 90, 70, 60)
		);

		Mockito.when(
			pageSpeedScoreProvider.getScores("https://example.com/page2")
		).thenThrow(
			new PageSpeedScoreProvider.PageSpeedScoreProviderException(
				"Connection error")
		);

		Mockito.when(
			pageSpeedScoreProvider.getScores("https://example.com/page3")
		).thenReturn(
			new PageSpeedScores(90, 80, 80, 80)
		);

		PageSpeedScanResult pageSpeedScanResult = _scanURLs(
			pageSpeedScoreProvider, _createURLs(3));

		Assertions.assertEquals(
			PageSpeedScanResult.STATUS_COMPLETED,
			pageSpeedScanResult.getStatus());
		Assertions.assertEquals(2, pageSpeedScanResult.getPagesScanned());
		Assertions.assertEquals(1, pageSpeedScanResult.getPagesErrored());
		Assertions.assertNull(pageSpeedScanResult.getErrorMessage());

		PageSpeedScores pageSpeedScores =
			pageSpeedScanResult.getAverageScores();

		Assertions.assertEquals(85, pageSpeedScores.getAccessibility());
		Assertions.assertEquals(85, pageSpeedScores.getBestPractices());
		Assertions.assertEquals(75, pageSpeedScores.getPerformance());
		Assertions.assertEquals(70, pageSpeedScores.getSeo());
	}

	@Test
	public void testScanURLsWithQuotaExceeded() throws Exception {
		PageSpeedScoreProvider pageSpeedScoreProvider = Mockito.mock(
			PageSpeedScoreProvider.class);

		Mockito.when(
			pageSpeedScoreProvider.getScores("https://example.com/page1")
		).thenReturn(
			new PageSpeedScores(80, 90, 70, 60)
		);

		JSONObject errorJSONObject = new JSONObject();

		errorJSONObject.put(
			"error",
			new JSONObject(
			).put(
				"code", 429
			));

		Mockito.when(
			pageSpeedScoreProvider.getScores("https://example.com/page2")
		).thenThrow(
			new PageSpeedScoreProvider.PageSpeedScoreProviderException(
				errorJSONObject, "Quota exceeded")
		);

		Mockito.when(
			pageSpeedScoreProvider.getScores("https://example.com/page3")
		).thenReturn(
			new PageSpeedScores(90, 80, 80, 80)
		);

		PageSpeedScanResult pageSpeedScanResult = _scanURLs(
			pageSpeedScoreProvider, _createURLs(3));

		Assertions.assertEquals(
			PageSpeedScanResult.STATUS_FAILED, pageSpeedScanResult.getStatus());
		Assertions.assertTrue(
			pageSpeedScanResult.getErrorMessage(
			).contains(
				"quota exceeded"
			));
	}

	@Test
	public void testScanURLsWithSuccessfulScan() throws Exception {
		PageSpeedScoreProvider pageSpeedScoreProvider = Mockito.mock(
			PageSpeedScoreProvider.class);

		Mockito.when(
			pageSpeedScoreProvider.getScores(Mockito.anyString())
		).thenReturn(
			new PageSpeedScores(80, 90, 70, 60)
		);

		PageSpeedScanResult pageSpeedScanResult = _scanURLs(
			pageSpeedScoreProvider, _createURLs(5));

		Assertions.assertEquals(
			PageSpeedScanResult.STATUS_COMPLETED,
			pageSpeedScanResult.getStatus());
		Assertions.assertEquals(5, pageSpeedScanResult.getPagesScanned());
		Assertions.assertEquals(5, pageSpeedScanResult.getPagesTotal());
		Assertions.assertEquals(0, pageSpeedScanResult.getPagesErrored());
		Assertions.assertNull(pageSpeedScanResult.getErrorMessage());

		PageSpeedScores pageSpeedScores =
			pageSpeedScanResult.getAverageScores();

		Assertions.assertEquals(80, pageSpeedScores.getAccessibility());
		Assertions.assertEquals(90, pageSpeedScores.getBestPractices());
		Assertions.assertEquals(70, pageSpeedScores.getPerformance());
		Assertions.assertEquals(60, pageSpeedScores.getSeo());
	}

	private List<String> _createURLs(int count) {
		List<String> urls = new ArrayList<>();

		for (int i = 1; i <= count; i++) {
			urls.add("https://example.com/page" + i);
		}

		return urls;
	}

	private PageSpeedScanResult _scanURLs(
			PageSpeedScoreProvider pageSpeedScoreProvider, List<String> urls)
		throws Exception {

		PageSpeedScanner pageSpeedScanner = new PageSpeedScanner();

		Method method = PageSpeedScanner.class.getDeclaredMethod(
			"_scanURLs", PageSpeedScoreProvider.class, List.class);

		method.setAccessible(true);

		return (PageSpeedScanResult)method.invoke(
			pageSpeedScanner, pageSpeedScoreProvider, urls);
	}

}