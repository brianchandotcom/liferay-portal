/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.ProductResource;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.ProductSpecificationResource;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.SkuResource;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderItemResource;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductConsumption;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchaseView;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseResource;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseViewResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keven Leone
 */
@RequestMapping("/koroneiki")
@RestController
public class KoroneikiRestController extends BaseRestController {

	@PostMapping("product-purchase")
	public void createProductPurchase(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		_initResource(jwt);

		JSONObject jsonObject = new JSONObject(json);

		JSONObject commerceOrderJSONObject = jsonObject.getJSONObject(
			"commerceOrder");

		JSONArray orderItemsJSONArray = commerceOrderJSONObject.getJSONArray(
			"orderItems");

		if (orderItemsJSONArray == null) {
			return;
		}

		long orderId = commerceOrderJSONObject.getLong("id");

		Order order = new Order();

		order.setOrderStatus(_COMMERCE_ORDER_PROCESSING_STATUS);

		_orderResource.patchOrder(orderId, order);

		int cpDefinitionIdInt = GetterUtil.getInteger(
			orderItemsJSONArray.getJSONObject(
				0
			).getString(
				"cpDefinitionId"
			));

		long cpDefinitionId = cpDefinitionIdInt + 1;

		Product product = _productResource.getProduct(cpDefinitionId);

		Page<Sku> skuPage = _skuResource.getProductIdSkusPage(
			product.getProductId(), Pagination.of(1, 10));

		Page<ProductSpecification> productSpecificationPage =
			_productSpecificationResource.getProductIdProductSpecificationsPage(
				product.getProductId(), Pagination.of(1, 10));

		Map<String, String> commerceProductSKUs = _getCommerceProductSKUS(
			skuPage.getItems());

		String licenseType = _getLicenseType(
			productSpecificationPage.getItems());

		ZonedDateTime commerceOrderStartDate = ZonedDateTime.parse(
			commerceOrderJSONObject.getString("createDate"),
			DateTimeFormatter.ISO_DATE_TIME);

		int successCount = 0;

		Map<String, Boolean> dxpLicenseUsageTypePropertiesMap = new HashMap<>();

		for (int i = 0; i < orderItemsJSONArray.length(); i++) {
			JSONObject orderItemJSONObject = orderItemsJSONArray.getJSONObject(
				i);

			_getDXPLicenseUsageTypeProperties(
				orderItemJSONObject.getString("options"),
				dxpLicenseUsageTypePropertiesMap);

			ProductPurchase productPurchase = new ProductPurchase();

			if (Validator.isNotNull(licenseType) &&
				licenseType.equals("Subscription")) {

				productPurchase.setEndDate(
					Date.from(
						commerceOrderStartDate.plusYears(
							1
						).toInstant()));
			}

			if (Validator.isNotNull(licenseType) &&
				licenseType.equals("Trial")) {

				productPurchase.setEndDate(
					Date.from(
						commerceOrderStartDate.plusMonths(
							1
						).toInstant()));
			}

			productPurchase.setStartDate(
				Date.from(commerceOrderStartDate.toInstant()));
			productPurchase.setPerpetual(
				Validator.isNotNull(licenseType) &&
				licenseType.equals("Perpetual"));
			productPurchase.setProductKey(
				commerceProductSKUs.get(orderItemJSONObject.getString("sku")));
			productPurchase.setStatus(ProductPurchase.Status.APPROVED);
			productPurchase.setQuantity(orderItemJSONObject.getInt("quantity"));

			ExternalLink externalLink = new ExternalLink();

			externalLink.setDomain("salesforce");
			externalLink.setEntityId(String.valueOf(orderId));
			externalLink.setEntityName("opportunity");

			ExternalLink[] externalLinks = {externalLink};

			productPurchase.setExternalLinks(externalLinks);

			try {
				productPurchase =
					_productPurchaseResource.
						postAccountAccountKeyProductPurchase(
							jsonObject.getString("userName"),
							String.valueOf(
								commerceOrderJSONObject.getInt("userId")),
							_accountResource.getAccount(
								commerceOrderJSONObject.getLong("accountId")
							).getExternalReferenceCode(),
							productPurchase);

				successCount++;

				System.out.println(
					"Create Account Purchased Key" + productPurchase);
			}
			catch (Exception exception) {
				System.out.println(
					"Failed to create account purchase." + exception);
			}
		}

		order.setOrderStatus(_COMMERCE_ORDER_COMPLETED_STATUS);

		boolean orderCompleted = false;

		if (successCount == orderItemsJSONArray.length()) {
			if (dxpLicenseUsageTypePropertiesMap.get("developer") ||
				(dxpLicenseUsageTypePropertiesMap.get("standard") &&
				 (commerceOrderJSONObject.getInt("paymentStatus") ==
					 _COMMERCE_ORDER_PAYMENT_COMPLETED_STATUS))) {

				orderCompleted = true;
			}

			if (dxpLicenseUsageTypePropertiesMap.get("trial")) {
				orderCompleted = true;

				order.setPaymentStatus(
					_COMMERCE_ORDER_PAYMENT_COMPLETED_STATUS);
			}

			if (!dxpLicenseUsageTypePropertiesMap.get("developer") &&
				!dxpLicenseUsageTypePropertiesMap.get("standard") &&
				!dxpLicenseUsageTypePropertiesMap.get("trial")) {

				orderCompleted = true;
			}
		}

		if (orderCompleted) {
			_orderResource.patchOrder(orderId, order);
		}
	}

