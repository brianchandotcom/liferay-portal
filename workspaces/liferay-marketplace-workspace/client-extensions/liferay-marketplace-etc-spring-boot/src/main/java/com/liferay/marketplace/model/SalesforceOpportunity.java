/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model;

import com.liferay.headless.admin.address.client.dto.v1_0.Country;
import com.liferay.headless.admin.address.client.dto.v1_0.Region;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.commerce.admin.catalog.client.custom.field.CustomField;
import com.liferay.headless.commerce.admin.catalog.client.custom.field.CustomValue;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.marketplace.util.MarketplaceUtil;
import com.liferay.portal.kernel.util.Validator;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Keven Leone
 */
public class SalesforceOpportunity {

	public SalesforceOpportunity(
		Country country, String licenseType, Order order, Sku sku,
		UserAccount userAccount) {

		_country = country;
		_licenseType = licenseType;
		_order = order;
		_sku = sku;
		_userAccount = userAccount;
	}

	public String toString() {
		JSONObject jsonObject = new JSONObject(
		).put(
			"accountId", _getAccountId()
		).put(
			"billingAddress", _getBillingAddressJSONObject()
		).put(
			"closeDate", _format(_order.getCreateDate())
		).put(
			"lineItems", _getLineItemsJSONArray()
		).put(
			"marketplaceDealId", _order.getId()
		).put(
			"opportunityCurrency", _order.getCurrencyCode()
		).put(
			"opportunityOwner", "Marketplace Integration"
		).put(
			"paymentMethodType", _getPaymentMethodType()
		).put(
			"primaryContact", _getPrimaryContactJSONObject()
		).put(
			"project", _getProjectJSONObject()
		).put(
			"termType", "Single Year"
		).put(
			"typeOfBusiness", "Existing Business"
		);

		return jsonObject.toString();
	}

	private String _format(Date date) {
		if (date == null) {
			return null;
		}

		return date.toInstant(
		).atZone(
			ZoneOffset.UTC
		).toLocalDate(
		).format(
			DateTimeFormatter.ISO_LOCAL_DATE
		);
	}

	private String _getAccountId() {
		Account account = _order.getAccount();

		Map<String, String> customFields =
			(Map<String, String>)account.getCustomFields();

		String salesforceAccountKey = customFields.get(
			"salesforce-account-key");

		if (Validator.isNotNull(salesforceAccountKey)) {
			return salesforceAccountKey;
		}

		return account.getExternalReferenceCode();
	}

	private JSONObject _getBillingAddressJSONObject() {
		BillingAddress billingAddress = _order.getBillingAddress();

		return new JSONObject(
		).put(
			"addressName", billingAddress.getName()
		).put(
			"city", billingAddress.getCity()
		).put(
			"country",
			MarketplaceUtil.getDefaultLocale(_country.getTitle_i18n())
		).put(
			"postalCode", billingAddress.getZip()
		).put(
			"state", _getState()
		).put(
			"street",
			billingAddress.getStreet1() + " " + billingAddress.getStreet2()
		);
	}

	private JSONArray _getLineItemsJSONArray() {
		String productId = null;

		for (CustomField customField : _sku.getCustomFields()) {
			if (Objects.equals(
					customField.getName(), "salesforce-product-id")) {

				CustomValue customValue = customField.getCustomValue();

				Object data = customValue.getData();

				if (data != null) {
					productId = data.toString();
				}

				break;
			}
		}

		JSONArray jsonArray = new JSONArray();

		for (OrderItem orderItem : _order.getOrderItems()) {
			JSONObject jsonObject = new JSONObject();

			if (!Objects.equals(
					_order.getOrderTypeExternalReferenceCode(),
					"AI_HUB_TOKEN")) {

				jsonObject.put(
					"endDate",
					_format(
						MarketplaceUtil.getOrderPurchaseEndDate(
							_licenseType,
							MarketplaceUtil.getSkuOptionValue(
								"license-usage-type", orderItem.getOptions())))
				).put(
					"startDate", _format(_order.getCreateDate())
				);
			}

			jsonArray.put(
				jsonObject.put(
					"orderType", "New"
				).put(
					"productId", productId
				).put(
					"quantity", orderItem.getQuantity()
				).put(
					"unitPrice", orderItem.getUnitPrice()
				));
		}

		return jsonArray;
	}

