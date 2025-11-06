/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductConsumption;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Pagination;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.time.Duration;

import java.util.Date;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.util.UriComponentsBuilder;

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
			UriComponentsBuilder.fromUriString(
				_analyticsAuthUrl
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
			).toUri());
	}

	@GetMapping("plan/{accountKey}")
	public ResponseEntity<?> getPlan(@PathVariable String accountKey)
		throws Exception {

		try {
			_koroneikiService.getAccount(accountKey);
		}
		catch (Exception exception) {
			_log.error(exception);

			return ResponseEntity.status(
				HttpStatus.BAD_REQUEST
			).body(
				new JSONObject(
				).put(
					"error", "ACCOUNT_NOT_FOUND"
				).toString()
			);
		}

		ProductPurchaseResource productPurchaseResource =
			_koroneikiService.getProductPurchaseResource();

		Page<ProductPurchase> productPurchasePage =
			productPurchaseResource.getProductPurchasesPage(
				"",
				StringBundler.concat(
					"accountKey eq '", accountKey, "' and name in (",
					"'Analytics Cloud Basic', 'Analytics Cloud Business', ",
					"'Analytics Cloud Enterprise')"),
				Pagination.of(1, 20), "");

		if (productPurchasePage.getTotalCount() == 0) {
			return ResponseEntity.ok(
				new JSONObject(
				).put(
					"plan", "Analytics Cloud Basic"
				).toString());
		}

		for (ProductPurchase productPurchase : productPurchasePage.getItems()) {
			ProductPurchase.Status status = productPurchase.getStatus();

			if (!Objects.equals(status.getValue(), "Approved")) {
				continue;
			}

			Date endDate = productPurchase.getEndDate();

			if (productPurchase.getPerpetual() ||
				((endDate != null) && endDate.after(new Date()))) {

				ProductConsumption[] productConsumptions =
					productPurchase.getProductConsumptions();

				if (productConsumptions.length == 0) {
					Product product = productPurchase.getProduct();

					return ResponseEntity.ok(
						new JSONObject(
						).put(
							"key", productPurchase.getKey()
						).put(
							"plan", product.getName()
						).toString());
				}

				return ResponseEntity.status(
					HttpStatus.BAD_REQUEST
				).body(
					new JSONObject(
					).put(
						"error", "WORKSPACE_ALREADY_EXISTS"
					).toString()
				);
			}
		}

		return ResponseEntity.status(
			HttpStatus.BAD_REQUEST
		).body(
			new JSONObject(
			).put(
				"error", "UNABLE_TO_PROVISION"
			).toString()
		);
	}

	@GetMapping("project/{projectId}")
	public String getProject(@PathVariable String projectId) throws Exception {
		return get(
			"Basic " + _analyticsAuthBasic,
			UriComponentsBuilder.fromUriString(
				_analyticsAuthUrl
			).path(
				"/o/faro/main/project/" + projectId
			).build(
			).toUri());
	}

	@GetMapping("project/{projectId}/email-address-domains")
	public String getProjectEmailAddressDomains(@PathVariable String projectId)
		throws Exception {

		return get(
			"Basic " + _analyticsAuthBasic,
			UriComponentsBuilder.fromUriString(
				_analyticsAuthUrl
			).path(
				"/o/faro/main/project/" + projectId + "/email_address_domains"
			).build(
			).toUri());
	}

	@PostMapping("provisioning/{orderId}")
	public String postProvisioning(
			@PathVariable("orderId") long orderId, @RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		String projectJSON = post(
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
				"timeZoneId", jsonObject.optString("timeZoneId")
			).with(
				"trial", "true"
			).with(
				"ownerEmailAddress", jsonObject.getString("ownerEmailAddress")
			).toString(),
			HashMapBuilder.put(
				HttpHeaders.AUTHORIZATION, "Basic " + _analyticsAuthBasic
			).put(
				HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE
			).build(),
			UriComponentsBuilder.fromUriString(
				_analyticsAuthUrl
			).path(
				"/o/faro/main/project/provisioned"
			).build(
			).toUri());

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

	private static final Log _log = LogFactory.getLog(
		AnalyticsRestController.class);

	@Value("${liferay.marketplace.analytics.auth.basic}")
	private String _analyticsAuthBasic;

	@Value("${liferay.marketplace.analytics.auth.token}")
	private String _analyticsAuthToken;

	@Value("${liferay.marketplace.analytics.auth.url}")
	private String _analyticsAuthUrl;

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private MarketplaceService _marketplaceService;

}