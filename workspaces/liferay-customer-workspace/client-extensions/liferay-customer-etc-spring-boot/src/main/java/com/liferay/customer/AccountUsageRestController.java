/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.customer.model.AccountUsage;
import com.liferay.customer.service.GoogleCloudFunctionService;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;

import java.net.URL;

import java.util.Random;

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
			AccountResource accountResource = _getAccountResource(jwt);

			Account account = accountResource.getAccountByExternalReferenceCode(
				externalReferenceCode);

			AccountUsage accountUsage = new AccountUsage();

			accountUsage.setAccountId(account.getId());

			JSONObject jsonObject =
				_googleCloudFunctionService.getCustomerAccountUsage(
					externalReferenceCode);

			Random random = new Random();

			int apvsMax = jsonObject.getInt("totalAnonymousPageViewsCount");

			accountUsage.setAPVsMax(apvsMax);
			accountUsage.setAPVsUsed(random.nextInt(apvsMax));

			int malusMax = jsonObject.getInt("totalActiveLoggedInUsersCount");

			accountUsage.setMALUsMax(malusMax);
			accountUsage.setMALUsUsed(random.nextInt(malusMax));

			int sitesMax = jsonObject.getInt("totalSitesCount");

			accountUsage.setSitesMax(sitesMax);
			accountUsage.setSitesUsed(random.nextInt(sitesMax));

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

	private AccountResource _getAccountResource(Jwt jwt) throws Exception {
		return AccountResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			new URL(getWebClientBaseURL())
		).build();
	}

	private static final Log _log = LogFactory.getLog(
		AccountUsageRestController.class);

	@Autowired
	private GoogleCloudFunctionService _googleCloudFunctionService;

}