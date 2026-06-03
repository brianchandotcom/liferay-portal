/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.crawler;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.seo.studio.model.CrawlHit;
import com.liferay.seo.studio.service.SEOStudioService;

import java.net.URI;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Brooke Dalton
 */
@Component
public class DetectOrphanPagesCrawler {

	public void detect(
			long seoStudioScanId, long accountEntryId, URI hostname,
			String insightType, List<CrawlHit> crawlHits)
		throws Exception {

		Set<String> canonicalURLs = new LinkedHashSet<>();
		Set<String> linkedCanonicalURLs = new HashSet<>();

		for (CrawlHit crawlHit : crawlHits) {
			String canonicalURL = crawlHit.getCanonicalURL();

			if ((canonicalURL == null) || canonicalURL.isBlank()) {
				continue;
			}

			canonicalURLs.add(canonicalURL);

			for (String link : crawlHit.getLinks()) {
				if ((link != null) && !link.isBlank() &&
					!link.equals(canonicalURL)) {

					linkedCanonicalURLs.add(link);
				}
			}
		}

		List<String> orphans = new ArrayList<>();

		String seedURL = SEOStudioService.toDomainURL(hostname);

		for (String canonicalURL : canonicalURLs) {
			if (canonicalURL.equals(seedURL) ||
				linkedCanonicalURLs.contains(canonicalURL)) {

				continue;
			}

			orphans.add(canonicalURL);
		}

		if (ListUtil.isEmpty(orphans)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"No orphan pages were detected for scan " +
						seoStudioScanId);
			}

