/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot.BaseRestController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * @author Wellington Barbosa
 */
@RequestMapping("/analytics")
@RestController
public class AnalyticsRestController extends BaseRestController {

	@GetMapping("pages")
	public String getPages(
			@RequestParam(defaultValue = "", required = false) String channelId,
			@RequestParam(defaultValue = "", required = false) String keywords,
			@RequestParam(defaultValue = "", required = false) String page,
			@RequestParam(defaultValue = "", required = false) String rangeKey,
			@RequestParam(defaultValue = "", required = false) String
				sortMetric,
			@RequestParam(defaultValue = "", required = false) String sortOrder)
		throws Exception {

		return get(
			"Bearer " + _analyticsAuthToken,
			_defaultUriBuilderFactory.builder(
			).path(
				"/api/reports/pages"
			).queryParam(
				"channelId", channelId
			).queryParam(
				"keywords", keywords
			).queryParam(
				"page", page
			).queryParam(
				"rangeKey", rangeKey
			).queryParam(
				"sortMetric", sortMetric
			).queryParam(
				"sortOrder", sortOrder
			).build(
			).toString());
	}

	@Override
	protected String getLXCDXPURL() {
		return _analyticsAuthUrl;
	}

	@Value("${liferay.marketplace.analytics.auth.token}")
	private String _analyticsAuthToken;

	@Value("${liferay.marketplace.analytics.auth.url}")
	private String _analyticsAuthUrl;

	private final DefaultUriBuilderFactory _defaultUriBuilderFactory =
		new DefaultUriBuilderFactory();

}