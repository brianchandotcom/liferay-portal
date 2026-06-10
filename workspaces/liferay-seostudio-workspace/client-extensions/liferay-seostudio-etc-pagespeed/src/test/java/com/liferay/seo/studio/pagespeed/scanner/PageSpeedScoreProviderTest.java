/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.scanner;

import java.lang.reflect.Method;

import org.json.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Kiana Suetani
 */
public class PageSpeedScoreProviderTest {

	@Test
	public void testParseScores() throws Exception {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
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

		PageSpeedScores pageSpeedScores = _parseScores(jsonObject.toString());

		Assertions.assertEquals(92, pageSpeedScores.getAccessibility());
		Assertions.assertEquals(85, pageSpeedScores.getBestPractices());
		Assertions.assertEquals(78, pageSpeedScores.getPerformance());
		Assertions.assertEquals(95, pageSpeedScores.getSEO());
	}

	private PageSpeedScores _parseScores(String responseJSON) throws Exception {
		Method method = PageSpeedScoreProvider.class.getDeclaredMethod(
			"_parseScores", String.class);

		method.setAccessible(true);

		PageSpeedScoreProvider pageSpeedScoreProvider =
			new PageSpeedScoreProvider(null, null, null);

		return (PageSpeedScores)method.invoke(
			pageSpeedScoreProvider, responseJSON);
	}

}