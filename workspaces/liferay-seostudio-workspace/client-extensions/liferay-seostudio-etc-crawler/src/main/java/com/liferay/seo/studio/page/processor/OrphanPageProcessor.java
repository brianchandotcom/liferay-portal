/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.page.processor;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.seo.studio.model.CrawlHit;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.stereotype.Component;

/**
 * @author Brooke Dalton
 */
@Component
public class OrphanPageProcessor implements PageProcessor {

	@Override
	public JSONObject processInsight(
			List<CrawlHit> crawlHits, String domainURL, long seoStudioScanId)
		throws Exception {

		Set<String> canonicalURLs = new LinkedHashSet<>();
		Set<String> linkedURLs = new HashSet<>();

		for (CrawlHit crawlHit : crawlHits) {
			String canonicalURL = crawlHit.getCanonicalURL();

			if (Validator.isNull(canonicalURL)) {
				continue;
			}

			canonicalURLs.add(canonicalURL);

			for (String linkedURL : crawlHit.getLinks()) {
				if (Validator.isNotNull(linkedURL) &&
					!linkedURL.equals(canonicalURL)) {

					linkedURLs.add(linkedURL);
				}
			}
		}

		List<String> orphanPageURLs = TransformUtil.transform(
			canonicalURLs,
			canonicalURL -> {
				if (canonicalURL.equals(domainURL) ||
					linkedURLs.contains(canonicalURL)) {

					return null;
				}

				return canonicalURL;
			});

		if (ListUtil.isEmpty(orphanPageURLs)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"No orphan pages were found for SEO Studio scan ID " +
						seoStudioScanId);
			}

			return null;
		}

		return new JSONObject(
		).put(
			"category", "linksAndURLs"
		).put(
			"classification", "problem"
		).put(
			"description",
			StringBundler.concat(
				"This page is published and indexable but has zero internal ",
				"links pointing to it. Orphan pages are nearly invisible to ",
				"both users browsing the site and crawlers building the link ",
				"graph. Even when they are listed in a sitemap, they collect ",
				"very little ranking authority.")
		).put(
			"fixHint",
			StringBundler.concat(
				"Identify 2-5 topically related pages and add contextual ",
				"internal links pointing to the orphan, with descriptive ",
				"anchor text. If no relevant linking context exists anywhere ",
				"on the site, that is a signal the page may not belong in the ",
				"public site at all.")
		).put(
			"name", "orphanPages"
		).put(
			"pageURLs", orphanPageURLs
		).put(
			"severity", "2"
		);
	}

	private static final Log _log = LogFactory.getLog(
		OrphanPageProcessor.class);

}