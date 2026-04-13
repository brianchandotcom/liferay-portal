/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;

import java.util.Base64;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Caleb Hall
 */
@Component
public class AnalyticsService extends BaseService {

	public String getAuthorization() {
		Base64.Encoder encoder = Base64.getEncoder();

		String authorization =
			_analyticsAuthEmailAddress + ":" + _analyticsAuthPassword;

		return "Basic " + encoder.encodeToString(authorization.getBytes());
	}

	public String provision(JSONObject jsonObject) throws Exception {
		String response = WebClient.builder(
		).baseUrl(
			_analyticsAuthUrl
		).defaultHeader(
			HttpHeaders.AUTHORIZATION, getAuthorization()
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
				"incidentReportEmailAddresses",
				jsonObject.getJSONArray(
					"incidentReportEmailAddresses"
				).toString()
			).with(
				"name", jsonObject.getString("name")
			).with(
				"serverLocation", jsonObject.getString("serverLocation")
			).with(
				"sharedCluster", "false"
			).with(
				"trial", "true"
			).with(
				"ownerEmailAddress", jsonObject.getString("ownerEmailAddress")
			)
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		if (_log.isInfoEnabled()) {
			_log.info("Analytics project created " + response);
		}

		return response;
	}

	private static final Log _log = LogFactory.getLog(AnalyticsService.class);

	@Value("${liferay.marketplace.analytics.auth.email.address}")
	private String _analyticsAuthEmailAddress;

	@Value("${liferay.marketplace.analytics.auth.password}")
	private String _analyticsAuthPassword;

	@Value("${liferay.marketplace.analytics.auth.url}")
	private String _analyticsAuthUrl;

	@Autowired
	private MarketplaceService _marketplaceService;

}