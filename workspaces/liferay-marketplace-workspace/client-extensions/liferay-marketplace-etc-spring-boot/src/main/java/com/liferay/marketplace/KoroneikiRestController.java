/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Sku;
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
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import java.time.ZoneOffset;
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

		JSONObject jsonObject = new JSONObject(json);

		JSONObject commerceOrderJSONObject = jsonObject.getJSONObject(
			"commerceOrder");

		JSONArray orderItemsJSONArray = commerceOrderJSONObject.getJSONArray(
			"orderItems");

		if (orderItemsJSONArray == null) {
			return;
		}

		_initResourceBuilders(jwt);

		Order order = new Order();

		order.setOrderStatus(_COMMERCE_ORDER_PROCESSING_STATUS);

		_orderResource.patchOrder(commerceOrderJSONObject.getLong("id"), order);

		long cpDefinitionId = GetterUtil.getInteger(
			orderItemsJSONArray.getJSONObject(
				0
			).getString(
				"cpDefinitionId"
			));

		Product product = _productResource.getProduct(cpDefinitionId + 1);

		Map<String, String> skuMap = _getSkuMap(
			_skuResource.getProductIdSkusPage(
				product.getProductId(), Pagination.of(1, 10)
			).getItems());

		String licenseType = _getLicenseType(
			_productSpecificationResource.getProductIdProductSpecificationsPage(
				product.getProductId(), Pagination.of(1, 10)
			).getItems());

		ZonedDateTime commerceOrderStartDate = ZonedDateTime.parse(
			commerceOrderJSONObject.getString("createDate"),
			DateTimeFormatter.ISO_DATE_TIME);

		int successCount = 0;

		Map<String, Boolean> dxpLicenseUsageTypePropertiesMap = new HashMap<>();

		for (int i = 0; i < orderItemsJSONArray.length(); i++) {
			JSONObject orderItemJSONObject = orderItemsJSONArray.getJSONObject(
				i);

			_getDXPLicenseUsageTypeProperties(
				dxpLicenseUsageTypePropertiesMap,
				orderItemJSONObject.getString("options"));

			ProductPurchase productPurchase = new ProductPurchase();

			if (StringUtil.equals(licenseType, "Subscription")) {
				productPurchase.setEndDate(
					Date.from(
						commerceOrderStartDate.plusYears(
							1
						).toInstant()));
			}

			if (StringUtil.equals(licenseType, "Trial")) {
				productPurchase.setEndDate(
					Date.from(
						commerceOrderStartDate.plusMonths(
							1
						).toInstant()));
			}

			productPurchase.setStartDate(
				Date.from(commerceOrderStartDate.toInstant()));
			productPurchase.setPerpetual(
				StringUtil.equals(licenseType, "Perpetual"));
			productPurchase.setProductKey(
				skuMap.get(orderItemJSONObject.getString("sku")));
			productPurchase.setStatus(ProductPurchase.Status.APPROVED);
			productPurchase.setQuantity(orderItemJSONObject.getInt("quantity"));

			ExternalLink externalLink = new ExternalLink();

			externalLink.setDomain("salesforce");
			externalLink.setEntityId(
				String.valueOf(commerceOrderJSONObject.getLong("id")));
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

		if (successCount != orderItemsJSONArray.length()) {
			return;
		}

		order.setOrderStatus(_COMMERCE_ORDER_COMPLETED_STATUS);

		if (dxpLicenseUsageTypePropertiesMap.get("developer") ||
			(dxpLicenseUsageTypePropertiesMap.get("standard") &&
			 (commerceOrderJSONObject.getInt("paymentStatus") ==
				 _COMMERCE_ORDER_PAYMENT_COMPLETED_STATUS))) {

			_orderResource.patchOrder(
				commerceOrderJSONObject.getLong("id"), order);
		}
		else if (dxpLicenseUsageTypePropertiesMap.get("trial")) {
			order.setPaymentStatus(_COMMERCE_ORDER_PAYMENT_COMPLETED_STATUS);

			_orderResource.patchOrder(
				commerceOrderJSONObject.getLong("id"), order);
		}
		else if (!dxpLicenseUsageTypePropertiesMap.get("developer") &&
				 !dxpLicenseUsageTypePropertiesMap.get("standard") &&
				 !dxpLicenseUsageTypePropertiesMap.get("trial")) {

			_orderResource.patchOrder(
				commerceOrderJSONObject.getLong("id"), order);
		}
	}

	@GetMapping("subscriptions/{orderId}")
	public String getSubscriptionsByOrderId(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("orderId") Long orderId)
		throws Exception {

		_initResourceBuilders(jwt);

		JSONArray jsonArray = new JSONArray();

		Order order = _orderResource.getOrder(orderId);

		for (OrderItem orderItem :
				_orderItemResource.getOrderIdOrderItemsPage(
					orderId,
					com.liferay.headless.commerce.admin.order.client.pagination.
						Pagination.of(1, 10)
				).getItems()) {

			Map<String, Boolean> dxpLicenseUsageTypePropertiesMap =
				new HashMap<>();

			_getDXPLicenseUsageTypeProperties(
				dxpLicenseUsageTypePropertiesMap, orderItem.getOptions());

			ProductPurchaseView productPurchaseView =
				_productPurchaseViewResource.
					getAccountAccountKeyProductProductKeyProductPurchaseView(
						order.getAccountExternalReferenceCode(),
						orderItem.getSkuExternalReferenceCode());

			int provisionedCount = 0;

			for (ProductConsumption productConsumption :
					productPurchaseView.getProductConsumptions()) {

				if (productConsumption.getEndDate(
					).after(
						new Date()
					)) {

					provisionedCount++;
				}
			}

			String licenseName = orderItem.getSkuExternalReferenceCode();

			for (String licenseUsageType : _LICENSE_USAGE_TYPES) {
				if (dxpLicenseUsageTypePropertiesMap.get(licenseUsageType)) {
					licenseName = licenseUsageType;

					break;
				}
			}

			ProductPurchase[] productPurchases =
					productPurchaseView.getProductPurchases();

			ProductPurchase productPurchase = productPurchases[0];

			String endDate = null;
			Date startDate = productPurchase.getStartDate();

			if (productPurchase.getPerpetual()) {
				startDate = order.getCreateDate();
			}
			else {
				endDate = ZonedDateTime.ofInstant(
					productPurchase.getEndDate(
					).toInstant(),
					ZoneOffset.UTC
				).format(
					DateTimeFormatter.ISO_INSTANT
				);
			}

			jsonArray.put(
				new JSONObject(
				).put(
					"endDate", endDate
				).put(
					"name", licenseName
				).put(
					"perpetual", productPurchase.getPerpetual()
				).put(
					"productPurchasedKey", productPurchase.getKey()
				).put(
					"provisionedCount", provisionedCount
				).put(
					"purchasedCount", orderItem.getQuantity()
				).put(
					"skuId", orderItem.getSkuId()
				).put(
					"startDate",
					ZonedDateTime.ofInstant(
						startDate.toInstant(), ZoneOffset.UTC
					).format(
						DateTimeFormatter.ISO_INSTANT
					)
				));
		}

		return jsonArray.toString();
	}

	private void _getDXPLicenseUsageTypeProperties(
		Map<String, Boolean> map, String options) {

		JSONArray optionsJSONArray = new JSONArray(options);

		for (int i = 0; i < optionsJSONArray.length(); i++) {
			JSONObject jsonObject = optionsJSONArray.getJSONObject(i);

			if (!StringUtil.equals(
					jsonObject.getString("key"), "dxp-license-usage-type")) {

				continue;
			}

			JSONArray jsonArray = jsonObject.getJSONArray("value");

			for (int j = 0; j < jsonArray.length(); j++) {
				for (String licenseUsageType : _LICENSE_USAGE_TYPES) {
					if (!map.containsKey(licenseUsageType) ||
						!map.get(licenseUsageType)) {

						map.put(
							licenseUsageType,
							StringUtil.equals(
								jsonArray.getString(j), licenseUsageType));
					}
				}
			}
		}
	}

	private String _getLicenseType(
		Collection<ProductSpecification> productSpecificationCollection) {

		for (ProductSpecification productSpecification :
				productSpecificationCollection) {

			if (!StringUtil.equals(
					productSpecification.getSpecificationKey(),
					"license-type")) {

				continue;
			}

			return productSpecification.getValue(
			).get(
				"en_US"
			);
		}

		return null;
	}

	private Map<String, String> _getSkuMap(Collection<Sku> skuCollection) {
		Map<String, String> map = new HashMap<>();

		for (Sku sku : skuCollection) {
			map.put(sku.getSku(), sku.getExternalReferenceCode());
		}

		return map;
	}

	private void _initResourceBuilders(Jwt jwt) throws Exception {
		URL koroneikiURL = new URL(_koroneikiAuthURL);

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
			koroneikiURL.getHost(), koroneikiURL.getPort(),
			koroneikiURL.getProtocol()
		).build();

		_productPurchaseViewResource = ProductPurchaseViewResource.builder(
		).header(
			"API_TOKEN", _koroneikiAuthToken
		).endpoint(
			koroneikiURL.getHost(), koroneikiURL.getPort(),
			koroneikiURL.getProtocol()
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

	private static final String[] _LICENSE_USAGE_TYPES = {
		"developer", "standard", "trial"
	};

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