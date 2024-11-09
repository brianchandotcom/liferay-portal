/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.time.Duration;

import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import reactor.util.retry.Retry;

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

	@GetMapping("project/{projectId}/data-source")
	public String getProjectDataSource(
			@RequestParam(defaultValue = "1", required = false) int cur,
			@RequestParam(defaultValue = "20", required = false) int delta,
			@PathVariable String projectId)
		throws Exception {

		return get(
			"Basic " + _analyticsAuthBasic,
			_defaultUriBuilderFactory.builder(
			).path(
				"/o/faro/contacts/" + projectId + "/data_source"
			).queryParam(
				"cur", cur
			).queryParam(
				"delta", delta
			).build(
			).toString());
	}

	@GetMapping("project/{projectId}/data-source/token")
	public String getProjectDataSourceToken(@PathVariable String projectId)
		throws Exception {

		return _getWebClient(
			"Basic " + _analyticsAuthBasic
		).get(
		).uri(
			"/o/faro/contacts/" + projectId + "/data_source/token"
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	@GetMapping("project/{projectId}/email-address-domains")
	public String getProjectEmailAddressDomains(@PathVariable String projectId)
		throws Exception {

		return get(
			"Basic " + _analyticsAuthBasic,
			"/o/faro/main/project/" + projectId + "/email_address_domains");
	}

	@PostMapping("provisioning/{orderId}")
	public String postProvisioning(
			@PathVariable("orderId") long orderId, @RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		String projectJSON = _getWebClient(
			"Basic " + _analyticsAuthBasic
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
				"serverLocation", "us-west1-ac-uat-c1"
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

		if (_log.isInfoEnabled()) {
			_log.info("Analytics project created for order " + orderId);
		}

		Order order = _marketplaceService.getOrder(orderId);

		if (Objects.equals(
				order.getOrderStatus(),
				MarketplaceConstants.ORDER_STATUS_OPEN)) {

			_marketplaceService.updateOrder(
				null, orderId, MarketplaceConstants.ORDER_STATUS_PENDING);
		}

		_marketplaceService.updateOrder(
			null, orderId, MarketplaceConstants.ORDER_STATUS_PROCESSING);

		_marketplaceService.updateOrder(
			HashMapBuilder.put(
				"analytics-group-id",
				String.valueOf(
					new JSONObject(
						projectJSON
					).getLong(
						"groupId"
					))
			).build(),
			orderId, MarketplaceConstants.ORDER_STATUS_COMPLETED);

		return projectJSON;
	}

	@Override
	protected String getWebClientBaseURL() {
		return _analyticsAuthUrl;
	}

	@Override
	protected ExchangeFilterFunction getWebClientExchangeFilterFunction() {
		return (clientRequest, exchangeFunction) -> exchangeFunction.exchange(
			clientRequest
		).retryWhen(
			Retry.fixedDelay(
				3, Duration.ofSeconds(5)
			).doBeforeRetry(
				retrySignal -> {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Retry attempt " + retrySignal.totalRetries() + 1);
					}
				}
			)
		);
	}

	private WebClient _getWebClient(String authorization) {
		return WebClient.builder(
		).baseUrl(
			_analyticsAuthUrl
		).defaultHeader(
			HttpHeaders.AUTHORIZATION, authorization
		).filter(
			getWebClientExchangeFilterFunction()
		).build();
	}

	private static final Log _log = LogFactory.getLog(
		AnalyticsRestController.class);

	@Value("${liferay.marketplace.analytics.auth.basic}")
	private String _analyticsAuthBasic;

	@Value("${liferay.marketplace.analytics.auth.token}")
	private String _analyticsAuthToken;

	@Value("${liferay.marketplace.analytics.auth.url}")
	private String _analyticsAuthUrl;

	private final DefaultUriBuilderFactory _defaultUriBuilderFactory =
		new DefaultUriBuilderFactory();

	@Autowired
	private MarketplaceService _marketplaceService;

}