/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.seo.studio.model.CrawlHit;

import java.net.URI;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Brooke Dalton
 */
@Component
public class SEOStudioService extends BaseService {

	public static URI toCrawlURI(String hostname) {
		if ((hostname == null) || hostname.isBlank()) {
			throw new IllegalArgumentException("Hostname is required");
		}

		String hostnameString = StringUtil.toLowerCase(hostname.trim());

		if (!hostnameString.startsWith("http://") &&
			!hostnameString.startsWith("https://")) {

			hostnameString = "https://" + hostnameString;
		}

		URI uri = URI.create(hostnameString);

		if (uri.getHost() == null) {
			throw new IllegalArgumentException(
				"Hostname \"" + hostname + "\" has no host component");
		}

		return uri;
	}

	public static String toDomainURL(URI uri) {
		if (uri.getPort() == -1) {
			return uri.getScheme() + "://" + uri.getHost();
		}

		return StringBundler.concat(
			uri.getScheme(), "://", uri.getHost(), ":", uri.getPort());
	}

	public static String toIndexName(long seoStudioDomainId) {
		return "seo_studio_" + seoStudioDomainId;
	}

	public String createInsightType(JSONObject jsonObject) {
		return post(
			_authorization(), jsonObject.toString(),
			URI.create(_INSIGHT_TYPES));
	}

	public String createPagesBatch(JSONArray jsonArray) {
		return post(
			_authorization(), jsonArray.toString(),
			URI.create(_PAGES + "/batch"));
	}

	public String createScanInsightsBatch(JSONArray jsonArray) {
		return post(
			_authorization(), jsonArray.toString(),
			URI.create(_SCAN_INSIGHTS + "/batch"));
	}

	public List<CrawlHit> fetchCrawlHits(long seoStudioDomainId) {
		List<CrawlHit> crawlHits = new ArrayList<>();

		String searchAfter = null;

		while (true) {
			JSONObject hitsJSONObject = new JSONObject(
				_fetchCrawlHits(seoStudioDomainId, 2000, searchAfter));

			JSONArray hitsJSONArray = hitsJSONObject.optJSONArray("items");

			if ((hitsJSONArray == null) || (hitsJSONArray.length() == 0)) {
				break;
			}

			String previousSearchAfter = searchAfter;

			for (Object hitObject : hitsJSONArray) {
				CrawlHit crawlHit = new CrawlHit((JSONObject)hitObject);

				String url = crawlHit.getURL();

				if ((url == null) || url.isBlank()) {
					continue;
				}

				searchAfter = url;

				crawlHits.add(crawlHit);
			}

			if (Objects.equals(previousSearchAfter, searchAfter)) {
				break;
			}
		}

		return crawlHits;
	}

	public String fetchDomain(long seoStudioDomainId) {
		return get(
			_authorization(), URI.create(_DOMAINS + "/" + seoStudioDomainId));
	}

	public String fetchPages(long seoStudioScanId, int pageSize, int page) {
		String filter = URLEncoder.encode(
			StringBundler.concat(
				"r_seoStudioScanToSEOStudioPages_seoStudioScanId eq '",
				seoStudioScanId, "'"),
			StandardCharsets.UTF_8);

		return get(
			_authorization(),
			URI.create(
				StringBundler.concat(
					_PAGES, "?filter=", filter, "&page=", page, "&pageSize=",
					pageSize)));
	}

	public String findInsightTypeByERC(String externalReferenceCode) {
		String encodedERC = URLEncoder.encode(
			externalReferenceCode, StandardCharsets.UTF_8);

		return get(
			_authorization(),
			URI.create(
				_INSIGHT_TYPES + "/by-external-reference-code/" + encodedERC));
	}

	public String updateDomain(long seoStudioDomainId, JSONObject jsonObject) {
		return patch(
			_authorization(), jsonObject.toString(),
			URI.create(_DOMAINS + "/" + seoStudioDomainId));
	}

	public String updateScan(long seoStudioScanId, JSONObject jsonObject) {
		return patch(
			_authorization(), jsonObject.toString(),
			URI.create(_SCANS + "/" + seoStudioScanId));
	}

	private String _authorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-seostudio-crawler-oahs");
	}

	private String _fetchCrawlHits(
		long seoStudioDomainId, int pageSize, String searchAfter) {

		String url = StringBundler.concat(
			"/o/seo-studio/v1.0/seo-studio-domains/", seoStudioDomainId,
			"/crawl-hits?pageSize=", pageSize);

		if ((searchAfter != null) && !searchAfter.isBlank()) {
			url +=
				"&searchAfter=" +
					URLEncoder.encode(searchAfter, StandardCharsets.UTF_8);
		}

		return get(_authorization(), URI.create(url));
	}

	private static final String _DOMAINS = "/o/seo-studio/domains";

	private static final String _INSIGHT_TYPES = "/o/seo-studio/insight-types";

	private static final String _PAGES = "/o/seo-studio/pages";

	private static final String _SCAN_INSIGHTS = "/o/seo-studio/scan-insights";

	private static final String _SCANS = "/o/seo-studio/scans";

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}