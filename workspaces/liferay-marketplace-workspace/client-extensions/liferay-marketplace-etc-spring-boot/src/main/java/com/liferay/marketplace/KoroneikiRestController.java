/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductConsumption;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchaseView;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseResource;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseViewResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

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

		_initResource();

		JSONObject jsonObject = new JSONObject(json);

		JSONObject commerceOrderJSONObject = jsonObject.getJSONObject(
			"commerceOrder");

		JSONArray orderItemsJSONArray = commerceOrderJSONObject.getJSONArray(
			"orderItems");

		if (orderItemsJSONArray == null) {
			return;
		}

		int accountId = commerceOrderJSONObject.getInt("accountId");

		JSONObject accountJSONObject = _getAccountJSONObject(jwt, accountId);

		int orderId = commerceOrderJSONObject.getInt("id");

		_patchOrderStatus(
			jwt, orderId,
			new JSONObject(
			).put(
				"orderStatus", _COMMERCE_ORDER_PROCESSING_STATUS
			));

		int cpDefinitionIdInt = GetterUtil.getInteger(
			orderItemsJSONArray.getJSONObject(
				0
			).getString(
				"cpDefinitionId"
			));

		String cpDefinitionId = String.valueOf(cpDefinitionIdInt + 1);

		JSONObject commerceProductJSONObject = _getCommerceProductJSONObject(
			jwt, commerceOrderJSONObject.getInt("channelId"), cpDefinitionId);

		Map<String, String> commerceProductSKUs = _getCommerceProductSKUS(
			jwt, cpDefinitionId);

		String licenseType = _getLicenseType(
			commerceProductJSONObject.getJSONArray("productSpecifications"));

		ZonedDateTime commerceOrderStartDate = ZonedDateTime.parse(
			commerceOrderJSONObject.getString("createDate"),
			DateTimeFormatter.ISO_DATE_TIME);

		int successCount = 0;

		Map<String, Boolean> dxpLicenseUsageTypePropertiesMap = new HashMap<>();

		for (int i = 0; i < orderItemsJSONArray.length(); i++) {
			JSONObject orderItemJSONObject = orderItemsJSONArray.getJSONObject(
				i);

			_getDXPLicenseUsageTypeProperties(
				orderItemJSONObject, dxpLicenseUsageTypePropertiesMap);

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
							accountJSONObject.getString(
								"externalReferenceCode"),
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

		JSONObject orderDetailsJSONObject = new JSONObject(
		).put(
			"orderStatus", _COMMERCE_ORDER_COMPLETED_STATUS
		);

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

				orderDetailsJSONObject.put(
					"paymentStatus", _COMMERCE_ORDER_PAYMENT_COMPLETED_STATUS);
			}

			if (!dxpLicenseUsageTypePropertiesMap.get("developer") &&
				!dxpLicenseUsageTypePropertiesMap.get("standard") &&
				!dxpLicenseUsageTypePropertiesMap.get("trial")) {

				orderCompleted = true;
			}
		}

		if (orderCompleted) {
			_patchOrderStatus(jwt, orderId, orderDetailsJSONObject);
		}
	}

	public JSONObject getOrderJSONObject(Jwt jwt, String orderId) {
		WebClient webClient = _getWebClient(jwt);

		String response = webClient.get(
		).uri(
			uriBuilder -> uriBuilder.path(
				"o/headless-commerce-admin-order/v1.0/orders/" + orderId
			).queryParam(
				"nestedFields", "orderItems"
			).build()
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		return new JSONObject(response);
	}

	@GetMapping("subscriptions/{orderId}")
	public String getSubscriptionsByOrderId(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("orderId") String orderId)
		throws Exception {

		_initResource();

		JSONArray jsonArray = new JSONArray();

		JSONObject orderJSONObject = getOrderJSONObject(jwt, orderId);

		JSONArray orderItemsJSONArray = orderJSONObject.getJSONArray(
			"orderItems");

		for (int i = 0; i < orderItemsJSONArray.length(); i++) {
			JSONObject orderItemJSONObject = orderItemsJSONArray.getJSONObject(
				i);

			String skuExternalReferenceCode = orderItemJSONObject.getString(
				"skuExternalReferenceCode");

			Map<String, Boolean> dxpLicenseUsageTypePropertiesMap =
				new HashMap<>();

			_getDXPLicenseUsageTypeProperties(
				orderItemJSONObject, dxpLicenseUsageTypePropertiesMap);

			ProductPurchaseView productPurchaseView =
				_productPurchaseViewResource.
					getAccountAccountKeyProductProductKeyProductPurchaseView(
						orderJSONObject.getString(
							"accountExternalReferenceCode"),
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
					"purchasedCount", orderItemJSONObject.getInt("quantity")
				).put(
					"provisionedCount", provisionedCount
				).put(
					"productPurchasedKey", productPurchase.getKey()
				).put(
					"skuId", orderItemJSONObject.getInt("skuId")
				).put(
					"startDate",
					productPurchase.getPerpetual() ?
						orderJSONObject.getString("createDate") :
							productPurchase.getStartDate()
				).put(
					"perpetual", productPurchase.getPerpetual()
				));
		}

		return jsonArray.toString();
	}

	private JSONObject _getAccountJSONObject(Jwt jwt, int accountId) {
		WebClient webClient = _getWebClient(jwt);

		String response = webClient.get(
		).uri(
			uriBuilder -> uriBuilder.path(
				"o/headless-admin-user/v1.0/accounts/" + accountId
			).build()
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		return new JSONObject(response);
	}

	private JSONObject _getCommerceProductJSONObject(
		Jwt jwt, int channelId, String productId) {

		WebClient webClient = _getWebClient(jwt);

		String response = webClient.get(
		).uri(
			uriBuilder -> uriBuilder.path(
				StringBundler.concat(
					"o/headless-commerce-delivery-catalog/v1.0/channels/",
					channelId, "/products/", productId)
			).queryParam(
				"accountId", "-1"
			).queryParam(
				"nestedFields", "productSpecifications"
			).build()
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		return new JSONObject(response);
	}

	private Map<String, String> _getCommerceProductSKUS(
		Jwt jwt, String productId) {

		WebClient webClient = _getWebClient(jwt);

		String response = webClient.get(
		).uri(
			uriBuilder -> uriBuilder.path(
				"o/headless-commerce-admin-catalog/v1.0/products/" + productId +
					"/skus"
			).queryParam(
				"accountId", "-1"
			).queryParam(
				"nestedFields", "productSpecifications"
			).build()
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		JSONArray jsonArray = new JSONObject(
			response
		).getJSONArray(
			"items"
		);

		Map<String, String> map = new HashMap<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			map.put(
				jsonObject.getString("sku"),
				jsonObject.getString("externalReferenceCode"));
		}

		return map;
	}

	private void _getDXPLicenseUsageTypeProperties(
		JSONObject orderJSONObject, Map<String, Boolean> map) {

		if (map.isEmpty()) {
			map.put("developer", false);
			map.put("standard", false);
			map.put("trial", false);
		}

		JSONArray optionsJSONArray = new JSONArray(
			orderJSONObject.getString("options"));

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

	private String _getLicenseType(JSONArray jsonArray) {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String specificationKey = jsonObject.getString("specificationKey");

			if (specificationKey.equals("license-type")) {
				return jsonObject.getString("value");
			}
		}

		return null;
	}

	private WebClient _getWebClient(Jwt jwt) {
		WebClient.Builder builder = WebClient.builder();

		return builder.baseUrl(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).build();
	}

	private void _initResource() throws Exception {
		URL url = new URL(_koroneikiAuthURL);

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
	}

	private void _patchOrderStatus(
		Jwt jwt, int orderId, JSONObject jsonObject) {

		WebClient webClient = _getWebClient(jwt);

		try {
			webClient.patch(
			).uri(
				uriBuilder -> uriBuilder.path(
					"o/headless-commerce-admin-order/v1.0/orders/" + orderId
				).build()
			).accept(
				MediaType.APPLICATION_JSON
			).contentType(
				MediaType.APPLICATION_JSON
			).bodyValue(
				jsonObject.toString()
			).retrieve(
			).bodyToMono(
				String.class
			).block();

			System.out.println(
				StringBundler.concat(
					"Order: ", orderId, " updated to status: ", jsonObject));
		}
		catch (Exception exception) {
			System.out.println(
				StringBundler.concat(
					"Unable to update the order: ", orderId, " to ",
					jsonObject.getInt("orderStatus"), "Reason: ", exception));
		}
	}

	private static final int _COMMERCE_ORDER_COMPLETED_STATUS = 0;

	private static final int _COMMERCE_ORDER_PAYMENT_COMPLETED_STATUS = 0;

	private static final int _COMMERCE_ORDER_PROCESSING_STATUS = 10;

	@Value("${com.liferay.lxc.koroneiki.auth.token}")
	private String _koroneikiAuthToken;

	@Value("${com.liferay.lxc.koroneiki.auth.url}")
	private String _koroneikiAuthURL;

	private ProductPurchaseResource _productPurchaseResource;
	private ProductPurchaseViewResource _productPurchaseViewResource;

}