/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model;

import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.ShippingAddress;
import com.liferay.marketplace.util.MarketplaceUtil;

import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class SalesforceOpportunity {

	public SalesforceOpportunity(
		String licenseType, Order order, OrderItem orderItem, Product product,
		UserAccount userAccount) {

		_licenseType = licenseType;
		_order = order;
		_orderItem = orderItem;
		_product = product;
		_userAccount = userAccount;
	}

	public String toString() {
		Account account = _order.getAccount();

		Map<String, String> customFields =
			(Map<String, String>)_order.getCustomFields();

		JSONObject orderMetadataJSONObject = new JSONObject(
			customFields.getOrDefault("order-metadata", "{}"));

		ShippingAddress shippingAddress = _order.getShippingAddress();

		JSONObject jsonObject = new JSONObject(
		).put(
			"accountId", account.getId()
		).put(
			"accountName", account.getName()
		).put(
			"billingAddress", _getBillingAddressJSONObject()
		).put(
			"closeDate", _format(_order.getCreateDate())
		).put(
			"marketplaceDealId", _order.getId()
		).put(
			"opportunityCurrency", _order.getCurrencyCode()
		).put(
			"opportunityOwner", "Marketplace Integration"
		).put(
			"primaryContact", _getPrimaryContactJSONObject()
		).put(
			"product", _getProductJSONObject()
		).put(
			"projectId",
			orderMetadataJSONObject.optString("salesforceProjectId")
		).put(
			"shippingAddress", _getShippingAddressJSONObject()
		).put(
			"soldBySalesTerritory", shippingAddress.getCountryISOCode()
		).put(
			"termType", "Single Year"
		).put(
			"typeOfBusiness", "New Business"
		);

		if (Objects.equals(_order.getPaymentMethod(), "money-order")) {
			jsonObject.put("invoice", _getInvoiceJSONObject());
		}

		return jsonObject.toString();
	}

	private String _format(Date date) {
		if (date == null) {
			return null;
		}

		return DateTimeFormatter.ISO_INSTANT.format(date.toInstant());
	}

	private JSONObject _getBillingAddressJSONObject() {
		BillingAddress billingAddress = _order.getBillingAddress();

		return new JSONObject(
		).put(
			"city", billingAddress.getCity()
		).put(
			"country", billingAddress.getCountryISOCode()
		).put(
			"postalCode", billingAddress.getZip()
		).put(
			"state", billingAddress.getRegionISOCode()
		).put(
			"street",
			billingAddress.getStreet1() + " " + billingAddress.getStreet2()
		);
	}

	private JSONObject _getInvoiceJSONObject() {
		return new JSONObject(
		).put(
			"accountInvoiceEmail", _order.getCreatorEmailAddress()
		).put(
			"grossAmount", _order.getTotalAmount()
		).put(
			"invoiceBy", "Liferay Intl."
		).put(
			"localCurrency", _order.getCurrencyCode()
		).put(
			"soldThrough", "Marketplace"
		).put(
			"status", "Needs Invoicing"
		);
	}

	private JSONObject _getPrimaryContactJSONObject() {
		return new JSONObject(
		).put(
			"email", _order.getCreatorEmailAddress()
		).put(
			"firstName", _userAccount.getGivenName()
		).put(
			"lastName", _userAccount.getFamilyName()
		).put(
			"role", "Marketplace User"
		);
	}

	private JSONObject _getProductJSONObject() {
		return new JSONObject(
		).put(
			"listPrice", _order.getTotal()
		).put(
			"orderType", "New"
		).put(
			"productEndDate",
			_format(
				MarketplaceUtil.getOrderPurchaseEndDate(
					_licenseType,
					MarketplaceUtil.getSkuOptionValue(
						"license-usage-type", _orderItem.getOptions())))
		).put(
			"productId", _product.getId()
		).put(
			"productName", MarketplaceUtil.getDefaultLocale(_product.getName())
		).put(
			"productStartDate", _format(_order.getCreateDate())
		).put(
			"quantity", _orderItem.getQuantity()
		);
	}

	private JSONObject _getShippingAddressJSONObject() {
		ShippingAddress shippingAddress = _order.getShippingAddress();

		return new JSONObject(
		).put(
			"city", shippingAddress.getCity()
		).put(
			"country", shippingAddress.getCountryISOCode()
		).put(
			"postalCode", shippingAddress.getZip()
		).put(
			"state", shippingAddress.getRegionISOCode()
		).put(
			"street",
			shippingAddress.getStreet1() + " " + shippingAddress.getStreet2()
		);
	}

	private final String _licenseType;
	private final Order _order;
	private final OrderItem _orderItem;
	private final Product _product;
	private final UserAccount _userAccount;

}