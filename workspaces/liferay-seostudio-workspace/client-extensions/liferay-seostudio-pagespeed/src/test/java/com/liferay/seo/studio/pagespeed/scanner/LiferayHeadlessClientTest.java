/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.scanner;

import com.liferay.petra.string.StringBundler;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

/**
 * @author Kiana Suetani
 */
public class LiferayHeadlessClientTest {

	@Test
	public void testGetPageURLs() throws Exception {
		HttpClient httpClient = Mockito.mock(HttpClient.class);

		HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);

		Mockito.when(
			httpResponse.statusCode()
		).thenReturn(
			200
		);

		Mockito.when(
			httpResponse.body()
		).thenReturn(
			StringBundler.concat(
				_SITEMAP_XML_HEADER, "<url><loc>https://a.co/1</loc></url>",
				"<url><loc>https://a.co/2</loc></url></urlset>")
		);

		Mockito.when(
			httpClient.send(
				Mockito.any(HttpRequest.class),
				Mockito.any(HttpResponse.BodyHandler.class))
		).thenReturn(
			httpResponse
		);

		LiferayHeadlessClient liferayHeadlessClient = new LiferayHeadlessClient(
			null, httpClient, "https://portal.a.co");

		List<String> urls = liferayHeadlessClient.getPageURLs("a.co", 100);

		Assertions.assertEquals(2, urls.size());
		Assertions.assertEquals("https://a.co/1", urls.get(0));
		Assertions.assertEquals("https://a.co/2", urls.get(1));
	}

	private static final String _SITEMAP_XML_HEADER =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?><urlset xmlns=" +
			"\"http://www.sitemaps.org/schemas/sitemap/0.9\">";

}