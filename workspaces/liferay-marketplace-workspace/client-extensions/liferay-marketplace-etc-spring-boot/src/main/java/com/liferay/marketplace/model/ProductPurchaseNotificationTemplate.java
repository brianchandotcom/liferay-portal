/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Catalog;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.util.MarketplaceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URL;

import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class ProductPurchaseNotificationTemplate {

	public ProductPurchaseNotificationTemplate(
		String liferayHost, Order order, Product product,
		Map<String, String> productSpecificationsMap) {

		_liferayHost = liferayHost;
		_order = order;
		_product = product;
		_productSpecificationsMap = productSpecificationsMap;
	}

	public Map<String, String> getInvoiceOrderSubmitTemplate()
		throws Exception {

		Account account = _order.getAccount();

		BillingAddress billingAddress = _order.getBillingAddress();

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
				Catalog catalog = _product.getCatalog();

				return catalog.getName();
			}
		).put(
			"[%EMAIL_ADDRESS%]", _order.getCreatorEmailAddress()
		).put(
			"[%EXCHANGE_RATE%]", _getExchangeRate()
		).put(
			"[%LICENSE_TYPE%]", _productSpecificationsMap.get("license-type")
		).put(
			"[%ORDER_DATE%]", MarketplaceUtil.format(_order.getCreateDate())
		).put(
			"[%ORDER_ID%]", String.valueOf(_order.getId())
		).put(
			"[%ORDER_PAYMENT_METHOD%]",
			MarketplaceConstants.getOrderPaymentMethodLabel(
				_order.getPaymentMethod())
		).put(
			"[%ORDER_STATUS%]",
			MarketplaceConstants.getOrderStatusLabel(_order.getOrderStatus())
		).put(
			"[%PAYMENT_TERM_DESCRIPTION%]", _order.getPaymentTermDescription()
		).put(
			"[%PRODUCT_NAME%]",
			_product.getName(
			).get(
				"en_US"
			)
		).put(
			"[%PRODUCT_THUMBNAIL%]", _getProductThumbnail()
		).put(
			"[%PRODUCT_TYPE%]",
			_productSpecificationsMap.get(
				"type"
			).replace(
				"-", " "
			)
		).put(
			"[%SUBSCRIPTION_EXPIRATION_DATE%]",
			() -> {
				OrderItem[] orderItems = _order.getOrderItems();

				OrderItem orderItem = orderItems[0];

				return MarketplaceUtil.format(
					MarketplaceUtil.getOrderPurchaseEndDate(
						_productSpecificationsMap.get("license-type"),
						MarketplaceUtil.getSkuOptionValue(
							"license-usage-type", orderItem.getOptions())));
			}
		).put(
			"[%SUBSCRIPTION_STARTING_DATE%]",
			MarketplaceUtil.format(_order.getCreateDate())
		).put(
			"[%SUBSCRIPTION_TYPE%]",
			_productSpecificationsMap.get("license-type")
		).put(
			"[%SUBTOTAL_FORMATTED%]", _order.getSubtotalFormatted()
		).put(
			"[%TAX_AMOUNT_FORMATTED%]", _order.getTaxAmountFormatted()
		).put(
			"[%TAX_ID%]", account.getTaxId()
		).put(
			"[%TOTAL_FORMATTED%]", _order.getTotalFormatted()
		).build();
	}

	public Map<String, String> getOrderConfirmationTemplate() throws Exception {
		Catalog catalog = _product.getCatalog();

		return new HashMapBuilder<>().put(
			"[%CATALOG_NAME%]", catalog.getName()
		).put(
			"[%CREATOR_EMAIL_ADDRESS%]", _order.getCreatorEmailAddress()
		).put(
			"[%CTA_TEXT%]", "Go to Dashboard"
		).put(
			"[%DESCRIPTION%]", _getOrderConfirmationDescription()
		).put(
			"[%ORDER_ID%]", String.valueOf(_order.getId())
		).put(
			"[%PRODUCT_NAME%]",
			_product.getName(
			).get(
				"en_US"
			)
		).put(
			"[%PRODUCT_THUMBNAIL%]", _getProductThumbnail()
		).put(
			"[%TOTAL_FORMATTED%]", _order.getTotalFormatted()
		).build();
	}

	public Map<String, String> getPaymentApprovedTemplate() throws Exception {
		return HashMapBuilder.put(
			"[%CATALOG_NAME%]",
			() -> {
				Catalog catalog = _product.getCatalog();

				return catalog.getName();
			}
		).put(
			"[%CREATOR_EMAIL_ADDRESS%]", _order.getCreatorEmailAddress()
		).put(
			"[%ORDER_ID%]", String.valueOf(_order.getId())
		).put(
			"[%PRODUCT_NAME%]",
			_product.getName(
			).get(
				"en_US"
			)
		).put(
			"[%PRODUCT_THUMBNAIL%]", _getProductThumbnail()
		).put(
			"[%SUBTOTAL_FORMATTED%]", _order.getSubtotalFormatted()
		).put(
			"[%TAX_AMOUNT_FORMATTED%]", _order.getTaxAmountFormatted()
		).put(
			"[%TOTAL_FORMATTED%]", _order.getTotalFormatted()
		).putAll(
			_getPaymentApprovedDescriptionMap()
		).build();
	}

	private String _getExchangeRate() {
		Map<String, String> customFields =
			(Map<String, String>)_order.getCustomFields();

		JSONObject orderMetadataJSONObject = new JSONObject(
			customFields.getOrDefault("order-metadata", "{}"));

		if (!Objects.equals(_order.getCurrencyCode(), "USD") ||
			!orderMetadataJSONObject.has("exchangeRate")) {

			return "Not applicable";
		}

		double exchangeRate = orderMetadataJSONObject.getDouble("exchangeRate");

		return "1 USD = " + String.format("%.5f", exchangeRate) + " EUR";
	}

	private String _getOrderConfirmationDescription() {
		String solutionType = _productSpecificationsMap.get("solution-type");

		if (Objects.equals(
				_order.getOrderTypeExternalReferenceCode(), "DXP_APP")) {

			String priceModel = _productSpecificationsMap.get("price-model");

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

	private Map<String, String> _getPaymentApprovedDescriptionMap() {
		String solutionType = _productSpecificationsMap.get("solution-type");

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

	private String _getProductThumbnail() throws Exception {
		return new URL(
			_liferayHost + _product.getThumbnail()
		).toString(
		).replaceAll(
			"(?<=accounts/)-?\\d+(?=/images)", "-1"
		);
	}

	private final String _liferayHost;
	private final Order _order;
	private final Product _product;
	private final Map<String, String> _productSpecificationsMap;

}