	@GetMapping("subscriptions/{orderId}")
	public String getSubscriptionsByOrderId(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("orderId") String orderId)
		throws Exception {

		_initResource(jwt);

		JSONArray jsonArray = new JSONArray();

		long orderId2 = GetterUtil.getLong(orderId);

		com.liferay.headless.commerce.admin.order.client.pagination.Page
			<OrderItem> orderItemPage =
				_orderItemResource.getOrderIdOrderItemsPage(
					orderId2,
					com.liferay.headless.commerce.admin.order.client.pagination.
						Pagination.of(1, 10));

		Order order = _orderResource.getOrder(orderId2);

		Collection<OrderItem> orderItemCollection = orderItemPage.getItems();

		for (OrderItem orderItem : orderItemCollection) {
			String skuExternalReferenceCode =
				orderItem.getSkuExternalReferenceCode();

			Map<String, Boolean> dxpLicenseUsageTypePropertiesMap =
				new HashMap<>();

			_getDXPLicenseUsageTypeProperties(
				orderItem.getOptions(), dxpLicenseUsageTypePropertiesMap);

			ProductPurchaseView productPurchaseView =
				_productPurchaseViewResource.
					getAccountAccountKeyProductProductKeyProductPurchaseView(
						order.getAccountExternalReferenceCode(),
						skuExternalReferenceCode);

			ProductConsumption[] productConsumptions =
				productPurchaseView.getProductConsumptions();

			ProductPurchase[] productPurchases =
				productPurchaseView.getProductPurchases();

			ProductPurchase productPurchase = productPurchases[0];

			int provisionedCount = 0;

			for (ProductConsumption productConsumption : productConsumptions) {
				if (productConsumption.getEndDate(
					).after(
						new Date()
					)) {

					provisionedCount++;
				}
			}

			String name = skuExternalReferenceCode;

			for (Map.Entry<String, Boolean> set :
					dxpLicenseUsageTypePropertiesMap.entrySet()) {

				if (set.getValue()) {
					name = set.getKey();

					break;
				}
			}

			jsonArray.put(
				new JSONObject(
				).put(
					"endDate",
					productPurchase.getPerpetual() ? null :
						productPurchase.getEndDate()
				).put(
					"name", name
				).put(
					"purchasedCount", orderItem.getQuantity()
				).put(
					"provisionedCount", provisionedCount
				).put(
					"productPurchasedKey", productPurchase.getKey()
				).put(
					"skuId", orderItem.getSkuId()
				).put(
					"startDate",
					productPurchase.getPerpetual() ? order.getCreateDate() :
						productPurchase.getStartDate()
				).put(
					"perpetual", productPurchase.getPerpetual()
				));
		}

		return jsonArray.toString();
	}

