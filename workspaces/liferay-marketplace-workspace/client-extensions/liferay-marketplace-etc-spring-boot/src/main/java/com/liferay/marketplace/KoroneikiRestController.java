/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.CustomField;
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.client.resource.v1_0.PostalAddressResource;
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
import com.liferay.petra.string.StringPool;

import java.net.URL;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

	@GetMapping("subscriptions/{orderId}")
	public String getSubscriptions(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("orderId") long orderId)
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

			ProductPurchaseView productPurchaseView =
				_productPurchaseViewResource.
					getAccountAccountKeyProductProductKeyProductPurchaseView(
						order.getAccountExternalReferenceCode(),
						orderItem.getSkuExternalReferenceCode());

			ProductPurchase productPurchase = null;

			outerLoop:
			for (ProductPurchase currentProductPurchase :
					productPurchaseView.getProductPurchases()) {

				for (ExternalLink externalLink :
						currentProductPurchase.getExternalLinks()) {

					if (Objects.equals(
							externalLink.getEntityId(),
							String.valueOf(orderId))) {

						productPurchase = currentProductPurchase;

						break outerLoop;
					}
				}
			}

			if (productPurchase == null) {
				continue;
			}

			String endDate = null;

			if (!productPurchase.getPerpetual()) {
				endDate = ZonedDateTime.ofInstant(
					productPurchase.getEndDate(
					).toInstant(),
					ZoneOffset.UTC
				).format(
					DateTimeFormatter.ISO_INSTANT
				);
			}

			String dxpLicenseName = orderItem.getSkuExternalReferenceCode();

			Map<String, Boolean> dxpLicenseUsageTypePropertiesMap =
				new HashMap<>();

			_populateDXPLicenseUsageTypePropertiesMap(
				dxpLicenseUsageTypePropertiesMap, orderItem.getOptions());

			for (String dxpLicenseUsageType : _DXP_LICENSE_USAGE_TYPES) {
				if (dxpLicenseUsageTypePropertiesMap.get(dxpLicenseUsageType)) {
					dxpLicenseName = dxpLicenseUsageType;

					break;
				}
			}

			int provisionedCount = 0;

			for (ProductConsumption productConsumption :
					productPurchaseView.getProductConsumptions()) {

				if (Objects.equals(
						productConsumption.getProductPurchaseKey(),
						productPurchase.getKey()) &&
					productConsumption.getEndDate(
					).after(
						new Date()
					)) {

					provisionedCount++;
				}
			}

			Date startDate = productPurchase.getStartDate();

			if (productPurchase.getPerpetual()) {
				startDate = order.getCreateDate();
			}

			jsonArray.put(
				new JSONObject(
				).put(
					"endDate", endDate
				).put(
					"name", dxpLicenseName
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

	@PostMapping("product-purchase")
	public void postProductPurchase(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject commerceOrderJSONObject = jsonObject.getJSONObject(
			"commerceOrder");

		if (commerceOrderJSONObject.getInt("paymentStatus") !=
				_COMMERCE_ORDER_STATUS_PAYMENT_COMPLETED) {

			if (_log.isInfoEnabled()) {
				_log.info(
					"Skipping POST product purchase for order " +
						commerceOrderJSONObject.getLong("id") +
							" because payment status is not completed");
			}

			return;
		}

		JSONArray orderItemsJSONArray = commerceOrderJSONObject.getJSONArray(
			"orderItems");

		_initResourceBuilders(jwt);

		Order order = _orderResource.getOrder(
			commerceOrderJSONObject.getLong("id"));

		if ((orderItemsJSONArray == null) ||
			!Objects.equals(
				order.getOrderTypeExternalReferenceCode(),
				_ALLOWED_ORDER_TYPE)) {

			if (_log.isInfoEnabled()) {
				_log.info(
					"Skipping POST product purchase for order " +
						commerceOrderJSONObject.getLong("id") +
							" because order type is not supported");
			}

			return;
		}

		order.setOrderStatus(_COMMERCE_ORDER_STATUS_PROCESSING);

		_orderResource.patchOrder(commerceOrderJSONObject.getLong("id"), order);

		long cpDefinitionId = Long.valueOf(
			orderItemsJSONArray.getJSONObject(
				0
			).getString(
				"cpDefinitionId"
			));

		Product product = _productResource.getProduct(cpDefinitionId + 1);

		Map<String, String> productSpecificationsMap =
			_getProductSpecificationsMap(
				_productSpecificationResource.
					getProductIdProductSpecificationsPage(
						product.getProductId(), Pagination.of(1, 20)
					).getItems());

		if (Objects.equals(
				productSpecificationsMap.get("price-model"), "Free")) {

			order.setOrderStatus(_COMMERCE_ORDER_STATUS_COMPLETED);

			_orderResource.patchOrder(
				commerceOrderJSONObject.getLong("id"), order);

			return;
		}

		Account account = _accountResource.getAccount(
			commerceOrderJSONObject.getLong("accountId"));

		if (!account.getExternalReferenceCode(
			).startsWith(
				"KOR-"
			)) {

			account.setExternalReferenceCode(
				_postKoroneikiAccount(
					account, jwt
				).getKey());

			_accountResource.patchAccount(account.getId(), account);
		}

		Map<String, Boolean> dxpLicenseUsageTypePropertiesMap = new HashMap<>();

		try {
			for (int i = 0; i < orderItemsJSONArray.length(); i++) {
				_postAccountAccountKeyProductPurchase(
					account, commerceOrderJSONObject,
					dxpLicenseUsageTypePropertiesMap, jsonObject,
					orderItemsJSONArray.getJSONObject(i), product,
					productSpecificationsMap);
			}

			order.setOrderStatus(_COMMERCE_ORDER_STATUS_COMPLETED);

			_orderResource.patchOrder(
				commerceOrderJSONObject.getLong("id"), order);
		}
		catch (Exception exception) {
			_log.error("Unable to create account product purchase", exception);
		}
	}

	private String _getProductKey(String skuString, Collection<Sku> skus) {
		for (Sku sku : skus) {
			if (Objects.equals(sku.getSku(), skuString)) {
				return sku.getExternalReferenceCode();
			}
		}

		return null;
	}

	private Map<String, String> _getProductSpecificationsMap(
		Collection<ProductSpecification> productSpecifications) {

		Map<String, String> map = new HashMap<>();

		for (ProductSpecification productSpecification :
				productSpecifications) {

			map.put(
				productSpecification.getSpecificationKey(),
				productSpecification.getValue(
				).get(
					"en_US"
				));
		}

		return map;
	}

	private void _initResourceBuilders(Jwt jwt) throws Exception {
		URL liferayDXPURL = new URL(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain);

		_accountResource = AccountResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayDXPURL
		).build();

		URL liferayMarketplaceKoroneikiAuthURL = new URL(_koroneikiAuthURL);

		_koroneikiAccountResource =
			com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.
				AccountResource.builder(
				).header(
					"API_TOKEN", _koroneikiAuthToken
				).endpoint(
					liferayMarketplaceKoroneikiAuthURL
				).build();

		_orderItemResource = OrderItemResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayDXPURL
		).build();

		_orderResource = OrderResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayDXPURL
		).build();

		_postalAddressResource = PostalAddressResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayDXPURL
		).build();

		_productPurchaseResource = ProductPurchaseResource.builder(
		).header(
			"API_TOKEN", _koroneikiAuthToken
		).endpoint(
			liferayMarketplaceKoroneikiAuthURL
		).build();

		_productPurchaseViewResource = ProductPurchaseViewResource.builder(
		).header(
			"API_TOKEN", _koroneikiAuthToken
		).endpoint(
			liferayMarketplaceKoroneikiAuthURL
		).build();

		_productResource = ProductResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayDXPURL
		).build();

		_productSpecificationResource = ProductSpecificationResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayDXPURL
		).build();

		_skuResource = SkuResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			liferayDXPURL
		).build();
	}

	private void _populateDXPLicenseUsageTypePropertiesMap(
		Map<String, Boolean> map, String options) {

		JSONArray optionsJSONArray = new JSONArray(options);

		for (int i = 0; i < optionsJSONArray.length(); i++) {
			JSONObject jsonObject = optionsJSONArray.getJSONObject(i);

			if (!Objects.equals(
					jsonObject.getString("key"), "dxp-license-usage-type")) {

				continue;
			}

			JSONArray jsonArray = jsonObject.getJSONArray("value");

			for (int j = 0; j < jsonArray.length(); j++) {
				for (String dxpLicenseUsageType : _DXP_LICENSE_USAGE_TYPES) {
					if (!map.containsKey(dxpLicenseUsageType) ||
						!map.get(dxpLicenseUsageType)) {

						map.put(
							dxpLicenseUsageType,
							Objects.equals(
								jsonArray.getString(j), dxpLicenseUsageType));
					}
				}
			}
		}
	}

	private void _postAccountAccountKeyProductPurchase(
			Account account, JSONObject commerceOrderJSONObject,
			Map<String, Boolean> dxpLicenseUsageTypePropertiesMap,
			JSONObject jsonObject, JSONObject orderItemJSONObject,
			Product product, Map<String, String> productSpecificationsMap)
		throws Exception {

		ProductPurchase productPurchase = new ProductPurchase();

		_populateDXPLicenseUsageTypePropertiesMap(
			dxpLicenseUsageTypePropertiesMap,
			orderItemJSONObject.getString("options"));

		ZonedDateTime zonedDateTime = ZonedDateTime.now();

		if (Objects.equals(
				productSpecificationsMap.get("license-type"), "Subscription")) {

			Instant instant = zonedDateTime.plusYears(
				1
			).toInstant();

			if (dxpLicenseUsageTypePropertiesMap.get("trial")) {
				instant = zonedDateTime.plusMonths(
					1
				).toInstant();
			}

			productPurchase.setEndDate(Date.from(instant));
		}

		ExternalLink externalLink = new ExternalLink();

		externalLink.setDomain("salesforce");
		externalLink.setEntityId(
			String.valueOf(commerceOrderJSONObject.getLong("id")));
		externalLink.setEntityName("opportunity");

		productPurchase.setExternalLinks(new ExternalLink[] {externalLink});

		productPurchase.setPerpetual(
			Objects.equals(
				productSpecificationsMap.get("license-type"), "Perpetual"));
		productPurchase.setProductKey(
			_getProductKey(
				orderItemJSONObject.getString("sku"),
				_skuResource.getProductIdSkusPage(
					product.getProductId(), Pagination.of(1, 10)
				).getItems()));
		productPurchase.setQuantity(orderItemJSONObject.getInt("quantity"));
		productPurchase.setStartDate(Date.from(zonedDateTime.toInstant()));
		productPurchase.setStatus(ProductPurchase.Status.APPROVED);

		productPurchase =
			_productPurchaseResource.postAccountAccountKeyProductPurchase(
				jsonObject.getString("userName"),
				String.valueOf(commerceOrderJSONObject.getInt("userId")),
				account.getExternalReferenceCode(), productPurchase);

		if (_log.isInfoEnabled()) {
			_log.info("Created account product purchase " + productPurchase);
		}
	}

	private com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
			_postKoroneikiAccount(Account account, Jwt jwt)
		throws Exception {

		com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
			koroneikiAccount =
				new com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
					Account();

		koroneikiAccount.setCode(
			account.getName(
			).replaceAll(
				StringPool.SPACE, StringPool.BLANK
			).toUpperCase());

		Map<String, String> customFieldsMap = new HashMap<>();

		for (CustomField customField : account.getCustomFields()) {
			customFieldsMap.put(
				customField.getName(),
				customField.getCustomValue(
				).getData(
				).toString());
		}

		koroneikiAccount.setContactEmailAddress(
			customFieldsMap.get("Contact Email"));
		koroneikiAccount.setDateCreated(
			Date.from(
				ZonedDateTime.parse(
					customFieldsMap.get("Create Date"),
					DateTimeFormatter.ISO_DATE_TIME
				).toInstant()));

		koroneikiAccount.setDescription(account.getDescription());
		koroneikiAccount.setName(account.getName());
		koroneikiAccount.setPhoneNumber(customFieldsMap.get("Contact Phone"));

		Page<PostalAddress> postalAddressPage =
			_postalAddressResource.getAccountPostalAddressesPage(
				account.getId());

		com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.PostalAddress[]
			koroneikiPostalAddresses = new
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.PostalAddress
				[(int)postalAddressPage.getTotalCount()];

		int i = 0;

		for (PostalAddress postalAddress : postalAddressPage.getItems()) {
			koroneikiPostalAddresses[i] =
				com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
					PostalAddress.toDTO(postalAddress.toString());

			koroneikiPostalAddresses[i].setAddressType("");

			i++;
		}

		koroneikiAccount.setPostalAddresses(koroneikiPostalAddresses);

		koroneikiAccount.setStatus(
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account.
				Status.ACTIVE);
		koroneikiAccount.setWebsite(customFieldsMap.get("Homepage URL"));

		return _koroneikiAccountResource.postAccount(
			jwt.getClaim("username"), jwt.getClaim("sub"), koroneikiAccount);
	}

	private static final String _ALLOWED_ORDER_TYPE = "DXPAPP";

	private static final int _COMMERCE_ORDER_STATUS_COMPLETED = 0;

	private static final int _COMMERCE_ORDER_STATUS_PAYMENT_COMPLETED = 0;

	private static final int _COMMERCE_ORDER_STATUS_PROCESSING = 10;

	private static final String[] _DXP_LICENSE_USAGE_TYPES = {
		"developer", "standard", "trial"
	};

	private static final Log _log = LogFactory.getLog(
		KoroneikiRestController.class);

	private AccountResource _accountResource;
	private
		com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.
			AccountResource _koroneikiAccountResource;

	@Value("${liferay.marketplace.koroneiki.auth.token}")
	private String _koroneikiAuthToken;

	@Value("${liferay.marketplace.koroneiki.auth.url}")
	private String _koroneikiAuthURL;

	private OrderItemResource _orderItemResource;
	private OrderResource _orderResource;
	private PostalAddressResource _postalAddressResource;
	private ProductPurchaseResource _productPurchaseResource;
	private ProductPurchaseViewResource _productPurchaseViewResource;
	private ProductResource _productResource;
	private ProductSpecificationResource _productSpecificationResource;
	private SkuResource _skuResource;

}