/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.AccountRole;
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountRoleResource;
import com.liferay.headless.admin.user.client.resource.v1_0.PostalAddressResource;
import com.liferay.headless.admin.user.client.resource.v1_0.UserAccountResource;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Catalog;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Currency;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.CurrencyResource;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderItemResource;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.model.PublisherAssetLink;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.marketplace.util.MarketplaceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import java.math.BigDecimal;

import java.net.URL;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * @author Keven Leone
 */
@RequestMapping("/marketplace")
@RestController
public class MarketplaceRestController extends BaseRestController {

	@GetMapping("orders/export")
	public ResponseEntity<StreamingResponseBody> getOrdersExport(
			@RequestParam(defaultValue = "", name = "filters", required = false)
				String filterString)
		throws Exception {

		StreamingResponseBody streamingResponseBody = outputStream -> {
			try (CSVPrinter csvPrinter = new CSVPrinter(
					new BufferedWriter(new OutputStreamWriter(outputStream)),
					CSVFormat.DEFAULT.builder(
					).setHeader(
						"Account ERC", "Account Name", "Create Date",
						"Creator Email", "Order ID", "Order Type",
						"Product Name", "Total"
					).build())) {

				OrderResource orderResource =
					_marketplaceService.getOrderResource();

				for (int i = 1;; i++) {
					Page<Order> page = orderResource.getOrdersPage(
						"", filterString, Pagination.of(i, 200), "");

					for (Order order : page.getItems()) {
						String orderItemName = "";

						for (OrderItem orderItem : order.getOrderItems()) {
							orderItemName = orderItem.getName(
							).get(
								"en_US"
							);

							break;
						}

						com.liferay.headless.commerce.admin.order.client.dto.
							v1_0.Account account = order.getAccount();

						csvPrinter.printRecord(
							account.getExternalReferenceCode(),
							account.getName(), order.getCreateDate(),
							order.getCreatorEmailAddress(), order.getId(),
							order.getOrderTypeExternalReferenceCode(),
							orderItemName, order.getTotalFormatted());
					}

					if (i >= page.getLastPage()) {
						break;
					}
				}

				csvPrinter.flush();
			}
			catch (Exception exception) {
				throw new IOException(exception);
			}
		};

		return ResponseEntity.ok(
		).header(
			HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders.csv"
		).contentType(
			MediaType.TEXT_PLAIN
		).body(
			streamingResponseBody
		);
	}

