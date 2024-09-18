/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot.BaseRestController;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * @author Keven Leone
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

	@GetMapping("project/{projectId}")
	public String getProject(@PathVariable String projectId) throws Exception {
		return get(
			"Basic " + _analyticsAuthBasic,
			"/o/faro/main/project/" + projectId);
	}

	@PostMapping("provisioning")
	public String postProvisioning(@RequestBody String json) throws Exception {
		JSONObject jsonObject = new JSONObject(json);

		return WebClient.builder(
		).baseUrl(
			_analyticsAuthUrl
		).defaultHeader(
			HttpHeaders.AUTHORIZATION, "Basic " + _analyticsAuthBasic
		).build(
		).post(
		).uri(
			"/o/faro/main/project/unprovisioned"
		).contentType(
			MediaType.APPLICATION_FORM_URLENCODED
		).body(
			BodyInserters.fromFormData(
				"corpProjectName", jsonObject.getString("corpProjectName")
			).with(
				"corpProjectUuid", jsonObject.getString("corpProjectUuid")
			).with(
				"emailAddressDomains",
				jsonObject.getJSONArray(
					"emailAddressDomains"
				).toString()
			).with(
				"friendlyURL", jsonObject.getString("friendlyURL")
			).with(
				"incidentReportEmailAddresses",
				jsonObject.getJSONArray(
					"incidentReportEmailAddresses"
				).toString()
			).with(
				"name", jsonObject.getString("name")
			).with(
				"serverLocation", _ANALYTICS_PROVISIONING_SERVER_LOCATION
			).with(
				"sharedCluster", "false"
			).with(
				"timeZoneId", jsonObject.getString("timeZoneId")
			).with(
				"trial", "true"
			).with(
				"ownerEmailAddress", jsonObject.getString("ownerEmailAddress")
			)
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	@Override
	protected String getLXCDXPURL() {
		return _analyticsAuthUrl;
	}

	private static final String _ANALYTICS_PROVISIONING_SERVER_LOCATION =
		"us-west1-ac-uat-c1";

	@Value("${liferay.marketplace.analytics.auth.basic}")
	private String _analyticsAuthBasic;

	@Value("${liferay.marketplace.analytics.auth.token}")
	private String _analyticsAuthToken;

	@Value("${liferay.marketplace.analytics.auth.url}")
	private String _analyticsAuthUrl;

	private final DefaultUriBuilderFactory _defaultUriBuilderFactory =
		new DefaultUriBuilderFactory();

}