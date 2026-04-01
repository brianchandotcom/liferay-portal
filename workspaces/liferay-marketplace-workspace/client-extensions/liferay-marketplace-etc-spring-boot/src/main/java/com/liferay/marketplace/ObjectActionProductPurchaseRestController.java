/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Catalog;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.model.SalesforceOpportunity;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.marketplace.service.SalesforceService;
import com.liferay.marketplace.util.MarketplaceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapBuilder;

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

			_koroneikiService.postAccountProductPurchases(
				jwt, productSpecificationsMap.get("license-type"), order);

			_marketplaceService.updateOrder(
				null, order.getId(),
				MarketplaceConstants.ORDER_STATUS_COMPLETED);
		}
	}

	private String _getExchangeRate(Order order) {
		Map<String, String> customFields =
			(Map<String, String>)order.getCustomFields();

		JSONObject orderMetadataJSONObject = new JSONObject(
			customFields.getOrDefault("order-metadata", "{}"));

		if (!Objects.equals(order.getCurrencyCode(), "USD") ||
			!orderMetadataJSONObject.has("exchangeRate")) {

			return "Not applicable";
		}

		double exchangeRate = orderMetadataJSONObject.getDouble("exchangeRate");

		return "1 USD = " + String.format("%.5f", exchangeRate) + " EUR";
	}

	private Map<String, String> _getInvoiceOrderMap(
			Order order, Product product,
			Map<String, String> productSpecificationsMap)
		throws Exception {

		Account account = order.getAccount();

		BillingAddress billingAddress = order.getBillingAddress();

		return HashMapBuilder.put(
			"[%ACCOUNT_ID%]", String.valueOf(account.getId())
		).put(
			"[%ACCOUNT_NAME%]", account.getName()
		).put(
			"[%BILLING_ADDRESS_FORMATTED%]",
			String.join(
				", ", billingAddress.getStreet1(), billingAddress.getCity(),
				billingAddress.getRegionISOCode(),
				billingAddress.getCountryISOCode())
		).put(
			"[%BILLING_ADDRESS_NAME%]", billingAddress.getName()
		).put(
			"[%BILLING_ADDRESS_PHONE%]", billingAddress.getPhoneNumber()
		).put(
			"[%CATALOG_NAME%]",
			() -> {
				Catalog catalog = product.getCatalog();

				return catalog.getName();
			}
		).put(
			"[%EMAIL_ADDRESS%]", order.getCreatorEmailAddress()
		).put(
			"[%EXCHANGE_RATE%]", _getExchangeRate(order)
		).put(
			"[%LICENSE_TYPE%]", productSpecificationsMap.get("license-type")
		).put(
			"[%ORDER_DATE%]", MarketplaceUtil.format(order.getCreateDate())
		).put(
			"[%ORDER_ID%]", String.valueOf(order.getId())
		).put(
			"[%ORDER_PAYMENT_METHOD%]",
			MarketplaceConstants.getOrderPaymentMethodLabel(
				order.getPaymentMethod())
		).put(
			"[%ORDER_STATUS%]",
			MarketplaceConstants.getOrderStatusLabel(order.getOrderStatus())
		).put(
			"[%PAYMENT_TERM_DESCRIPTION%]", order.getPaymentTermDescription()
		).put(
			"[%PRODUCT_NAME%]",
			product.getName(
			).get(
				"en_US"
			)
		).put(
			"[%PRODUCT_THUMBNAIL%]",
			_marketplaceService.getProductThumbnail(product)
		).put(
			"[%PRODUCT_TYPE%]",
			productSpecificationsMap.get(
				"type"
			).replace(
				"-", " "
			)
		).put(
			"[%SUBSCRIPTION_EXPIRATION_DATE%]",
			() -> {
				OrderItem[] orderItems = order.getOrderItems();

				OrderItem orderItem = orderItems[0];

				return MarketplaceUtil.format(
					MarketplaceUtil.getOrderPurchaseEndDate(
						productSpecificationsMap.get("license-type"),
						MarketplaceUtil.getSkuOptionValue(
							"license-usage-type", orderItem.getOptions())));
			}
		).put(
			"[%SUBSCRIPTION_STARTING_DATE%]",
			MarketplaceUtil.format(order.getCreateDate())
		).put(
			"[%SUBSCRIPTION_TYPE%]",
			productSpecificationsMap.get("license-type")
		).put(
			"[%SUBTOTAL_FORMATTED%]", order.getSubtotalFormatted()
		).put(
			"[%TAX_AMOUNT_FORMATTED%]", order.getTaxAmountFormatted()
		).put(
			"[%TAX_ID%]", account.getTaxId()
		).put(
			"[%TOTAL_FORMATTED%]", order.getTotalFormatted()
		).build();
	}

	private String _getOrderConfirmationDescription(
		Order order, Map<String, String> productSpecificationsMap) {

		String solutionType = productSpecificationsMap.get("solution-type");

		if (Objects.equals(
				order.getOrderTypeExternalReferenceCode(), "DXP_APP")) {

			String priceModel = productSpecificationsMap.get("price-model");

			if (Objects.equals(priceModel, "Free")) {
				return StringBundler.concat(
					"<p>Your app is ready for download.</p> <p>To find your ",
					"app download, find your Order ID and choose Manage, then ",
					"Download LPKG.</p>");
			}

			return StringBundler.concat(
				"<p>Your app is ready for download.</p>",
				"<p>To access your download, find your Order ID and select ",
				"Manage, then <b>Download LPKG.</b> Please note that a ",
				"<b>valid license is also required to activate</b> and use ",
				"the application.</p>");
		}
		else if (Objects.equals(solutionType, "liferay-data-platform")) {
			return StringBundler.concat(
				"<p>Your workspace is being created now!</p>",
				"<p>Click the button below to go to your dashboard and check",
				"the status of your environment. You can start using it as ",
				"soon as it is ready.</p>");
		}

		return "";
	}

	private Map<String, String> _getOrderConfirmationMap(
			Order order, Product product,
			Map<String, String> productSpecificationsMap)
		throws Exception {

		Catalog catalog = product.getCatalog();

		return new HashMapBuilder<>().put(
			"[%CATALOG_NAME%]", catalog.getName()
		).put(
			"[%CREATOR_EMAIL_ADDRESS%]", order.getCreatorEmailAddress()
		).put(
			"[%CTA_TEXT%]", "Go to Dashboard"
		).put(
			"[%DESCRIPTION%]",
			_getOrderConfirmationDescription(order, productSpecificationsMap)
		).put(
			"[%ORDER_ID%]", String.valueOf(order.getId())
		).put(
			"[%PRODUCT_NAME%]",
			product.getName(
			).get(
				"en_US"
			)
		).put(
			"[%PRODUCT_THUMBNAIL%]",
			_marketplaceService.getProductThumbnail(product)
		).put(
			"[%TOTAL_FORMATTED%]", order.getTotalFormatted()
		).build();
	}

	private Map<String, String> _getPaymentApprovedDescriptionMap(
		Map<String, String> productSpecificationsMap) {

		String solutionType = productSpecificationsMap.get("solution-type");

		if (Objects.equals(solutionType, "liferay-data-platform")) {
			return HashMapBuilder.put(
				"CTA_TEXT", "Launch LDP"
			).put(
				"DESCRIPTION",
				StringBundler.concat(
					"<p>You’re all set \uD83D\uDE80 You <b>can start using ",
					"all the premium features </b> of your Liferay Data ",
					"Platform right away. Click the button below to access ",
					"your LDP and enjoy the full experience.</p>")
			).build();
		}

		return HashMapBuilder.put(
			"CTA_TEXT", "Go to Dashboard"
		).put(
			"DESCRIPTION",
			StringBundler.concat(
				"<p>You’re all set \uD83C\uDF89 Your purchase has been ",
				"successfully processed and your app is now available in your ",
				"account.</p> <p>You can manage your subscription, access ",
				"features, and configure your app anytime from your ",
				"dashboard. Click the button below to get started.</p>")
		).build();
	}

	private Map<String, String> _getPaymentApprovedMap(
			Order order, Product product,
			Map<String, String> productSpecificationsMap)
		throws Exception {

		return HashMapBuilder.put(
			"[%CATALOG_NAME%]",
			() -> {
				Catalog catalog = product.getCatalog();

				return catalog.getName();
			}
		).put(
			"[%CREATOR_EMAIL_ADDRESS%]", order.getCreatorEmailAddress()
		).put(
			"[%ORDER_ID%]", String.valueOf(order.getId())
		).put(
			"[%PRODUCT_NAME%]",
			product.getName(
			).get(
				"en_US"
			)
		).put(
			"[%PRODUCT_THUMBNAIL%]",
			_marketplaceService.getProductThumbnail(product)
		).put(
			"[%SUBTOTAL_FORMATTED%]", order.getSubtotalFormatted()
		).put(
			"[%TAX_AMOUNT_FORMATTED%]", order.getTaxAmountFormatted()
		).put(
			"[%TOTAL_FORMATTED%]", order.getTotalFormatted()
		).putAll(
			_getPaymentApprovedDescriptionMap(productSpecificationsMap)
		).build();
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
				null, "MARKETPLACE-INVOICE-ORDER",
				_getInvoiceOrderMap(order, product, productSpecificationsMap));
		}

		if (Objects.equals(
				order.getPaymentMethod(),
				MarketplaceConstants.ORDER_PAYMENT_METHOD_PAYPAL) &&
			(order.getPaymentStatus() ==
				MarketplaceConstants.ORDER_PAYMENT_STATUS_COMPLETED)) {

			_marketplaceService.postNotificationQueueEntry(
				order.getCreatorEmailAddress(),
				"MARKETPLACE-ORDER-CONFIRMATION",
				_getOrderConfirmationMap(
					order, product, productSpecificationsMap));
		}

		if (Objects.equals(
				order.getPaymentMethod(),
				MarketplaceConstants.ORDER_PAYMENT_METHOD_MONEY_ORDER) &&
			(order.getPaymentStatus() ==
				MarketplaceConstants.ORDER_PAYMENT_STATUS_COMPLETED)) {

			_marketplaceService.postNotificationQueueEntry(
				order.getCreatorEmailAddress(), "MARKETPLACE-PAYMENT-APPROVED",
				_getPaymentApprovedMap(
					order, product, productSpecificationsMap));
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

		BillingAddress billingAddress = order.getBillingAddress();

		JSONObject jsonObject = _salesforceService.postSalesforceOpportunity(
			new SalesforceOpportunity(
				_marketplaceService.getCountryByA2(
					billingAddress.getCountryISOCode()),
				licenseType, order,
				_marketplaceService.getUserAccount(
					order.getCreatorEmailAddress())));

		if (!jsonObject.optBoolean("success")) {
			_log.error(
				StringBundler.concat(
					"Unable to create Salesforce opportunity for order ",
					order.getId(), " message: ",
					jsonObject.optString("message"), " error: ",
					jsonObject.optString("error")));

			return;
		}

		OrderResource orderResource = _marketplaceService.getOrderResource();

		orderResource.patchOrder(
			order.getId(),
			new Order() {
				{
					setExternalReferenceCode(
						() -> jsonObject.getJSONObject(
							"data"
						).getString(
							"opportunityId"
						));
				}
			});
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