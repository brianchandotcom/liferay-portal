/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.page.processor;

import com.liferay.seo.studio.model.CrawlHit;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Brooke Dalton
 */
public interface PageProcessor {

	public JSONObject processInsight(
			List<CrawlHit> crawlHits, String domainURL, long seoStudioScanId)
		throws Exception;

}