	private String _getPaymentMethodType() {
		if (Objects.equals(_order.getPaymentMethod(), "money-order")) {
			return "Offline";
		}

		return "Online";
	}

	private String _getPrimaryContactEmailAddress() {
		JSONObject orderMetadataJSONObject =
			MarketplaceUtil.getOrderMetadataJSONObject(_order);

		if (Objects.equals(
				_order.getOrderTypeExternalReferenceCode(), "AI_HUB")) {

			JSONObject aiHubFormJSONObject =
				orderMetadataJSONObject.optJSONObject(
					"aiHubForm", new JSONObject());

			return aiHubFormJSONObject.optString(
				"administratorEmailAddress", _order.getCreatorEmailAddress());
		}

		JSONObject provisioningFormJSONObject =
			orderMetadataJSONObject.optJSONObject(
				"provisioningForm", new JSONObject());

		return provisioningFormJSONObject.optString(
			"ownerEmailAddress", _order.getCreatorEmailAddress());
	}

	private JSONObject _getPrimaryContactJSONObject() {
		return new JSONObject(
		).put(
			"email", _getPrimaryContactEmailAddress()
		).put(
			"firstName", _userAccount.getGivenName()
		).put(
			"lastName", _userAccount.getFamilyName()
		).put(
			"role", "Opportunity Owner"
		);
	}

	private JSONObject _getProjectJSONObject() {
		JSONObject orderMetadataJSONObject =
			MarketplaceUtil.getOrderMetadataJSONObject(_order);

		JSONObject projectJSONObject = new JSONObject(
		).put(
			"projectId",
			orderMetadataJSONObject.getString("salesforceProjectId")
		);

		if (Objects.equals(
				_order.getOrderTypeExternalReferenceCode(), "AI_HUB")) {

			MarketplaceUtil.getOrderMetadataJSONObject(_order);

			JSONObject aiHubFormJSONObject =
				orderMetadataJSONObject.getJSONObject("aiHubForm");

			return projectJSONObject.put(
				"aiHubAccountName",
				aiHubFormJSONObject.optString("aiHubAccountName")
			).put(
				"projectContacts",
				new JSONArray(
				).put(
					_getPrimaryContactJSONObject().put(
						"role", "AI Hub Administrator")
				)
			);
		}

		if (Objects.equals(
				_order.getOrderTypeExternalReferenceCode(), "AI_HUB_TOKEN")) {

			return projectJSONObject.put("projectContacts", new JSONArray());
		}

		JSONObject provisioningFormJSONObject =
			orderMetadataJSONObject.optJSONObject("provisioningForm");

		if (provisioningFormJSONObject == null) {
			return projectJSONObject;
		}

		return projectJSONObject.put(
			"allowedEmailDomains",
			provisioningFormJSONObject.optString("allowedEmailDomains")
		).put(
			"dataCenterLocation",
			provisioningFormJSONObject.optString("dataCenterLocation")
		).put(
			"friendlyWorkspaceURL",
			provisioningFormJSONObject.optString("friendlyWorkspaceURL")
		).put(
			"projectContacts",
			new JSONArray(
			).put(
				_getPrimaryContactJSONObject().put("role", "LDP Administrator")
			)
		).put(
			"securityContactEmailAddress", _order.getCreatorEmailAddress()
		).put(
			"workspaceName",
			provisioningFormJSONObject.optString("workspaceName")
		);
	}

	private String _getState() {
		BillingAddress billingAddress = _order.getBillingAddress();

		for (Region region : _country.getRegions()) {
			if (Objects.equals(
					billingAddress.getRegionISOCode(),
					region.getRegionCode())) {

				return MarketplaceUtil.getDefaultLocale(region.getTitle_i18n());
			}
		}

		return null;
	}

	private final Country _country;
	private final String _licenseType;
	private final Order _order;
	private final Sku _sku;
	private final UserAccount _userAccount;

}