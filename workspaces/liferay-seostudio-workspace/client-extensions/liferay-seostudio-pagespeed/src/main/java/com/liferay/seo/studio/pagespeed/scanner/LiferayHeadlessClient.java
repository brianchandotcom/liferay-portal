/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.scanner;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.StringReader;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

/**
 * @author Kiana Suetani
 */
public class LiferayHeadlessClient {

	public LiferayHeadlessClient(
		String authToken, HttpClient httpClient, String portalBaseURL) {

		_authToken = authToken;
		_httpClient = httpClient;
		_portalBaseURL = portalBaseURL;
	}

	public String[] getDomainInfo(long domainId) {
		try {
			JSONObject domainJSONObject = _getDomainJSONObject(domainId);

			if (domainJSONObject == null) {
				return new String[2];
			}

			String domainHostname = domainJSONObject.optString(
				"domainHostname", null);

			String pageSpeedAPIKey = null;

			long instanceId = domainJSONObject.optLong(
				"r_seoStudioInstanceToSEOStudioDomains_seoStudioInstanceId", 0);

			if (instanceId > 0) {
				String instanceJSON = _makeRequest(
					_portalBaseURL + "/o/seo-studio/instances/" + instanceId);

				if (Validator.isNotNull(instanceJSON)) {
					JSONObject instanceJSONObject = new JSONObject(
						instanceJSON);

					pageSpeedAPIKey = instanceJSONObject.optString(
						"googlePageSpeedApiKey", null);
				}
			}

			return new String[] {domainHostname, pageSpeedAPIKey};
		}
		catch (Exception exception) {
			if (exception instanceof InterruptedException) {
				Thread.currentThread(
				).interrupt();
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to read domain info for domain " + domainId,
					exception);
			}

			return new String[2];
		}
	}

	public List<String> getPageURLs(String domainHostname, int maxPages)
		throws Exception {

		if ((maxPages <= 0) || Validator.isNull(domainHostname)) {
			return new ArrayList<>();
		}

		String sitemapURL = "https://" + domainHostname + "/sitemap.xml";

		LinkedHashSet<String> urlSet = new LinkedHashSet<>();

		_addSitemapURLs(maxPages, sitemapURL, urlSet);

		List<String> urls = new ArrayList<>(urlSet);

		if (urls.size() > maxPages) {
			return urls.subList(0, maxPages);
		}

		return urls;
	}

	private void _addSitemapURLs(
		int maxPages, String sitemapURL, LinkedHashSet<String> urlSet) {

		try {
			String sitemapXML = _makeRequest(sitemapURL);

			if (Validator.isNotNull(sitemapXML)) {
				urlSet.addAll(_parseSitemapURLs(0, maxPages, sitemapXML));
			}
		}
		catch (Exception exception) {
			if (exception instanceof InterruptedException) {
				Thread.currentThread(
				).interrupt();
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse sitemap " + sitemapURL, exception);
			}
		}
	}

	private List<String> _getChildSitemapURLs(Element rootElement) {
		List<String> childSitemapURLs = new ArrayList<>();

		NodeList sitemapNodeList = rootElement.getElementsByTagName("sitemap");

		for (int i = 0; i < sitemapNodeList.getLength(); i++) {
			if (!(sitemapNodeList.item(i) instanceof Element)) {
				continue;
			}

			Element sitemapElement = (Element)sitemapNodeList.item(i);

			NodeList locNodeList = sitemapElement.getElementsByTagName("loc");

			if (locNodeList.getLength() > 0) {
				String loc = locNodeList.item(
					0
				).getTextContent();

				if (Validator.isNotNull(loc)) {
					childSitemapURLs.add(loc);
				}
			}
		}

		return childSitemapURLs;
	}

	private JSONObject _getDomainJSONObject(long domainId) {
		String domainJSON = _makeRequest(
			_portalBaseURL + "/o/seo-studio/domains/" + domainId);

		if (Validator.isNull(domainJSON)) {
			return null;
		}

		return new JSONObject(domainJSON);
	}

	private String _makeRequest(String url) {
		try {
			HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder(
			).GET(
			).timeout(
				Duration.ofSeconds(30)
			).uri(
				URI.create(url)
			);

			if (Validator.isNotNull(_authToken)) {
				httpRequestBuilder.header(
					"Authorization", "Bearer " + _authToken);
			}

			HttpResponse<String> httpResponse = _httpClient.send(
				httpRequestBuilder.build(),
				HttpResponse.BodyHandlers.ofString());

			if (httpResponse.statusCode() != HttpURLConnection.HTTP_OK) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Response code ", httpResponse.statusCode(),
							" for ", url));
				}

				return null;
			}

			return httpResponse.body();
		}
		catch (Exception exception) {
			if (exception instanceof InterruptedException) {
				Thread.currentThread(
				).interrupt();
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Unable to make request to " + url, exception);
			}

			return null;
		}
	}

	private List<String> _parseSitemapURLs(int depth, int maxPages, String xml)
		throws Exception {

		if (depth > 3) {
			if (_log.isDebugEnabled()) {
				_log.debug("Maximum sitemap recursion depth exceeded");
			}

			return new ArrayList<>();
		}

		Document document = _parseXML(xml);

		Element rootElement = document.getDocumentElement();

		if (rootElement == null) {
			return new ArrayList<>();
		}

		List<String> urls = new ArrayList<>();

		if (Objects.equals(rootElement.getTagName(), "sitemapindex")) {
			for (String childSitemapURL : _getChildSitemapURLs(rootElement)) {
				try {
					String childSitemapXML = _makeRequest(childSitemapURL);

					if (Validator.isNotNull(childSitemapXML)) {
						urls.addAll(
							_parseSitemapURLs(
								depth + 1, maxPages, childSitemapXML));
					}
				}
				catch (Exception exception) {
					if (exception instanceof InterruptedException) {
						Thread.currentThread(
						).interrupt();
					}

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to parse child sitemap " + childSitemapURL,
							exception);
					}
				}

				if (urls.size() >= maxPages) {
					break;
				}
			}
		}
		else {
			NodeList urlNodeList = rootElement.getElementsByTagName("url");

			for (int i = 0; i < urlNodeList.getLength(); i++) {
				if (!(urlNodeList.item(i) instanceof Element)) {
					continue;
				}

				Element urlElement = (Element)urlNodeList.item(i);

				NodeList locNodeList = urlElement.getElementsByTagName("loc");

				if (locNodeList.getLength() == 0) {
					continue;
				}

				String url = locNodeList.item(
					0
				).getTextContent();

				if (Validator.isNotNull(url)) {
					urls.add(url);

					if (urls.size() >= maxPages) {
						break;
					}
				}
			}
		}

		return urls;
	}

	private Document _parseXML(String xml) throws Exception {
		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		documentBuilderFactory.setExpandEntityReferences(false);
		documentBuilderFactory.setFeature(
			"http://apache.org/xml/features/disallow-doctype-decl", true);
		documentBuilderFactory.setFeature(
			"http://xml.org/sax/features/external-general-entities", false);
		documentBuilderFactory.setFeature(
			"http://xml.org/sax/features/external-parameter-entities", false);
		documentBuilderFactory.setFeature(
			"http://apache.org/xml/features/nonvalidating/load-external-dtd",
			false);
		documentBuilderFactory.setNamespaceAware(false);
		documentBuilderFactory.setXIncludeAware(false);

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		return documentBuilder.parse(new InputSource(new StringReader(xml)));
	}

	private static final Log _log = LogFactory.getLog(
		LiferayHeadlessClient.class);

	private final String _authToken;
	private final HttpClient _httpClient;
	private final String _portalBaseURL;

}