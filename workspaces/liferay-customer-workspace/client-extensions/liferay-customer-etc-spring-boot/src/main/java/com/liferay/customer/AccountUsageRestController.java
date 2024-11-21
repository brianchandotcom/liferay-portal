/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.customer.model.AccountUsage;
import com.liferay.customer.service.GoogleCloudFunctionService;
import com.liferay.customer.service.KoroneikiService;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.petra.string.StringPool;

import java.net.URL;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Amos Fong
 */
@RequestMapping("/accounts/{externalReferenceCode}/usage")
@RestController
public class AccountUsageRestController extends BaseRestController {

	@GetMapping
	public ResponseEntity<String> get(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		try {
			_checkPermission(jwt, externalReferenceCode);

			List<ProductPurchase> productPurchases =
				_koroneikiService.searchProductPurchases(
					"accountKey eq '" + externalReferenceCode +
						"' and state eq 'Active'",
					1, 1000, StringPool.BLANK);

			JSONObject jsonObject =
				_googleCloudFunctionService.getCustomerAccountUsage(
					externalReferenceCode);

			AccountUsage accountUsage = new AccountUsage(
				productPurchases, jsonObject);

			JSONObject accountUsageJSONObject = accountUsage.toJSONObject();

			return new ResponseEntity<>(
				accountUsageJSONObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void _checkPermission(Jwt jwt, String externalReferenceCode)
		throws Exception {

		AccountResource accountResource = AccountResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			new URL(getWebClientBaseURL())
		).build();

		accountResource.getAccountByExternalReferenceCode(
			externalReferenceCode);
	}

	private static final Log _log = LogFactory.getLog(
		AccountUsageRestController.class);

	@Autowired
	private GoogleCloudFunctionService _googleCloudFunctionService;

	@Autowired
	private KoroneikiService _koroneikiService;

}