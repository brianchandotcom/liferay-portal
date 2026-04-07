/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Base64;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
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

	public void provision(JSONObject jsonObject, long orderId)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Provisioning order " + orderId);
		}

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
				new JSONArray(
					jsonObject.getString("incidentReportEmailAddresses")
				).toString()
			).with(
				"name", jsonObject.getString("name")
			).with(
				"serverLocation",
				_getServerLocation(jsonObject.getString("serverLocation"))
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

		if (response == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Analytics project created for order " + orderId);
		}

		Order order = _marketplaceService.getOrder(orderId);

		_marketplaceService.updateOrder(
			HashMapBuilder.put(
				"order-metadata",
				new JSONObject(
					GetterUtil.get(
						order.getCustomFields(
						).get(
							"order-metadata"
						),
						"{}")
				).put(
					"analyticsProject", new JSONObject(response)
				).toString()
			).build(),
			orderId, order.getOrderStatus());
	}

	private String _getServerLocation(String serverLocation) {
		if (Validator.isBlank(serverLocation)) {
			return _SERVER_LOCATION;
		}

		return serverLocation;
	}

	private static final String _SERVER_LOCATION = "us-west1-ac-uat-c1";

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