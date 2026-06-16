/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.seo.studio.model.CrawlHit;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Brooke Dalton
 */
@Component
public class SEOStudioService extends BaseService {

	public static URI toCrawlURI(String hostname) {
		if (Validator.isNull(hostname)) {
			throw new IllegalArgumentException("Hostname is required");
		}

		hostname = StringUtil.toLowerCase(hostname.trim());

		if (!hostname.startsWith("http://") &&
			!hostname.startsWith("https://")) {

			hostname = "https://" + hostname;
		}

		URI uri = URI.create(hostname);

		if (Validator.isNull(uri.getHost())) {
			throw new IllegalArgumentException(
				"Hostname \"" + hostname + "\" has no host component");
		}

		return uri;
	}

	public static String toDomainURL(URI uri) {
		String host = StringUtil.toLowerCase(uri.getHost());
		String scheme = StringUtil.toLowerCase(uri.getScheme());

		if (uri.getPort() == -1) {
			return scheme + "://" + host;
		}

		return StringBundler.concat(scheme, "://", host, ":", uri.getPort());
	}

	public static String toIndexName(long seoStudioDomainId) {
		return "seo_studio_" + seoStudioDomainId;
	}

	public String fetchActiveScans() {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			"/o/seo-studio/scans"
		).queryParam(
			"filter", "state in ('queued','running')"
		).queryParam(
			"pageSize", 100
		).build();

		return get(_getAuthorization(), uriComponents.toUri());
	}

	public List<CrawlHit> fetchCrawlHits(long seoStudioDomainId) {
		List<CrawlHit> crawlHits = new ArrayList<>();

		String lastURL = null;

		while (true) {
			JSONObject hitsJSONObject = new JSONObject(
				_fetchCrawlHits(lastURL, 2000, seoStudioDomainId));

			JSONArray itemsJSONArray = hitsJSONObject.optJSONArray("items");

			if ((itemsJSONArray == null) || itemsJSONArray.isEmpty()) {
				break;
			}

			String previousLastURL = lastURL;

			for (Object object : itemsJSONArray) {
				CrawlHit crawlHit = new CrawlHit((JSONObject)object);

				crawlHits.add(crawlHit);

				lastURL = crawlHit.getURL();
			}

			if (Objects.equals(previousLastURL, lastURL)) {
				break;
			}
		}

		return crawlHits;
	}

	public String fetchDomain(long seoStudioDomainId) {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			"/o/seo-studio/domains/" + seoStudioDomainId
		).build();

		return get(_getAuthorization(), uriComponents.toUri());
	}

	public String fetchPage(int page, int pageSize, long seoStudioScanId) {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			"/o/seo-studio/pages"
		).queryParam(
			"filter",
			"r_seoStudioScanToSEOStudioPages_seoStudioScanId eq '" +
				seoStudioScanId + "'"
		).queryParam(
			"page", page
		).queryParam(
			"pageSize", pageSize
		).build();

		return get(_getAuthorization(), uriComponents.toUri());
	}

	public String postInsightType(JSONObject jsonObject) {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			"/o/seo-studio/insight-types"
		).build();

		return post(
			_getAuthorization(), jsonObject.toString(), uriComponents.toUri());
	}

	public String postPagesBatch(JSONArray jsonArray) {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			"/o/seo-studio/pages/batch"
		).build();

		return post(
			_getAuthorization(), jsonArray.toString(), uriComponents.toUri());
	}

	public String postScanInsightsBatch(JSONArray jsonArray) {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			"/o/seo-studio/scan-insights/batch"
		).build();

		return post(
			_getAuthorization(), jsonArray.toString(), uriComponents.toUri());
	}

	public String updateDomain(JSONObject jsonObject, long seoStudioDomainId) {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			"/o/seo-studio/domains/" + seoStudioDomainId
		).build();

		return patch(
			_getAuthorization(), jsonObject.toString(), uriComponents.toUri());
	}

	public String updateScan(JSONObject jsonObject, long seoStudioScanId) {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			"/o/seo-studio/scans/" + seoStudioScanId
		).build();

		return patch(
			_getAuthorization(), jsonObject.toString(), uriComponents.toUri());
	}

	public String updateScan(
		String errorMessage, long seoStudioScanId, String state) {

		JSONObject jsonObject = new JSONObject();

		if (Validator.isNotNull(errorMessage)) {
			jsonObject.put("errorMessage", errorMessage);
		}

		jsonObject.put("state", state);

		return updateScan(jsonObject, seoStudioScanId);
	}

	private String _fetchCrawlHits(
		String lastURL, int pageSize, long seoStudioDomainId) {

		UriComponentsBuilder uriComponentsBuilder =
			UriComponentsBuilder.fromPath(
				"/o/seo-studio/v1.0/seo-studio-domains/" + seoStudioDomainId +
					"/crawl-hits"
			).queryParam(
				"pageSize", pageSize
			);

		if (Validator.isNotNull(lastURL)) {
			uriComponentsBuilder.queryParam("lastURL", lastURL);
		}

		UriComponents uriComponents = uriComponentsBuilder.build();

		uriComponents = uriComponents.encode();

		return get(_getAuthorization(), uriComponents.toUri());
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-seostudio-etc-crawler-oahs");
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}