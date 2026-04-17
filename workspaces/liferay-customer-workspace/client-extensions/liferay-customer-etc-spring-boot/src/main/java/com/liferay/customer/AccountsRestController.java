/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.customer.constants.ProductConstants;
import com.liferay.customer.model.ExperienceUsageStrategy;
import com.liferay.customer.model.SaaSUsageStrategy;
import com.liferay.customer.model.UsageStrategy;
import com.liferay.customer.permission.BusinessEventPermission;
import com.liferay.customer.service.GoogleCloudFunctionService;
import com.liferay.customer.service.JiraService;
import com.liferay.customer.service.KoroneikiService;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jenny Chen
 * @author Amos Fong
 */
@RequestMapping("/accounts")
@RestController
public class AccountsRestController extends BaseRestController {

	@GetMapping("/{externalReferenceCode}/jira/object-key")
	public ResponseEntity<String> getJiraObjectKey(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.VIEW);

			return new ResponseEntity<>(
				_jiraService.getAccountObjectKey(externalReferenceCode),
				HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(
				"Error getting JIRA object key for " + externalReferenceCode,
				exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{externalReferenceCode}/usage")
	public ResponseEntity<String> getUsage(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		try {
			_checkPermissions(jwt, externalReferenceCode);

			List<ProductPurchase> productPurchases =
				_koroneikiService.searchProductPurchases(
					"accountKey eq '" + externalReferenceCode +
						"' and state eq 'Active'",
					1, 1000, StringPool.BLANK);

			UsageStrategy usageStrategy = _fetchUsageStrategy(
				externalReferenceCode, productPurchases);

			JSONObject usageStrategyJSONObject = usageStrategy.toJSONObject();

			return new ResponseEntity<>(
				usageStrategyJSONObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{externalReferenceCode}/synchronize")
	public ResponseEntity<String> postSynchronize(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		try {
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
				koroneikiAccount = _koroneikiService.fetchAccount(
					externalReferenceCode);

			if (koroneikiAccount == null) {
				return new ResponseEntity(
					"Unable to find account with key " + externalReferenceCode,
					HttpStatus.NOT_FOUND);
			}

			_updateAccount(jwt, koroneikiAccount);

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to synchronize account with key " +
					externalReferenceCode,
				exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void _checkPermissions(Jwt jwt, String externalReferenceCode)
		throws Exception {

		AccountResource accountResource = AccountResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).build();

		accountResource.getAccountByExternalReferenceCode(
			externalReferenceCode);
	}

	private UsageStrategy _fetchUsageStrategy(
			String accountKey, List<ProductPurchase> productPurchases)
		throws Exception {

		for (ProductPurchase productPurchase : productPurchases) {
			Product product = productPurchase.getProduct();

			String name = product.getName();

			if (ArrayUtil.contains(
					ProductConstants.SAAS_PLAN_SUBSCRIPTION_NAMES, name)) {

				return new SaaSUsageStrategy(
					productPurchases,
					_googleCloudFunctionService.fetchCustomerAccountUsage(
						accountKey));
			}

			if (name.equals(ProductConstants.NAME_PRODUCTION_ENVIRONMENT)) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

				return new ExperienceUsageStrategy(
					productPurchase,
					_googleCloudFunctionService.fetchCustomerAccountUsage(
						accountKey, dateFormat.format(new Date())));
			}
		}

		throw new Exception(
			"Unable to find a valid product for account with key: " +
				accountKey);
	}

	private void _updateAccount(
			Jwt jwt,
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
				koroneikiAccount)
		throws Exception {

		AccountResource accountResource = AccountResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			new URL(lxcDXPServerProtocol + "://" + lxcDXPMainDomain)
		).build();

		Account account = new Account();

		account.setDescription(koroneikiAccount::getDescription);
		account.setExternalReferenceCode(koroneikiAccount::getKey);
		account.setName(
			() -> StringUtil.shorten(koroneikiAccount.getName(), 99));

		accountResource.putAccountByExternalReferenceCode(
			koroneikiAccount.getKey(), account);
	}

	private static final Log _log = LogFactory.getLog(
		AccountsRestController.class);

	@Autowired
	private BusinessEventPermission _businessEventPermission;

	@Autowired
	private GoogleCloudFunctionService _googleCloudFunctionService;

	@Autowired
	private JiraService _jiraService;

	@Autowired
	private KoroneikiService _koroneikiService;

}