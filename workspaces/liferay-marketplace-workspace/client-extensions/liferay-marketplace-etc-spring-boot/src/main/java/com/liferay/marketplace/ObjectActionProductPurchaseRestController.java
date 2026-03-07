/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.model.ProductPurchaseNotificationTemplate;
import com.liferay.marketplace.model.SalesforceOpportunity;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.marketplace.service.SalesforceService;
import com.liferay.marketplace.util.MarketplaceUtil;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keven Leone
 */
@RestController
public class ObjectActionProductPurchaseRestController
	extends BaseRestController {

	@PostMapping("/object/action/product/purchase")
	public void post(@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("POST product purchase " + json);
		}

		JSONObject jsonObject = new JSONObject(json);

		JSONObject commerceOrderJSONObject = jsonObject.getJSONObject(
			"commerceOrder");

		Order order = _marketplaceService.getOrder(
			commerceOrderJSONObject.getLong("id"));

		int paymentStatus = commerceOrderJSONObject.getInt("paymentStatus");

		_postNotificationQueueEntry(order);

		if ((paymentStatus !=
				MarketplaceConstants.ORDER_PAYMENT_STATUS_COMPLETED) &&
			(paymentStatus !=
				MarketplaceConstants.ORDER_PAYMENT_STATUS_NOT_REQUIRED)) {

			if (_log.isInfoEnabled()) {
				_log.info(
					"Skipping POST product purchase for order " +
						commerceOrderJSONObject.getLong("id") +
							" because payment status is not completed");
			}

			return;
		}

		_marketplaceService.updateOrder(
			null, order.getId(), MarketplaceConstants.ORDER_STATUS_PROCESSING);

		String orderTypeExternalReferenceCode =
			order.getOrderTypeExternalReferenceCode();
		Map<String, String> productSpecificationsMap =
			_marketplaceService.getProductSpecificationsMap(
				_marketplaceService.getOrderProductId(order));

		if (Objects.equals(orderTypeExternalReferenceCode, "ADDONS")) {
			_setUpAddOns(jwt, order, productSpecificationsMap);

			_marketplaceService.updateOrder(
				null, order.getId(),
				MarketplaceConstants.ORDER_STATUS_COMPLETED);
		}

		if (Objects.equals(orderTypeExternalReferenceCode, "CLOUD_APP") ||
			Objects.equals(orderTypeExternalReferenceCode, "COMPOSITE_APP") ||
			Objects.equals(
				orderTypeExternalReferenceCode, "LOW_CODE_CONFIGURATION") ||
			Objects.equals(orderTypeExternalReferenceCode, "OTHER")) {

			_marketplaceService.updateOrder(
				null, order.getId(),
				MarketplaceConstants.ORDER_STATUS_COMPLETED);
		}

		if (Objects.equals(
				orderTypeExternalReferenceCode, "CLIENT_EXTENSION") ||
			Objects.equals(
				order.getOrderTypeExternalReferenceCode(), "DXP_APP")) {

			if (Objects.equals(
					productSpecificationsMap.get("price-model"), "Free")) {

				_marketplaceService.updateOrder(
					null, order.getId(),
					MarketplaceConstants.ORDER_STATUS_COMPLETED);

				return;
			}

			_setUpProductEntitlements(
				jwt, productSpecificationsMap.get("license-type"), order);
		}
	}

	private void _postNotificationQueueEntry(Order order) throws Exception {
		OrderItem[] orderItems = order.getOrderItems();

		OrderItem orderItem = orderItems[0];

		if (orderItem == null) {
			return;
		}

		Product product = _marketplaceService.getProductBySkuId(
			orderItem.getSkuId());

		Map<String, String> productSpecificationsMap =
			_marketplaceService.getProductSpecificationsMap(
				product.getProductId());

		ProductPurchaseNotificationTemplate
			productPurchaseNotificationTemplate =
				new ProductPurchaseNotificationTemplate(
					lxcDXPServerProtocol + "://" + lxcDXPMainDomain, order,
					product, productSpecificationsMap);

		if ((Objects.equals(
				order.getPaymentMethod(),
				MarketplaceConstants.ORDER_PAYMENT_METHOD_MONEY_ORDER) &&
			 (order.getPaymentStatus() ==
				 MarketplaceConstants.ORDER_PAYMENT_STATUS_PENDING)) ||
			(Objects.equals(
				order.getPaymentMethod(),
				MarketplaceConstants.ORDER_PAYMENT_METHOD_PAYPAL) &&
			 (order.getPaymentStatus() ==
				 MarketplaceConstants.ORDER_PAYMENT_STATUS_COMPLETED))) {

			_marketplaceService.postNotificationQueueEntry(
				null, "MARKETPLACE-INVOICE-ORDER-SUBMIT-TEMPLATE",
				productPurchaseNotificationTemplate.
					getInvoiceOrderSubmitTemplate());
		}

		if (Objects.equals(
				order.getPaymentMethod(),
				MarketplaceConstants.ORDER_PAYMENT_METHOD_PAYPAL) &&
			(order.getPaymentStatus() ==
				MarketplaceConstants.ORDER_PAYMENT_STATUS_COMPLETED)) {

			_marketplaceService.postNotificationQueueEntry(
				order.getCreatorEmailAddress(),
				"MARKETPLACE-ORDER-CONFIRMATION",
				productPurchaseNotificationTemplate.
					getOrderConfirmationTemplate());
		}

		if (Objects.equals(
				order.getPaymentMethod(),
				MarketplaceConstants.ORDER_PAYMENT_METHOD_MONEY_ORDER) &&
			(order.getPaymentStatus() ==
				MarketplaceConstants.ORDER_PAYMENT_STATUS_COMPLETED)) {

			_marketplaceService.postNotificationQueueEntry(
				order.getCreatorEmailAddress(), "MARKETPLACE-PAYMENT-APPROVED",
				productPurchaseNotificationTemplate.
					getPaymentApprovedTemplate());
		}
	}

	private void _setUpAddOns(
			Jwt jwt, Order order, Map<String, String> productSpecificationsMap)
		throws Exception {

		String solutionType = productSpecificationsMap.get("solution-type");

		if (Objects.equals(solutionType, "analytics")) {
			_setUpAnalyticsAddOn(jwt, order);

			return;
		}

		if (Objects.equals(solutionType, "ai-hub") ||
			Objects.equals(solutionType, "liferay-data-platform")) {

			_setUpCustomAddOn(
				productSpecificationsMap.get("license-type"), order);
		}
	}

	private void _setUpAnalyticsAddOn(Jwt jwt, Order order) throws Exception {
		if (!order.getAccountExternalReferenceCode(
			).startsWith(
				"KOR-"
			)) {

			return;
		}

		Map<String, String> customFields =
			(Map<String, String>)order.getCustomFields();

		JSONObject orderMetadataJSONObject = new JSONObject(
			customFields.getOrDefault("order-metadata", "{}"));

		if (_koroneikiService.hasEntitlement(
				_koroneikiService.getKoroneikiAccount(
					order.getAccountExternalReferenceCode()),
				MarketplaceConstants.KORONEIKI_AC_ENTITLEMENTS)) {

			_koroneikiService.linkProductPurchaseToOpportunity(
				jwt, String.valueOf(order.getId()),
				orderMetadataJSONObject.getString("productPurchaseKey"));

			return;
		}

		for (OrderItem orderItem : order.getOrderItems()) {
			if (!Objects.equals(
					orderItem.getSkuExternalReferenceCode(),
					orderMetadataJSONObject.getString("productKey"))) {

				continue;
			}

			_koroneikiService.postAccountAccountKeyProductPurchase(
				order.getAccountExternalReferenceCode(), jwt, "Subscription",
				MarketplaceUtil.getSkuOptionValue(
					"license-usage-type", orderItem.getOptions()),
				orderItem);
		}
	}

	private void _setUpCustomAddOn(String licenseType, Order order)
		throws Exception {

		OrderItem[] orderItems = order.getOrderItems();

		OrderItem orderItem = orderItems[0];

		if (orderItem == null) {
			return;
		}

		UserAccount userAccount = _marketplaceService.getUserAccount(
			order.getCreatorEmailAddress());

		Product product = _marketplaceService.getProductBySkuId(
			orderItem.getSkuId());

		_salesforceService.postSalesforceOpportunity(
			new SalesforceOpportunity(
				licenseType, order, orderItem, product, userAccount));
	}

	private void _setUpProductEntitlements(
			Jwt jwt, String licenseType, Order order)
		throws Exception {

		String accountExternalReferenceCode =
			order.getAccountExternalReferenceCode();

		if (!accountExternalReferenceCode.startsWith("KOR-")) {
			AccountResource accountResource =
				_marketplaceService.getAccountResource();

			Account account = accountResource.getAccount(order.getAccountId());

			accountResource.patchAccount(
				account.getId(),
				new Account() {
					{
						setExternalReferenceCode(
							() -> _koroneikiService.postKoroneikiAccount(
								account, jwt
							).getKey());
					}
				});
		}

		try {
			for (OrderItem orderItem : order.getOrderItems()) {
				_koroneikiService.postAccountAccountKeyProductPurchase(
					accountExternalReferenceCode, jwt, licenseType,
					MarketplaceUtil.getSkuOptionValue(
						"license-usage-type", orderItem.getOptions()),
					orderItem);
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
		ObjectActionProductPurchaseRestController.class);

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private MarketplaceService _marketplaceService;

	@Autowired
	private SalesforceService _salesforceService;

}