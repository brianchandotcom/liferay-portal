/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.SkuResource;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
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
@RequestMapping("/marketplace")
@RestController
public class MarketplaceRestController extends BaseRestController {

	@PostMapping("product/purchase")
	public void postProductPurchase(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("POST product purchase " + json);
		}

		JSONObject jsonObject = new JSONObject(json);

		JSONObject commerceOrderJSONObject = jsonObject.getJSONObject(
			"commerceOrder");

		if (commerceOrderJSONObject.getInt("paymentStatus") !=
				MarketplaceConstants.ORDER_PAYMENT_STATUS_COMPLETED) {

			if (_log.isInfoEnabled()) {
				_log.info(
					"Skipping POST product purchase for order " +
						commerceOrderJSONObject.getLong("id") +
							" because payment status is not completed");
			}

			return;
		}

		Order order = _marketplaceService.getOrder(
			commerceOrderJSONObject.getLong("id"));

		_marketplaceService.updateOrder(
			null, order.getId(), MarketplaceConstants.ORDER_STATUS_PROCESSING);

		Page<OrderItem> orderItemPage =
			_marketplaceService.getOrderItemResource(
			).getOrderIdOrderItemsPage(
				order.getId(),
				com.liferay.headless.commerce.admin.order.client.pagination.
					Pagination.of(1, 10)
			);

		if (Objects.equals(
				order.getOrderTypeExternalReferenceCode(), "CLOUDAPP")) {

			_setUpCloudProductPurchase(order, orderItemPage);
		}

		if (Objects.equals(
				order.getOrderTypeExternalReferenceCode(), "DXPAPP")) {

			_setUpDxpProductPurchase(jwt, order, orderItemPage);
		}
	}

	private void _setUpCloudProductPurchase(
			Order order, Page<OrderItem> orderItemPage)
		throws Exception {

		JSONArray jsonArray = new JSONArray();

		for (OrderItem orderItem : orderItemPage.getItems()) {
			jsonArray.put(
				new JSONObject(
				).put(
					"deployments", new JSONArray()
				).put(
					"orderItemId", orderItem.getId()
				).put(
					"sku", orderItem.getSku()
				).put(
					"shippedQuantity", 0
				).put(
					"quantity",
					orderItem.getQuantity(
					).intValue()
				));
		}

		Map<String, String> customFields =
			(Map<String, String>)order.getCustomFields();

		customFields.put("cloud-provisioning", jsonArray.toString());

		_marketplaceService.updateOrder(
			customFields, order.getId(),
			MarketplaceConstants.ORDER_STATUS_COMPLETED);
	}

	private void _setUpDxpProductPurchase(
			Jwt jwt, Order order, Page<OrderItem> orderItemPage)
		throws Exception {

		SkuResource skuResource = _marketplaceService.getSkuResource();

		Map<String, String> productSpecificationsMap =
			_marketplaceService.getProductSpecificationsMap(
				skuResource.getSku(
					orderItemPage.fetchFirstItem(
					).getSkuId()
				).getProductId());

		if (Objects.equals(
				productSpecificationsMap.get("price-model"), "Free")) {

			_marketplaceService.updateOrder(
				null, order.getId(),
				MarketplaceConstants.ORDER_STATUS_COMPLETED);

			return;
		}

		AccountResource accountResource =
			_marketplaceService.getAccountResource();

		Account account = accountResource.getAccount(order.getAccountId());

		if (!account.getExternalReferenceCode(
			).startsWith(
				"KOR-"
			)) {

			account.setExternalReferenceCode(
				() -> _koroneikiService.postKoroneikiAccount(
					account, jwt
				).getKey());

			accountResource.patchAccount(account.getId(), account);
		}

		try {
			for (OrderItem orderItem : orderItemPage.getItems()) {
				_koroneikiService.postAccountAccountKeyProductPurchase(
					account, jwt,
					_marketplaceService.getSkuOptionValue(
						"dxp-license-usage-type", orderItem.getOptions()),
					orderItem, productSpecificationsMap);
			}

			_marketplaceService.updateOrder(
				null, order.getId(),
				MarketplaceConstants.ORDER_STATUS_COMPLETED);
		}
		catch (Exception exception) {
			_log.error("Unable to create account product purchase", exception);
		}
	}

	private static final Log _log = LogFactory.getLog(
		MarketplaceRestController.class);

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private MarketplaceService _marketplaceService;

}