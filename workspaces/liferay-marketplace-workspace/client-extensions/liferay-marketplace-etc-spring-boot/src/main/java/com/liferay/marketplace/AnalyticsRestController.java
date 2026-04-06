/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.AnalyticsService;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductConsumption;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Pagination;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseResource;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductResource;
import com.liferay.petra.string.StringBundler;

import java.time.Duration;

import java.util.Date;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

	@GetMapping("plan/{accountKey}")
	public ResponseEntity<?> getPlan(@PathVariable String accountKey)
		throws Exception {

		try {
			if (!_koroneikiService.hasEntitlement(
					_koroneikiService.getKoroneikiAccount(accountKey),
					MarketplaceConstants.KORONEIKI_DXP_ENTITLEMENTS)) {

				throw new Exception(
					"DXP entitlements not found for account " + accountKey);
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			return ResponseEntity.status(
				HttpStatus.BAD_REQUEST
			).body(
				new JSONObject(
				).put(
					"error", "ACCOUNT_OR_ENTITLEMENT_NOT_FOUND"
				).toString()
			);
		}

		ProductPurchaseResource productPurchaseResource =
			_koroneikiService.getProductPurchaseResource();

		Page<ProductPurchase> productPurchasesPage =
			productPurchaseResource.getProductPurchasesPage(
				"",
				StringBundler.concat(
					"accountKey eq '", accountKey, "' and name in (",
					"'Analytics Cloud Basic', 'Analytics Cloud Business', ",
					"'Analytics Cloud Enterprise')"),
				Pagination.of(1, 20), "");

		if (productPurchasesPage.getTotalCount() == 0) {
			ProductResource productResource =
				_koroneikiService.getProductResource();

			Product product = productResource.getProductByNameProductName(
				"Analytics%20Cloud%20Basic");

			return ResponseEntity.ok(
				new JSONObject(
				).put(
					"productKey", product.getKey()
				).put(
					"productName", product.getName()
				).toString());
		}

		for (ProductPurchase productPurchase :
				productPurchasesPage.getItems()) {

			ProductPurchase.Status status = productPurchase.getStatus();

			if (!Objects.equals(status.getValue(), "Approved")) {
				continue;
			}

			Date endDate = productPurchase.getEndDate();

			if (productPurchase.getPerpetual() ||
				((endDate != null) && endDate.after(new Date()))) {

				Product product = productPurchase.getProduct();

				ProductConsumption[] productConsumptions =
					productPurchase.getProductConsumptions();

				if (productConsumptions.length == 0) {
					return ResponseEntity.ok(
						new JSONObject(
						).put(
							"productKey", product.getKey()
						).put(
							"productName", product.getName()
						).put(
							"productPurchaseKey", productPurchase.getKey()
						).toString());
				}

				return ResponseEntity.status(
					HttpStatus.BAD_REQUEST
				).body(
					new JSONObject(
					).put(
						"error", "WORKSPACE_ALREADY_EXISTS"
					).put(
						"productName", product.getName()
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
			_analyticsService.getAuthorization(),
			UriComponentsBuilder.fromUriString(
				_analyticsAuthUrl
			).path(
				"/o/faro/main/project/" + projectId
			).build(
			).toUri());
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
							StringBundler.concat(
								"Retrying ", clientRequest.url(),
								retrySignal.totalRetries() + 1));
					}
				}
			)
		);
	}

	private static final Log _log = LogFactory.getLog(
		AnalyticsRestController.class);

	@Value("${liferay.marketplace.analytics.auth.url}")
	private String _analyticsAuthUrl;

	@Autowired
	private AnalyticsService _analyticsService;

	@Autowired
	private KoroneikiService _koroneikiService;

}