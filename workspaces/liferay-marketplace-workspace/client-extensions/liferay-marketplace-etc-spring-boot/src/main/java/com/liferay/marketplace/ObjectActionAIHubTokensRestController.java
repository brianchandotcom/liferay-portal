/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.model.SalesforceOpportunity;
import com.liferay.marketplace.service.AIHubService;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.marketplace.service.SalesforceService;
import com.liferay.marketplace.util.MarketplaceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keven Leone
 */
@RequestMapping("/object/action/ai/hub/tokens")
@RestController
public class ObjectActionAIHubTokensRestController extends BaseRestController {

	@PostMapping
	public void post(@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject commerceOrderJSONObject = jsonObject.getJSONObject(
			"commerceOrder");

		if ((commerceOrderJSONObject.getInt("orderStatus") ==
				MarketplaceConstants.ORDER_STATUS_COMPLETED) ||
			(commerceOrderJSONObject.getInt("paymentStatus") !=
				MarketplaceConstants.ORDER_PAYMENT_STATUS_COMPLETED)) {

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Skipping POST AI Hub token for order ",
						commerceOrderJSONObject.getLong("id"),
						" because order or payment status is not ",
						"completed"));
			}

			return;
		}

		Order order = _marketplaceService.getOrder(
			commerceOrderJSONObject.getLong("id"));

		JSONObject aiHubApplicationJSONObject =
			_marketplaceService.getAIHubApplicationJSONObject(
				"AI-HUB-" + order.getAccountExternalReferenceCode());

		if (aiHubApplicationJSONObject == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"AI Hub application was not found for order " +
						order.getId());
			}

			return;
		}

		OrderItem[] orderItems = order.getOrderItems();

		if (ArrayUtil.isEmpty(orderItems)) {
			return;
		}

		OrderItem orderItem = orderItems[0];

		String skuOptionValue = MarketplaceUtil.getSkuOptionValue(
			"license-usage-type", orderItem.getOptions());

		if (skuOptionValue == null) {
			return;
		}

		String tokensAmount = StringUtil.removeSubstring(
			skuOptionValue, "-lr-tokens");

		_aiHubService.purchaseQuotaPrepaidBlock(
			aiHubApplicationJSONObject.getInt("accountEntryId"),
			new JSONObject(
			).put(
				"size", Long.valueOf(tokensAmount)
			).put(
				"transactionId", order.getId()
			));

		_marketplaceService.completeOrder(
			null, order.getId(), MarketplaceConstants.ORDER_STATUS_COMPLETED);

		_setUpSalesforceOpportunity(
			aiHubApplicationJSONObject, order, orderItem);
	}

	private String _getSalesforceProjectId(
		JSONObject aiHubApplicationJSONObject) {

		JSONObject orderMetadataJSONObject = MarketplaceUtil.getOrderMetadata(
			Order.toDTO(
				String.valueOf(
					aiHubApplicationJSONObject.getJSONObject(
						"orderToAIHubApplication"))));

		return orderMetadataJSONObject.getString("salesforceProjectId");
	}

	private void _setUpSalesforceOpportunity(
			JSONObject aiHubApplicationJSONObject, Order order,
			OrderItem orderItem)
		throws Exception {

		order.setCustomFields(
			() -> HashMapBuilder.put(
				"order-metadata",
				new JSONObject(
				).put(
					"salesforceProjectId",
					_getSalesforceProjectId(aiHubApplicationJSONObject)
				).toString()
			).build());

		BillingAddress billingAddress = order.getBillingAddress();

		SalesforceOpportunity salesforceOpportunity = new SalesforceOpportunity(
			_marketplaceService.getCountryByA2(
				billingAddress.getCountryISOCode()),
			"Subscription", order,
			_marketplaceService.getSku(orderItem.getSkuId()),
			_marketplaceService.getUserAccount(order.getCreatorEmailAddress()));

		JSONObject salesforceOpportunityJSONObject =
			_salesforceService.postSalesforceOpportunity(salesforceOpportunity);

		if (salesforceOpportunityJSONObject == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to post Salesforce opportunity");
			}

			return;
		}

		OrderResource orderResource = _marketplaceService.getOrderResource();

		orderResource.patchOrder(
			order.getId(),
			new Order() {
				{
					setExternalReferenceCode(
						() -> salesforceOpportunityJSONObject.getJSONObject(
							"data"
						).getString(
							"opportunityId"
						));
				}
			});
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionAIHubTokensRestController.class);

	@Autowired
	private AIHubService _aiHubService;

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private MarketplaceService _marketplaceService;

	@Autowired
	private SalesforceService _salesforceService;

}