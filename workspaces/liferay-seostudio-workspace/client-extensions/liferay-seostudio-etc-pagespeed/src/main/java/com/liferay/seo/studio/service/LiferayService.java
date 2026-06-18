/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.seo.studio.model.Domain;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Kiana Suetani
 */
@Component
public class LiferayService extends BaseService {

	public Domain getDomain(long domainId) {
		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObject(
				get(
					_liferayOAuth2AccessTokenManager.getAuthorization(
						"liferay-seostudio-etc-pagespeed-oahs"),
					UriComponentsBuilder.fromPath(
						"/o/seo-studio/domains/" + domainId
					).queryParam(
						"nestedFields", "seoStudioInstance"
					).build(
					).toUri()));
		}
		catch (JSONException | NullPointerException exception) {
			String message = "Domain " + domainId + " was not found";

			if (_log.isWarnEnabled()) {
				_log.warn(message, exception);
			}

			throw new IllegalArgumentException(message, exception);
		}

		return new Domain(jsonObject);
	}

	public List<String> getSitemapPageURLs(String hostname, int limit) {
		if ((limit <= 0) || Validator.isNull(hostname)) {
			return Collections.emptyList();
		}

		String sitemapXML = _getSitemapXML(
			"https://" + hostname + "/sitemap.xml");

		if (Validator.isNull(sitemapXML)) {
			return Collections.emptyList();
		}

		return _parseSitemapPageURLs(0, limit, sitemapXML);
	}

	private String _getSitemapXML(String url) {
		try {
			HttpResponse<String> httpResponse = _httpClient.send(
				HttpRequest.newBuilder(
				).uri(
					URI.create(url)
				).timeout(
					Duration.ofSeconds(10)
				).GET(
				).build(),
				HttpResponse.BodyHandlers.ofString());

			if (httpResponse.statusCode() != HttpURLConnection.HTTP_OK) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Unable to fetch sitemap ", url, ", HTTP ",
							httpResponse.statusCode()));
				}

				return null;
			}

			return httpResponse.body();
		}
		catch (IllegalArgumentException | IOException exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to fetch sitemap " + url, exception);
			}

			return null;
		}
		catch (InterruptedException interruptedException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to fetch sitemap " + url, interruptedException);
			}

			Thread thread = Thread.currentThread();

			thread.interrupt();

			return null;
		}
	}

	private List<String> _parseSitemapPageURLs(
		int depth, int limit, String sitemapXML) {

		if (limit <= 0) {
			return Collections.emptyList();
		}

		if (depth > 3) {
			if (_log.isDebugEnabled()) {
				_log.debug("Maximum sitemap recursion depth exceeded");
			}

			return Collections.emptyList();
		}

		List<String> urls = new ArrayList<>();

		JsonNode jsonNode = null;

		try {
			jsonNode = _xmlMapper.readTree(sitemapXML);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse sitemap", ioException);
			}

			return urls;
		}

		if (jsonNode == null) {
			return urls;
		}

		boolean hasSitemap = jsonNode.has("sitemap");

		JsonNode entriesJsonNode =
			hasSitemap ? jsonNode.path("sitemap") : jsonNode.path("url");

		List<JsonNode> entryJsonNodes = new ArrayList<>();

		if (entriesJsonNode.isArray()) {
			entriesJsonNode.forEach(entryJsonNodes::add);
		}
		else if (!entriesJsonNode.isMissingNode()) {
			entryJsonNodes.add(entriesJsonNode);
		}

		for (JsonNode entryJsonNode : entryJsonNodes) {
			if (urls.size() >= limit) {
				break;
			}

			JsonNode locJsonNode = entryJsonNode.path("loc");

			String loc = locJsonNode.asText(null);

			if (Validator.isNull(loc)) {
				continue;
			}

			loc = loc.trim();

			if (Validator.isNull(loc)) {
				continue;
			}

			if (hasSitemap) {
				String childSitemapXML = _getSitemapXML(loc);

				if (Validator.isNotNull(childSitemapXML)) {
					urls.addAll(
						_parseSitemapPageURLs(
							depth + 1, limit - urls.size(), childSitemapXML));
				}
			}
			else {
				urls.add(loc);
			}
		}

		return urls;
	}

	private static final Log _log = LogFactory.getLog(LiferayService.class);

	private final HttpClient _httpClient = HttpClient.newBuilder(
	).connectTimeout(
		Duration.ofSeconds(5)
	).followRedirects(
		HttpClient.Redirect.NORMAL
	).build();

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	private final XmlMapper _xmlMapper = new XmlMapper();

}