	@PostMapping("/account")
	public ResponseEntity<Account> postAccount(
			@RequestPart("account") String accountJSON,
			@RequestPart(name = "file", required = false) MultipartFile file,
			@AuthenticationPrincipal Jwt jwt)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("POST account " + accountJSON);
		}

		Account account = Account.toDTO(accountJSON);

		if (file != null) {
			Base64.Encoder encoder = Base64.getEncoder();

			account.setLogoBase64(
				() -> encoder.encodeToString(file.getBytes()));
		}

		AccountResource accountResource =
			_marketplaceService.getAccountResource();

		com.liferay.headless.admin.user.client.pagination.Page<Account>
			accountsPage = accountResource.getAccountsPage(
				"", "name eq '" + account.getName() + "'",
				com.liferay.headless.admin.user.client.pagination.Pagination.of(
					1, 1),
				"");

		if (accountsPage.getTotalCount() > 0) {
			throw new ResponseStatusException(
				HttpStatus.CONFLICT, "Account already exists");
		}

		account = accountResource.postAccount(account);

		PostalAddressResource postalAddressesResource =
			_marketplaceService.getPostalAddressResource();

		PostalAddress postalAddress =
			postalAddressesResource.getAccountPostalAddressesPage(
				account.getId()
			).fetchFirstItem();

		if (postalAddress != null) {
			accountResource.patchAccount(
				account.getId(),
				new Account() {
					{
						setDefaultBillingAddressId(postalAddress::getId);
					}
				});
		}

		UserAccountResource userAccountResource =
			_marketplaceService.getUserAccountResource();

		UserAccount userAccount = userAccountResource.getUserAccount(
			GetterUtil.getLong(jwt.getClaimAsString("sub")));

		String emailAddress = userAccount.getEmailAddress();

		userAccountResource.postAccountUserAccountByEmailAddress(
			account.getId(), emailAddress);

		Long accountRoleId = _getAccountAdministratorRoleId(account.getId());

		if (accountRoleId != null) {
			AccountRoleResource accountRoleResource =
				_marketplaceService.getAccountRoleResource();

			accountRoleResource.
				postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
					account.getExternalReferenceCode(), accountRoleId,
					emailAddress);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"User ", emailAddress, " associated with account ",
					account.getName()));
		}

		return ResponseEntity.ok(account);
	}

	@PostMapping("product/purchase")
	public void postProductPurchase(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("POST product purchase " + json);
		}

		JSONObject jsonObject = new JSONObject(json);

		JSONObject commerceOrderJSONObject = jsonObject.getJSONObject(
			"commerceOrder");

		String paymentMethod = commerceOrderJSONObject.getString(
			"paymentMethod");
		int paymentStatus = commerceOrderJSONObject.getInt("paymentStatus");

		Order order = _marketplaceService.getOrder(
			commerceOrderJSONObject.getLong("id"));

		if ((Objects.equals(
				paymentMethod,
				MarketplaceConstants.ORDER_PAYMENT_METHOD_MONEY_ORDER) &&
			 (paymentStatus ==
				 MarketplaceConstants.ORDER_PAYMENT_STATUS_PENDING)) ||
			(Objects.equals(
				paymentMethod,
				MarketplaceConstants.ORDER_PAYMENT_METHOD_PAYPAL) &&
			 (paymentStatus ==
				 MarketplaceConstants.ORDER_PAYMENT_STATUS_COMPLETED))) {

			_sendOrderPurchasedNotification(order);
		}

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

		if (Objects.equals(orderTypeExternalReferenceCode, "ADDONS")) {
			_setUpAddOns(jwt, order);

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

			Page<OrderItem> orderItemsPage =
				_marketplaceService.getOrderItemResource(
				).getOrderIdOrderItemsPage(
					order.getId(), Pagination.of(1, 10)
				);

			Map<String, String> productSpecificationsMap =
				_marketplaceService.getProductSpecificationsMap(
					_marketplaceService.getSku(
						orderItemsPage.fetchFirstItem(
						).getSkuId()
					).getProductId());

			if (Objects.equals(
					productSpecificationsMap.get("price-model"), "Free")) {

				_marketplaceService.updateOrder(
					null, order.getId(),
					MarketplaceConstants.ORDER_STATUS_COMPLETED);

				return;
			}

			_setUpProductEntitlements(
				jwt, productSpecificationsMap.get("license-type"), order,
				orderItemsPage);
		}
	}

	@PostMapping("product/submit")
	public void postProductSubmit(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("POST product submit " + json);
		}

		JSONObject jsonObject = new JSONObject(json);

		JSONObject modelCPDefinitionJSONObject = jsonObject.getJSONObject(
			"modelCPDefinition");

		Product product = _marketplaceService.getProduct(
			modelCPDefinitionJSONObject.getLong("CProductId"));

		_marketplaceService.postNotificationQueueEntry(
			null, "MARKETPLACE-PRODUCT-SUBMIT-TEMPLATE",
			new HashMapBuilder<String, Object>().put(
				"[%CPDEFINITION_NAME%]",
				product.getName(
				).get(
					modelCPDefinitionJSONObject.getString("defaultLanguageId")
				)
			).put(
				"[%CPDEFINITION_THUMBNAIL%]",
				new URL(
					"http://" + lxcDXPMainDomain + product.getThumbnail()
				).toString()
			).put(
				"[%CPDEFINITION_DEVELOPER_NAME%]",
				_marketplaceService.getCatalog(
					product.getCatalogId()
				).getName()
			).put(
				"[%CPDEFINITION_URL%]",
				new URL(
					StringBundler.concat(
						lxcDXPServerProtocol, "://", lxcDXPMainDomain,
						"/web/marketplace/administrator-dashboard#/apps/",
						modelCPDefinitionJSONObject.getLong("CProductId"))
				).toString()
			).put(
				"[%CPDEFINITION_CREATEDATE%]",
				ZonedDateTime.ofInstant(
					product.getCreateDate(
					).toInstant(),
					ZoneOffset.UTC
				).format(
					DateTimeFormatter.ofPattern("MMMM d, yyyy")
				)
			).put(
				"[%CPDEFINITION_ID%]",
				String.valueOf(
					modelCPDefinitionJSONObject.getLong("CPDefinitionId"))
			).build());
	}

	@PostMapping("/tax-calculate/{orderId}")
	public void postTaxCalculate(@PathVariable long orderId) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("POST tax calculate for order " + orderId);
		}

		Order order = _marketplaceService.getOrder(orderId);

		BillingAddress billingAddress = _marketplaceService.getBillingAddress(
			orderId);

		if (billingAddress == null) {
			return;
		}

		OrderItemResource orderItemResource =
			_marketplaceService.getOrderItemResource();
		OrderResource orderResource = _marketplaceService.getOrderResource();

		com.liferay.headless.commerce.admin.order.client.dto.v1_0.Account
			account = order.getAccount();

		BigDecimal subtotalAmount = BigDecimal.valueOf(
			order.getSubtotalAmount());

		BigDecimal taxAmount = BigDecimal.ZERO;

		BigDecimal total = subtotalAmount.add(taxAmount);

		if ((Objects.equals(account.getType(), _ACCOUNT_TYPE_BUSINESS) &&
			 Objects.equals(billingAddress.getCountryISOCode(), "IE")) ||
			(Objects.equals(account.getType(), _ACCOUNT_TYPE_PERSON) &&
			 _europeanCountriesISOCode.contains(
				 billingAddress.getCountryISOCode()))) {

			taxAmount = subtotalAmount.multiply(
				BigDecimal.valueOf(_MARKETPLACE_TAX_PERCENTAGE));

			total = subtotalAmount.add(taxAmount);
		}

		BigDecimal finalTaxAmount = taxAmount;
		BigDecimal finalTotal = total;

		for (OrderItem orderItem : order.getOrderItems()) {
			orderItemResource.patchOrderItem(
				orderItem.getId(),
				new OrderItem() {
					{
						setFinalPrice(orderItem::getFinalPrice);
						setFinalPriceWithTaxAmount(
							() -> orderItem.getFinalPrice(
							).add(
								orderItem.getFinalPrice(
								).multiply(
									BigDecimal.valueOf(
										_MARKETPLACE_TAX_PERCENTAGE)
								)
							));
						setPriceManuallyAdjusted(() -> true);
					}
				});
		}

		_setExchangeRate(order);

		orderResource.patchOrder(
			orderId,
			new Order() {
				{
					setCustomFields(order::getCustomFields);
					setTaxAmount(() -> finalTaxAmount);
					setTotal(() -> finalTotal);
				}
			});
	}

	@PostMapping("/process-publisher-asset-links/{productId}")
	public void processPublisherAssetLinks(@PathVariable long productId) {
		try {
			Product product = _marketplaceService.getProduct(productId);

			Map<String, String> productSpecificationsMap =
				_marketplaceService.getProductSpecificationsMap(productId);

			if (Objects.equals(productSpecificationsMap.get("type"), "dxp")) {
				return;
			}

			List<PublisherAssetLink> publisherAssetLinks =
				_getPublisherAssetLinks(
					_marketplaceService.getPublisherAssetsJSONObject(
						productId));

			if (publisherAssetLinks.isEmpty()) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"No publisher asset link found for product " +
							productId);
				}

				return;
			}

			for (PublisherAssetLink publisherAssetLink : publisherAssetLinks) {
				_processPublisherAssetLink(
					product, productSpecificationsMap, publisherAssetLink);
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to upload publisher asset link for product " +
					productId,
				exception);
		}
	}

	private Long _getAccountAdministratorRoleId(long accountId)
		throws Exception {

		AccountRoleResource accountRoleResource =
			_marketplaceService.getAccountRoleResource();

		com.liferay.headless.admin.user.client.pagination.Page<AccountRole>
			accountRolesPage = accountRoleResource.getAccountAccountRolesPage(
				accountId, null, "name eq 'Account Administrator'",
				com.liferay.headless.admin.user.client.pagination.Pagination.of(
					1, 1),
				null);

		AccountRole accountRole = accountRolesPage.fetchFirstItem();

		if (accountRole == null) {
			return null;
		}

		return accountRole.getId();
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

	private File _getPublisherAssetFile(String publisherAssetURL)
		throws Exception {

		File tempFile = File.createTempFile("publisher_asset_", ".zip");

		try (InputStream inputStream =
				_marketplaceService.getPublisherAssetInputStream(
					publisherAssetURL);
			FileOutputStream fileOutputStream = new FileOutputStream(
				tempFile)) {

			inputStream.transferTo(fileOutputStream);
		}

		return tempFile;
	}

	private List<PublisherAssetLink> _getPublisherAssetLinks(
		JSONObject jsonObject) {

		List<PublisherAssetLink> publisherAssetLinks = new ArrayList<>();

		JSONArray itemsJSONArray = jsonObject.optJSONArray("items");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			JSONArray attachmentsJSONArray = itemJSONObject.getJSONArray(
				"publisherAssetsToAttachment");

			for (int j = 0; j < attachmentsJSONArray.length(); j++) {
				JSONObject attachmentJSONObject =
					attachmentsJSONArray.getJSONObject(j);

				if (attachmentJSONObject.getBoolean("processed")) {
					continue;
				}

				JSONObject sourceCodeJSONObject =
					attachmentJSONObject.getJSONObject("sourceCode");

				JSONObject linkJSONObject = sourceCodeJSONObject.getJSONObject(
					"link");

				publisherAssetLinks.add(
					new PublisherAssetLink(
						attachmentJSONObject.getLong("id"),
						sourceCodeJSONObject.getString("name"),
						linkJSONObject.getString("href"),
						itemJSONObject.optString("version", "")));
			}
		}

		return publisherAssetLinks;
	}

	private void _processPublisherAssetLink(
			Product product, Map<String, String> productSpecificationsMap,
			PublisherAssetLink publisherAssetLink)
		throws Exception {

		File publisherAssetArtifactFile = null;
		File publisherAssetFile = null;

		try {
			publisherAssetFile = _getPublisherAssetFile(
				publisherAssetLink.getHref());

			publisherAssetArtifactFile = MarketplaceUtil.addArtifactMetadata(
				publisherAssetFile, publisherAssetLink.getFileName(),
				MarketplaceUtil.getArtifactProperties(
					product, productSpecificationsMap, publisherAssetLink));

			_marketplaceService.postVirtualFileEntry(
				publisherAssetArtifactFile, product.getProductId(),
				publisherAssetLink.getVersion());

			if (Objects.equals(productSpecificationsMap.get("type"), "cloud")) {
				_marketplaceService.postProductAttachment(
					publisherAssetArtifactFile,
					publisherAssetLink.getFileName(), product.getProductId());
			}

			_marketplaceService.patchPublisherAssetAttachment(
				new JSONObject(
				).put(
					"processed", true
				).toString(),
				publisherAssetLink.getAttachmentId());
		}
		finally {
			MarketplaceUtil.deleteTempFile(publisherAssetFile, false);

			MarketplaceUtil.deleteTempFile(publisherAssetArtifactFile, true);
		}
	}

	private void _sendOrderPurchasedNotification(Order order) throws Exception {
		OrderItem[] orderItems = order.getOrderItems();

		OrderItem orderItem = orderItems[0];

		if (orderItem == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Sending purchased order notification for order " +
					order.getId());
		}

		com.liferay.headless.commerce.admin.order.client.dto.v1_0.Account
			account = order.getAccount();

		BillingAddress billingAddress = _marketplaceService.getBillingAddress(
			order.getId());

		Product product = _marketplaceService.getProductBySkuId(
			orderItem.getSkuId());

		Catalog catalog = _marketplaceService.getCatalog(
			product.getCatalogId());

		Map<String, String> productSpecificationsMap =
			_marketplaceService.getProductSpecificationsMap(
				product.getProductId());

		_marketplaceService.postNotificationQueueEntry(
			null, "MARKETPLACE-ORDER-PURCHASED-NOTIFICATION",
			new HashMapBuilder<String, String>().put(
				"[%ACCOUNT_ID%]", String.valueOf(account.getId())
			).put(
				"[%ACCOUNT_NAME%]", account.getName()
			).put(
				"[%APP_NAME%]",
				product.getName(
				).get(
					"en_US"
				)
			).put(
				"[%APP_TYPE%]",
				productSpecificationsMap.get(
					"type"
				).replace(
					"-", " "
				)
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
				"[%CATALOG_NAME%]", catalog.getName()
			).put(
				"[%EMAIL_ADDRESS%]", order.getCreatorEmailAddress()
			).put(
				"[%EXCHANGE_RATE%]", _getExchangeRate(order)
			).put(
				"[%LICENSE_TYPE%]", productSpecificationsMap.get("license-type")
			).put(
				"[%NET_PRICE_FORMATTED%]", order.getSubtotalFormatted()
			).put(
				"[%ORDER_DATE%]",
				ZonedDateTime.ofInstant(
					order.getCreateDate(
					).toInstant(),
					ZoneOffset.UTC
				).format(
					DateTimeFormatter.ofPattern("MMMM d, yyyy")
				)
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
				"[%PAYMENT_TERMS%]", order.getPaymentTermDescription()
			).put(
				"[%PRODUCT_THUMBNAIL%]",
				new URL(
					StringBundler.concat(
						lxcDXPServerProtocol, "://", lxcDXPMainDomain,
						product.getThumbnail())
				).toString(
				).replaceAll(
					"(?<=accounts/)-?\\d+(?=/images)", "-1"
				)
			).put(
				"[%TOTAL_FORMATTED%]", order.getTotalFormatted()
			).put(
				"[%VAT_FORMATTED%]", order.getTaxAmountFormatted()
			).put(
				"[%VAT_NUMBER%]", account.getTaxId()
			).build());
	}

	private void _setExchangeRate(Order order) throws Exception {
		Map<String, String> customFields =
			(Map<String, String>)order.getCustomFields();

		JSONObject orderMetadataJSONObject = new JSONObject(
			customFields.getOrDefault("order-metadata", "{}"));

		if (orderMetadataJSONObject.has("exchangeRate")) {
			return;
		}

		CurrencyResource currencyResource =
			_marketplaceService.getCurrencyResource();

		com.liferay.headless.commerce.admin.catalog.client.pagination.Page
			<Currency> currenciesPage = currencyResource.getCurrenciesPage(
				null, "code eq 'EUR'",
				com.liferay.headless.commerce.admin.catalog.client.pagination.
					Pagination.of(1, 1),
				null);

		Currency currency = currenciesPage.fetchFirstItem();

		if (currency == null) {
			return;
		}

		customFields.put(
			"order-metadata",
			orderMetadataJSONObject.put(
				"exchangeRate", currency.getRate()
			).toString());
	}

	private void _setUpAddOns(Jwt jwt, Order order) throws Exception {
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

	private void _setUpProductEntitlements(
			Jwt jwt, String licenseType, Order order,
			Page<OrderItem> orderItemsPage)
		throws Exception {

		String accountExternalReferenceCode =
			order.getAccountExternalReferenceCode();

		if (!accountExternalReferenceCode.startsWith("KOR-")) {
			AccountResource accountResource =
				_marketplaceService.getAccountResource();

			Account account = accountResource.getAccount(order.getAccountId());

			account.setExternalReferenceCode(
				() -> _koroneikiService.postKoroneikiAccount(
					account, jwt
				).getKey());

			accountResource.patchAccount(account.getId(), account);
		}

		try {
			for (OrderItem orderItem : orderItemsPage.getItems()) {
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

	private static final int _ACCOUNT_TYPE_BUSINESS = 2;

	private static final int _ACCOUNT_TYPE_PERSON = 1;

	private static final double _MARKETPLACE_TAX_PERCENTAGE = 0.23;

	private static final Log _log = LogFactory.getLog(
		MarketplaceRestController.class);

	private final Set<String> _europeanCountriesISOCode = Set.of(
		"AT", "BE", "BG", "CY", "CZ", "DE", "DK", "EE", "ES", "FI", "FR", "GR",
		"HR", "HU", "IE", "IT", "LT", "LU", "LV", "MT", "NL", "PL", "PT", "RO",
		"SE", "SI", "SK");

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Autowired
	private MarketplaceService _marketplaceService;

}