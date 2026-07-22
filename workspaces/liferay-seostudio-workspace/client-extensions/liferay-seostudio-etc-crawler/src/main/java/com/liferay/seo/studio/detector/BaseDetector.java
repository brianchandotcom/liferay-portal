/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.detector;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.seo.studio.model.CrawlHit;
import com.liferay.seo.studio.model.DetectorResult;
import com.liferay.seo.studio.service.SEOStudioService;

import java.net.URI;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Noor Najjar
 */
public abstract class BaseDetector {

	public void detect(
			long accountEntryId, List<CrawlHit> crawlHits, URI crawlURI,
			long seoStudioScanId)
		throws Exception {

		DetectorResult detectorResult = doDetect(
			accountEntryId, crawlHits, crawlURI, seoStudioScanId);

		if (detectorResult == null) {
			return;
		}

		List<String> pageURLs = detectorResult.getPageURLs();

		if (ListUtil.isEmpty(pageURLs)) {
			return;
		}

		JSONObject seoStudioInsightTypeJSONObject = new JSONObject(
			seoStudioService.postSEOStudioInsightType(
				new JSONObject(
				).put(
					"category", detectorResult.getCategory()
				).put(
					"description", detectorResult.getDescription()
				).put(
					"externalReferenceCode",
					detectorResult.getName() + "_" + seoStudioScanId
				).put(
					"fixHint", detectorResult.getFixHint()
				).put(
					"name", detectorResult.getName()
				).put(
					"r_accountToSEOStudioInsightTypes_accountEntryId",
					accountEntryId
				).put(
					"r_seoStudioScanToSEOStudioInsightTypes_seoStudioScanId",
					seoStudioScanId
				).put(
					"severity", detectorResult.getSeverity()
				)));

		_addSEOStudioScanInsights(
			accountEntryId, detectorResult, pageURLs,
			seoStudioInsightTypeJSONObject.getLong("id"),
			_resolveSEOStudioPageIds(accountEntryId, pageURLs, seoStudioScanId),
			seoStudioScanId);
	}

	protected abstract DetectorResult doDetect(
			long accountEntryId, List<CrawlHit> crawlHits, URI crawlURI,
			long seoStudioScanId)
		throws Exception;

	@Autowired
	protected SEOStudioService seoStudioService;

	private void _addSEOStudioPages(
			long accountEntryId, List<String> pageURLs, long seoStudioScanId)
		throws Exception {

		for (int i = 0; i < pageURLs.size(); i += _BATCH_SIZE) {
			JSONArray seoStudioPagesJSONArray = new JSONArray();

			List<String> batchPageURLs = pageURLs.subList(
				i, Math.min(i + _BATCH_SIZE, pageURLs.size()));

			for (String pageURL : batchPageURLs) {
				seoStudioPagesJSONArray.put(
					_toSEOStudioPageJSONObject(
						accountEntryId, pageURL, seoStudioScanId));
			}

			seoStudioService.postSEOStudioPagesBatch(seoStudioPagesJSONArray);
		}
	}

	private void _addSEOStudioScanInsights(
			long accountEntryId, DetectorResult detectorResult,
			List<String> pageURLs, long seoStudioInsightTypeId,
			Map<String, Long> seoStudioPageIds, long seoStudioScanId)
		throws Exception {

		String detectedDateString = Instant.now(
		).truncatedTo(
			ChronoUnit.SECONDS
		).toString();

		for (int i = 0; i < pageURLs.size(); i += _BATCH_SIZE) {
			JSONArray seoStudioScanInsightsJSONArray = new JSONArray();

			List<String> batchPageURLs = pageURLs.subList(
				i, Math.min(i + _BATCH_SIZE, pageURLs.size()));

			for (String pageURL : batchPageURLs) {
				Long seoStudioPageId = seoStudioPageIds.get(pageURL);

				if (seoStudioPageId == null) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to get a page for URL " + pageURL);
					}

					continue;
				}

				seoStudioScanInsightsJSONArray.put(
					_toSEOStudioScanInsightJSONObject(
						accountEntryId, detectorResult.getClassification(),
						detectedDateString, seoStudioInsightTypeId,
						seoStudioPageId, seoStudioScanId));
			}

			if (seoStudioScanInsightsJSONArray.isEmpty()) {
				continue;
			}

			seoStudioService.postSEOStudioScanInsightsBatch(
				seoStudioScanInsightsJSONArray);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Posted ", pageURLs.size(), " ", detectorResult.getName(),
					" SEO Studio Scan Insights for SEO Studio Insight Type ID ",
					seoStudioInsightTypeId));
		}
	}

	private Map<String, Long> _getSEOStudioPageIds(long seoStudioScanId) {
		Map<String, Long> seoStudioPageIds = new HashMap<>();

		int page = 1;

		while (true) {
			JSONArray itemsJSONArray = new JSONObject(
				seoStudioService.getSEOStudioPages(page, 2000, seoStudioScanId)
			).optJSONArray(
				"items"
			);

			if ((itemsJSONArray == null) || itemsJSONArray.isEmpty()) {
				break;
			}

			for (Object object : itemsJSONArray) {
				JSONObject itemJSONObject = (JSONObject)object;

				seoStudioPageIds.put(
					itemJSONObject.getString("pageURL"),
					itemJSONObject.getLong("id"));
			}

			page++;
		}

		return seoStudioPageIds;
	}

	private Map<String, Long> _resolveSEOStudioPageIds(
			long accountEntryId, List<String> pageURLs, long seoStudioScanId)
		throws Exception {

		Map<String, Long> seoStudioPageIds = _getSEOStudioPageIds(
			seoStudioScanId);

		List<String> missingPageURLs = ListUtil.filter(
			pageURLs, pageURL -> !seoStudioPageIds.containsKey(pageURL));

		if (ListUtil.isEmpty(missingPageURLs)) {
			return seoStudioPageIds;
		}

		_addSEOStudioPages(accountEntryId, missingPageURLs, seoStudioScanId);

		long deadline = System.currentTimeMillis() + 60000;

		while (true) {
			Set<String> existingPageURLs = seoStudioPageIds.keySet();

			if (existingPageURLs.containsAll(pageURLs)) {
				break;
			}

			if (System.currentTimeMillis() > deadline) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Timed out waiting for pages to be readable for SEO " +
							"Studio Scan ID " + seoStudioScanId);
				}

				break;
			}

			Thread.sleep(1000);

			seoStudioPageIds.putAll(_getSEOStudioPageIds(seoStudioScanId));
		}

		return seoStudioPageIds;
	}

	private JSONObject _toSEOStudioPageJSONObject(
		long accountEntryId, String pageURL, long seoStudioScanId) {

		return new JSONObject(
		).put(
			"pageURL", pageURL
		).put(
			"r_accountToSEOStudioPages_accountEntryId", accountEntryId
		).put(
			"r_seoStudioScanToSEOStudioPages_seoStudioScanId", seoStudioScanId
		);
	}

	private JSONObject _toSEOStudioScanInsightJSONObject(
		long accountEntryId, String classification, String detectedDateString,
		long seoStudioInsightTypeId, long seoStudioPageId,
		long seoStudioScanId) {

		return new JSONObject(
		).put(
			"classification", classification
		).put(
			"detectedDate", detectedDateString
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
	}

	private static final int _BATCH_SIZE = 100;

	private static final Log _log = LogFactory.getLog(BaseDetector.class);

}