/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.headless.admin.user.client.custom.field.CustomField;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.marketplace.util.MarketplaceUtil;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Entitlement;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.PostalAddress;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Pagination;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.AccountResource;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ContactResource;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseResource;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseViewResource;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductResource;
import com.liferay.petra.string.StringPool;

import java.net.URL;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 */
@Component
public class KoroneikiService {

	public AccountResource getAccountResource() throws Exception {
		return AccountResource.builder(
		).header(
			"API_TOKEN", _koroneikiAuthToken
		).endpoint(
			new URL(_koroneikiAuthURL)
		).build();
	}

	public ContactResource getContactResource() throws Exception {
		return ContactResource.builder(
		).header(
			"API_TOKEN", _koroneikiAuthToken
		).endpoint(
			new URL(_koroneikiAuthURL)
		).build();
	}

	public Account getKoroneikiAccount(String accountKey) throws Exception {
		AccountResource accountResource = getAccountResource();

		return accountResource.getAccount(accountKey);
	}

	public ProductPurchase getProductPurchase(String productPurchaseKey)
		throws Exception {

		ProductPurchaseResource productPurchaseResource =
			getProductPurchaseResource();

		return productPurchaseResource.getProductPurchase(productPurchaseKey);
	}

	public ProductPurchaseResource getProductPurchaseResource()
		throws Exception {

		return ProductPurchaseResource.builder(
		).header(
			"API_TOKEN", _koroneikiAuthToken
		).endpoint(
			new URL(_koroneikiAuthURL)
		).parameters(
			"nestedFields", "productConsumptions"
		).build();
	}

	public ProductPurchaseViewResource getProductPurchaseViewResource()
		throws Exception {

		return ProductPurchaseViewResource.builder(
		).header(
			"API_TOKEN", _koroneikiAuthToken
		).endpoint(
			new URL(_koroneikiAuthURL)
		).build();
	}

	public ProductResource getProductResource() throws Exception {
		return ProductResource.builder(
		).header(
			"API_TOKEN", _koroneikiAuthToken
		).endpoint(
			new URL(_koroneikiAuthURL)
		).build();
	}

	public boolean hasEntitlement(
		Account koroneikiAccount, String[] entitlementNames) {

		for (Entitlement entitlement : koroneikiAccount.getEntitlements()) {
			for (String entitlementName : entitlementNames) {
				if (Objects.equals(entitlementName, entitlement.getName())) {
					return true;
				}
			}
		}

		return false;
	}

	public void linkProductPurchaseToOpportunity(
			Jwt jwt, String opportunity, String productPurchaseKey)
		throws Exception {

		ProductPurchaseResource productPurchaseResource =
			getProductPurchaseResource();

		ProductPurchase productPurchase =
			productPurchaseResource.getProductPurchase(productPurchaseKey);

		ExternalLink[] externalLinks = MarketplaceUtil.addExternalLink(
			productPurchase.getExternalLinks(), "salesforce", opportunity,
			"opportunity");

		productPurchase.setExternalLinks(externalLinks);

		productPurchaseResource.putProductPurchase(
			jwt.getClaim("username"), jwt.getClaim("sub"), productPurchaseKey,
			productPurchase);
	}

	public void postAccountAccountKeyProductPurchase(
			String accountKey, Jwt jwt, String licenseType,
			String licenseUsageType, OrderItem orderItem)
		throws Exception {

		ZonedDateTime zonedDateTime = ZonedDateTime.now();

		ProductPurchase productPurchase = new ProductPurchase();

		productPurchase.setPerpetual(Objects.equals(licenseType, "Perpetual"));

		if (Objects.equals(licenseUsageType, "trial")) {
			productPurchase.setEndDate(
				Date.from(
					zonedDateTime.plusMonths(
						1
					).toInstant()));

			productPurchase.setPerpetual(false);
		}
		else if (Objects.equals(licenseType, "Subscription")) {
			Instant instant = zonedDateTime.plusYears(
				1
			).toInstant();

			productPurchase.setEndDate(Date.from(instant));
		}

		ExternalLink[] externalLink = MarketplaceUtil.addExternalLink(
			productPurchase.getExternalLinks(), "marketplace",
			String.valueOf(orderItem.getOrderId()), "opportunity");

		productPurchase.setExternalLinks(externalLink);

		productPurchase.setProductKey(orderItem.getSkuExternalReferenceCode());
		productPurchase.setQuantity(
			orderItem.getQuantity(
			).intValue());
		productPurchase.setStartDate(Date.from(zonedDateTime.toInstant()));
		productPurchase.setStatus(ProductPurchase.Status.APPROVED);

		ProductPurchaseResource productPurchaseResource =
			getProductPurchaseResource();

		productPurchase =
			productPurchaseResource.postAccountAccountKeyProductPurchase(
				jwt.getClaim("username"), jwt.getClaim("sub"), accountKey,
				productPurchase);

		if (_log.isInfoEnabled()) {
			_log.info("Created account product purchase " + productPurchase);
		}
	}

	public Account postKoroneikiAccount(
			com.liferay.headless.admin.user.client.dto.v1_0.Account account,
			Jwt jwt)
		throws Exception {

		String code = account.getName(
		).replaceAll(
			StringPool.SPACE, StringPool.BLANK
		).toUpperCase();

		AccountResource accountResource = getAccountResource();

		Page<Account> page = accountResource.getAccountsPage(
			"", "code eq '" + code + "'", Pagination.of(1, 5), "");

		Account koroneikiAccount = page.fetchFirstItem();

		if (koroneikiAccount != null) {
			return koroneikiAccount;
		}

		koroneikiAccount = new Account();

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

		ExternalLink[] externalLinks = MarketplaceUtil.addExternalLink(
			koroneikiAccount.getExternalLinks(), "marketplace",
			account.getName(), "account");

		koroneikiAccount.setExternalLinks(externalLinks);

		koroneikiAccount.setName(account.getName());
		koroneikiAccount.setPhoneNumber(customFieldsMap.get("Contact Phone"));

		com.liferay.headless.admin.user.client.pagination.Page
			<com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress>
				postalAddressPage =
					_marketplaceService.getPostalAddressResource(
					).getAccountPostalAddressesPage(
						account.getId()
					);

		PostalAddress[] koroneikiPostalAddresses =
			new PostalAddress[(int)postalAddressPage.getTotalCount()];

		int i = 0;

		for (com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress
				postalAddress : postalAddressPage.getItems()) {

			koroneikiPostalAddresses[i] = PostalAddress.toDTO(
				postalAddress.toString());

			koroneikiPostalAddresses[i].setAddressType("");

			i++;
		}

		koroneikiAccount.setPostalAddresses(koroneikiPostalAddresses);

		koroneikiAccount.setStatus(Account.Status.ACTIVE);
		koroneikiAccount.setWebsite(customFieldsMap.get("Homepage URL"));

		return accountResource.postAccount(
			jwt.getClaim("username"), jwt.getClaim("sub"), koroneikiAccount);
	}

	private static final Log _log = LogFactory.getLog(KoroneikiService.class);

	@Value("${liferay.marketplace.koroneiki.auth.token}")
	private String _koroneikiAuthToken;

	@Value("${liferay.marketplace.koroneiki.auth.url}")
	private String _koroneikiAuthURL;

	@Autowired
	private MarketplaceService _marketplaceService;

}