	private Map<String, String> _getCommerceProductSKUS(
		Collection<Sku> skuCollection) {

		Map<String, String> map = new HashMap<>();

		skuCollection.forEach(
			sku -> map.put(sku.getSku(), sku.getExternalReferenceCode()));

		return map;
	}

	private void _getDXPLicenseUsageTypeProperties(
		String options, Map<String, Boolean> map) {

		if (map.isEmpty()) {
			map.put("developer", false);
			map.put("standard", false);
			map.put("trial", false);
		}

		JSONArray optionsJSONArray = new JSONArray(options);

		for (int i = 0; i < optionsJSONArray.length(); i++) {
			JSONObject jsonObject = optionsJSONArray.getJSONObject(i);

			String key = jsonObject.getString("key");

			if (key.equals("dxp-license-usage-type")) {
				JSONArray jsonArray = jsonObject.getJSONArray("value");

				for (int j = 0; j < jsonArray.length(); j++) {
					String licenseUsageType = jsonArray.getString(j);

					if (!map.get("developer")) {
						map.put(
							"developer", licenseUsageType.equals("developer"));
					}

					if (!map.get("standard")) {
						map.put(
							"standard", licenseUsageType.equals("standard"));
					}

					if (!map.get("trial")) {
						map.put("trial", licenseUsageType.equals("trial"));
					}
				}
			}
		}
	}

	private String _getLicenseType(
		Collection<ProductSpecification> productSpecificationCollection) {

		for (ProductSpecification productSpecification :
				productSpecificationCollection) {

			String specificationKey =
				productSpecification.getSpecificationKey();

			if (specificationKey.equals("license-type")) {
				return productSpecification.getValue(
				).get(
					"en_US"
				);
			}
		}

		return null;
	}

	private void _initResource(Jwt jwt) throws Exception {
		URL url = new URL(_koroneikiAuthURL);

		URL liferayURL = new URL(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain);

		_accountResource = AccountResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayURL.getHost(), liferayURL.getPort(), liferayURL.getProtocol()
		).build();

		_orderItemResource = OrderItemResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayURL.getHost(), liferayURL.getPort(), liferayURL.getProtocol()
		).build();

		_orderResource = OrderResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayURL.getHost(), liferayURL.getPort(), liferayURL.getProtocol()
		).build();

		_productPurchaseResource = ProductPurchaseResource.builder(
		).header(
			"API_TOKEN", _koroneikiAuthToken
		).endpoint(
			url.getHost(), url.getPort(), url.getProtocol()
		).build();

		_productPurchaseViewResource = ProductPurchaseViewResource.builder(
		).header(
			"API_TOKEN", _koroneikiAuthToken
		).endpoint(
			url.getHost(), url.getPort(), url.getProtocol()
		).build();

		_productResource = ProductResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayURL.getHost(), liferayURL.getPort(), liferayURL.getProtocol()
		).build();

		_productSpecificationResource = ProductSpecificationResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayURL.getHost(), liferayURL.getPort(), liferayURL.getProtocol()
		).build();

		_skuResource = SkuResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayURL.getHost(), liferayURL.getPort(), liferayURL.getProtocol()
		).build();
	}

	private static final int _COMMERCE_ORDER_COMPLETED_STATUS = 0;

	private static final int _COMMERCE_ORDER_PAYMENT_COMPLETED_STATUS = 0;

	private static final int _COMMERCE_ORDER_PROCESSING_STATUS = 10;

	private AccountResource _accountResource;

	@Value("${com.liferay.lxc.koroneiki.auth.token}")
	private String _koroneikiAuthToken;

	@Value("${com.liferay.lxc.koroneiki.auth.url}")
	private String _koroneikiAuthURL;

	private OrderItemResource _orderItemResource;
	private OrderResource _orderResource;
	private ProductPurchaseResource _productPurchaseResource;
	private ProductPurchaseViewResource _productPurchaseViewResource;
	private ProductResource _productResource;
	private ProductSpecificationResource _productSpecificationResource;
	private SkuResource _skuResource;

}