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
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.SkuOption;
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
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URL;

import java.nio.charset.Charset;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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

		_initResourceBuilders();

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

			String name = _getDXPLicenseUsageType(orderItem.getOptions());

			if (name == null) {
				name = orderItem.getSkuExternalReferenceCode();
			}

			int provisionedCount = 0;

			for (ProductConsumption productConsumption :
					productPurchaseView.getProductConsumptions()) {

				if (Objects.equals(
						productConsumption.getProductPurchaseKey(),
						productPurchase.getKey()) &&
					(productPurchase.getPerpetual() ||
					 productConsumption.getEndDate(
					 ).after(
						 new Date()
					 ))) {

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
					"name", name
				).put(
					"perpetual", productPurchase.getPerpetual()
				).put(
					"productPurchasedKey", productPurchase.getKey()
				).put(
					"provisionedCount", provisionedCount
				).put(
					"purchasedCount", orderItem.getQuantity()
				).put(
					"productVersion", _getProductVersion(orderItem.getSkuId())
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

	@PostMapping("product/{productId}")
	public void postProduct(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("productId") long productId)
		throws Exception {

		_initResourceBuilders();

		Product product = _productResource.getProduct(productId);

		for (Sku sku :
				_skuResource.getProductIdSkusPage(
					product.getProductId(), Pagination.of(1, 10)
				).getItems()) {

			String dxpLicenseUsageType = _getDXPLicenseUsageType(
				sku.getSkuOptions());

			if ((dxpLicenseUsageType == null) ||
				sku.getExternalReferenceCode(
				).startsWith(
					"KOR-"
				)) {

				if (_log.isInfoEnabled()) {
					_log.info(
						"Skipping POST product for sku " + sku.toString());
				}

				continue;
			}

			String productName = product.getName(
			).get(
				"en_US"
			);

			String name = productName + " - " + dxpLicenseUsageType;

			com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page
				<com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product>
					page = _koroneikiProductResource.getProductsPage(
						"", "name eq '" + name + "'",
						com.liferay.osb.koroneiki.phloem.rest.client.pagination.
							Pagination.of(1, 1),
						"");

			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product
				koroneikiProduct = page.fetchFirstItem();

			if (koroneikiProduct == null) {
				koroneikiProduct =
					new com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
						Product();

				koroneikiProduct.setName(name);
				koroneikiProduct.setProperties(
					HashMapBuilder.put(
						"display-group-name", productName
					).put(
						"display-name", name
					).put(
						"licenses", "true"
					).put(
						"type", "marketplace-app"
					).build());

				koroneikiProduct = _koroneikiProductResource.postProduct(
					jwt.getClaim("username"), jwt.getClaim("sub"),
					koroneikiProduct);

				if (_log.isInfoEnabled()) {
					_log.info("Created product " + koroneikiProduct);
				}
			}

			sku.setExternalReferenceCode(koroneikiProduct.getKey());

			_skuResource.patchSku(sku.getId(), sku);
		}
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

		_initResourceBuilders();

		Order order = _orderResource.getOrder(
			commerceOrderJSONObject.getLong("id"));

		if (!Objects.equals(
				order.getOrderTypeExternalReferenceCode(), "DXPAPP")) {

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

		com.liferay.headless.commerce.admin.order.client.pagination.Page
			<OrderItem> orderItemPage =
				_orderItemResource.getOrderIdOrderItemsPage(
					order.getId(),
					com.liferay.headless.commerce.admin.order.client.pagination.
						Pagination.of(1, 10));

		Map<String, String> productSpecificationsMap =
			_getProductSpecificationsMap(
				_productSpecificationResource.
					getProductIdProductSpecificationsPage(
						_skuResource.getSku(
							orderItemPage.fetchFirstItem(
							).getSkuId()
						).getProductId(),
						Pagination.of(1, 20)
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

		try {
			for (OrderItem orderItem : orderItemPage.getItems()) {
				_postAccountAccountKeyProductPurchase(
					account, jwt, orderItem, productSpecificationsMap);
			}

			order.setOrderStatus(_COMMERCE_ORDER_STATUS_COMPLETED);

			_orderResource.patchOrder(
				commerceOrderJSONObject.getLong("id"), order);
		}
		catch (Exception exception) {
			_log.error("Unable to create account product purchase", exception);
		}
	}

	private String _getDXPLicenseUsageType(SkuOption[] skuOptions) {
		for (SkuOption skuOption : skuOptions) {
			if (!Objects.equals(skuOption.getKey(), "dxp-license-usage-type")) {
				continue;
			}

			String value = skuOption.getValue();

			String firstCharUpperCase = value.substring(
				0, 1
			).toUpperCase();

			return firstCharUpperCase + value.substring(1);
		}

		return null;
	}

	private String _getDXPLicenseUsageType(String options) {
		JSONArray optionsJSONArray = new JSONArray(options);

		for (int i = 0; i < optionsJSONArray.length(); i++) {
			JSONObject jsonObject = optionsJSONArray.getJSONObject(i);

			if (!Objects.equals(
					jsonObject.getString("key"), "dxp-license-usage-type")) {

				continue;
			}

			JSONArray jsonArray = jsonObject.getJSONArray("value");

			return jsonArray.getString(0);
		}

		return null;
	}

	private String _getOAuthAccessToken() throws Exception {
		if ((_oauthAccessToken != null) &&
			(System.currentTimeMillis() < (_oauthExpirationMillis - 15000))) {

			return _oauthAccessToken;
		}

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		HttpPost httpPost = new HttpPost(
			new URL(lxcDXPServerProtocol + "://" + lxcDXPMainDomain) +
				"/o/oauth2/token");

		httpPost.setEntity(
			new UrlEncodedFormEntity(
				Arrays.asList(
					new BasicNameValuePair("client_id", _dxpAuthClientId),
					new BasicNameValuePair(
						"client_secret", _dxpAuthClientSecret),
					new BasicNameValuePair(
						"grant_type", "client_credentials"))));
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build();
			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost)) {

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				throw new Exception("Unable to get OAuth access token");
			}

			JSONObject jsonObject = new JSONObject(
				EntityUtils.toString(
					closeableHttpResponse.getEntity(),
					Charset.defaultCharset()));

			_oauthAccessToken =
				jsonObject.getString("token_type") + " " +
					jsonObject.getString("access_token");
			_oauthExpirationMillis =
				(jsonObject.getLong("expires_in") * 1000) +
					System.currentTimeMillis();

			return _oauthAccessToken;
		}
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

	private String _getProductVersion(Long skuId) {
		String version = "1.0.0";

		try {
			Sku sku = _skuResource.getSku(skuId);

			for (com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.
					CustomField customField : sku.getCustomFields()) {

				if (Objects.equals(customField.getName(), "Version")) {
					version = customField.getCustomValue(
					).getData(
					).toString();

					break;
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to get product version " + exception.getMessage());
		}

		return version;
	}

	private void _initResourceBuilders() throws Exception {
		String oAuthAccessToken = _getOAuthAccessToken();

		URL liferayDXPURL = new URL(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain);

		_accountResource = AccountResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, oAuthAccessToken
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

		_koroneikiProductResource =
			com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.
				ProductResource.builder(
				).header(
					"API_TOKEN", _koroneikiAuthToken
				).endpoint(
					liferayMarketplaceKoroneikiAuthURL
				).build();

		_orderItemResource = OrderItemResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, oAuthAccessToken
		).endpoint(
			liferayDXPURL
		).build();

		_orderResource = OrderResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, oAuthAccessToken
		).endpoint(
			liferayDXPURL
		).build();

		_postalAddressResource = PostalAddressResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, oAuthAccessToken
		).endpoint(
			liferayDXPURL
		).build();

		_productResource = ProductResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, oAuthAccessToken
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

		_productSpecificationResource = ProductSpecificationResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, oAuthAccessToken
		).endpoint(
			liferayDXPURL
		).build();

		_skuResource = SkuResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, oAuthAccessToken
		).endpoint(
			liferayDXPURL
		).build();
	}

	private void _postAccountAccountKeyProductPurchase(
			Account account, Jwt jwt, OrderItem orderItem,
			Map<String, String> productSpecificationsMap)
		throws Exception {

		ProductPurchase productPurchase = new ProductPurchase();

		ZonedDateTime zonedDateTime = ZonedDateTime.now();

		if (Objects.equals(
				productSpecificationsMap.get("license-type"), "Subscription")) {

			Instant instant = zonedDateTime.plusYears(
				1
			).toInstant();

			if (Objects.equals(
					_getDXPLicenseUsageType(orderItem.getOptions()), "trial")) {

				instant = zonedDateTime.plusMonths(
					1
				).toInstant();
			}

			productPurchase.setEndDate(Date.from(instant));
		}

		ExternalLink externalLink = new ExternalLink();

		externalLink.setDomain("salesforce");
		externalLink.setEntityId(String.valueOf(orderItem.getOrderId()));
		externalLink.setEntityName("opportunity");

		productPurchase.setExternalLinks(new ExternalLink[] {externalLink});

		productPurchase.setPerpetual(
			Objects.equals(
				productSpecificationsMap.get("license-type"), "Perpetual"));
		productPurchase.setProductKey(orderItem.getSkuExternalReferenceCode());
		productPurchase.setQuantity(
			orderItem.getQuantity(
			).intValue());
		productPurchase.setStartDate(Date.from(zonedDateTime.toInstant()));
		productPurchase.setStatus(ProductPurchase.Status.APPROVED);

		productPurchase =
			_productPurchaseResource.postAccountAccountKeyProductPurchase(
				jwt.getClaim("username"), jwt.getClaim("sub"),
				account.getExternalReferenceCode(), productPurchase);

		if (_log.isInfoEnabled()) {
			_log.info("Created account product purchase " + productPurchase);
		}
	}

	private com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
			_postKoroneikiAccount(Account account, Jwt jwt)
		throws Exception {

		String code = account.getName(
		).replaceAll(
			StringPool.SPACE, StringPool.BLANK
		).toUpperCase();

		com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page
			<com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account>
				koroneikiAccountResourceAccountsPage =
					_koroneikiAccountResource.getAccountsPage(
						"", "code eq '" + code + "'",
						com.liferay.osb.koroneiki.phloem.rest.client.pagination.
							Pagination.of(1, 5),
						"");

		com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
			koroneikiAccount =
				koroneikiAccountResourceAccountsPage.fetchFirstItem();

		if (koroneikiAccount != null) {
			return koroneikiAccount;
		}

		koroneikiAccount =
			new com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account();

		koroneikiAccount.setCode(code);

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

	private static final int _COMMERCE_ORDER_STATUS_COMPLETED = 0;

	private static final int _COMMERCE_ORDER_STATUS_PAYMENT_COMPLETED = 0;

	private static final int _COMMERCE_ORDER_STATUS_PROCESSING = 10;

	private static final Log _log = LogFactory.getLog(
		KoroneikiRestController.class);

	private AccountResource _accountResource;

	@Value("${liferay.marketplace.dxp.auth.client.id}")
	private String _dxpAuthClientId;

	@Value("${liferay.marketplace.dxp.auth.client.secret}")
	private String _dxpAuthClientSecret;

	private
		com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.
			AccountResource _koroneikiAccountResource;

	@Value("${liferay.marketplace.koroneiki.auth.token}")
	private String _koroneikiAuthToken;

	@Value("${liferay.marketplace.koroneiki.auth.url}")
	private String _koroneikiAuthURL;

	private
		com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.
			ProductResource _koroneikiProductResource;
	private String _oauthAccessToken;
	private long _oauthExpirationMillis;
	private OrderItemResource _orderItemResource;
	private OrderResource _orderResource;
	private PostalAddressResource _postalAddressResource;
	private ProductPurchaseResource _productPurchaseResource;
	private ProductPurchaseViewResource _productPurchaseViewResource;
	private ProductResource _productResource;
	private ProductSpecificationResource _productSpecificationResource;
	private SkuResource _skuResource;

}