			return;
		}

		long seoStudioInsightTypeId = _findOrCreateInsightTypeId(
			seoStudioScanId, accountEntryId, insightType);

		_createPagesBatch(seoStudioScanId, accountEntryId, orphans);

		Map<String, Long> pageIdsByURL = _fetchPageIdsByURL(
			seoStudioScanId, orphans);

		_createScanInsightsBatch(
			seoStudioScanId, accountEntryId, seoStudioInsightTypeId, orphans,
			pageIdsByURL);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Wrote ", orphans.size(),
					" orphan page scan insights for insight type ",
					seoStudioInsightTypeId));
		}
	}

	private void _createPagesBatch(
			long seoStudioScanId, long accountEntryId, List<String> orphans)
		throws Exception {

		for (int i = 0; i < orphans.size(); i += _BATCH_SIZE) {
			List<String> chunk = orphans.subList(
				i, Math.min(i + _BATCH_SIZE, orphans.size()));

			JSONArray pagesJSONArray = new JSONArray();

			for (String orphan : chunk) {
				pagesJSONArray.put(
					_toPageJSONObject(accountEntryId, orphan, seoStudioScanId));
			}

			_seoStudioService.createPagesBatch(pagesJSONArray);
		}
	}

	private void _createScanInsightsBatch(
			long seoStudioScanId, long accountEntryId,
			long seoStudioInsightTypeId, List<String> orphans,
			Map<String, Long> pageIdsByURL)
		throws Exception {

		String detectedDate = Instant.now(
		).truncatedTo(
			ChronoUnit.SECONDS
		).toString();

		for (int i = 0; i < orphans.size(); i += _BATCH_SIZE) {
			List<String> chunk = orphans.subList(
				i, Math.min(i + _BATCH_SIZE, orphans.size()));

			JSONArray scanInsightsJSONArray = new JSONArray();

			for (String orphan : chunk) {
				Long seoStudioPageId = pageIdsByURL.get(orphan);

				if (seoStudioPageId == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"No page was found for orphan URL " + orphan +
								"; skipping scan insight");
					}

					continue;
				}

				scanInsightsJSONArray.put(
					_toScanInsightJSONObject(
						accountEntryId, detectedDate, seoStudioInsightTypeId,
						seoStudioPageId, seoStudioScanId));
			}

			if (scanInsightsJSONArray.length() == 0) {
				continue;
			}

			_seoStudioService.createScanInsightsBatch(scanInsightsJSONArray);
		}
	}

	private Map<String, Long> _fetchPageIdsByURL(
			long seoStudioScanId, List<String> orphans)
		throws Exception {

		long time = System.currentTimeMillis() + _PAGE_FETCH_TIMEOUT;

		Map<String, Long> pageIdsByURL = _readPages(seoStudioScanId);

		while (pageIdsByURL.size() < orphans.size()) {
			if (System.currentTimeMillis() > time) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Timed out waiting for ", orphans.size(),
							" pages to be readable for scan ", seoStudioScanId,
							"; found ", pageIdsByURL.size()));
				}

				break;
			}

			Thread.sleep(_PAGE_FETCH_POLL_INTERVAL);

			pageIdsByURL = _readPages(seoStudioScanId);
		}

		return pageIdsByURL;
	}

	private long _findOrCreateInsightTypeId(
		long seoStudioScanId, long accountEntryId, String insightType) {

		String externalReferenceCode = insightType + ":" + seoStudioScanId;

		try {
			String body = _seoStudioService.findInsightTypeByERC(
				externalReferenceCode);

			JSONObject insightTypeJSONObject = new JSONObject(body);

			if (insightTypeJSONObject.has("id")) {
				return insightTypeJSONObject.getLong("id");
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No existing insight type for \"" + externalReferenceCode +
						"\"; creating",
					exception);
			}
		}

		JSONObject bodyJSONObject = new JSONObject();

		bodyJSONObject.put(
			"category", "linksAndURLs"
		).put(
			"externalReferenceCode", externalReferenceCode
		).put(
			"name", "orphanPages"
		).put(
			"r_accountToSEOStudioInsightTypes_accountEntryId", accountEntryId
		).put(
			"r_seoStudioScanToSEOStudioInsightTypes_seoStudioScanId",
			seoStudioScanId
		).put(
			"severity", "high"
		);

		JSONObject responseJSONObject = new JSONObject(
			_seoStudioService.createInsightType(bodyJSONObject));

		return responseJSONObject.getLong("id");
	}

	private Map<String, Long> _readPages(long seoStudioScanId) {
		Map<String, Long> pageIdsByURL = new HashMap<>();

		int page = 1;

		while (true) {
			JSONObject pagesJSONObject = new JSONObject(
				_seoStudioService.fetchPages(seoStudioScanId, 2000, page));

			JSONArray itemsJSONArray = pagesJSONObject.optJSONArray("items");

			if ((itemsJSONArray == null) || (itemsJSONArray.length() == 0)) {
				break;
			}

			for (Object itemObject : itemsJSONArray) {
				JSONObject itemJSONObject = (JSONObject)itemObject;

				String pageURL = itemJSONObject.optString("pageURL", null);

				if ((pageURL != null) && !pageURL.isBlank()) {
					pageIdsByURL.put(pageURL, itemJSONObject.getLong("id"));
				}
			}

			page++;
		}

		return pageIdsByURL;
	}

	private JSONObject _toPageJSONObject(
		long accountEntryId, String pageURL, long seoStudioScanId) {

		JSONObject pageJSONObject = new JSONObject();

		pageJSONObject.put(
			"pageURL", pageURL
		).put(
			"r_accountToSEOStudioPages_accountEntryId", accountEntryId
		).put(
			"r_seoStudioScanToSEOStudioPages_seoStudioScanId", seoStudioScanId
		);

		return pageJSONObject;
	}

	private JSONObject _toScanInsightJSONObject(
		long accountEntryId, String detectedDate, long seoStudioInsightTypeId,
		long seoStudioPageId, long seoStudioScanId) {

		JSONObject scanInsightJSONObject = new JSONObject();

		scanInsightJSONObject.put(
			"classification", "informational"
		).put(
			"detectedDate", detectedDate
		).put(
			"r_accountToSEOStudioScanInsights_accountEntryId", accountEntryId
		).put(
			"r_seoStudioInsightTypeToScanInsights_seoStudioInsightTypeId",
			seoStudioInsightTypeId
		).put(
			"r_seoStudioPageToSEOStudioScanInsights_seoStudioPageId",
			seoStudioPageId
		).put(
			"r_seoStudioScanToSEOStudioScanInsights_seoStudioScanId",
			seoStudioScanId
		);

		return scanInsightJSONObject;
	}

	private static final int _BATCH_SIZE = 100;

	private static final long _PAGE_FETCH_POLL_INTERVAL = 1000;

	private static final long _PAGE_FETCH_TIMEOUT = 60000;

	private static final Log _log = LogFactory.getLog(
		DetectOrphanPagesCrawler.class);

	@Autowired
	private SEOStudioService _seoStudioService;

}