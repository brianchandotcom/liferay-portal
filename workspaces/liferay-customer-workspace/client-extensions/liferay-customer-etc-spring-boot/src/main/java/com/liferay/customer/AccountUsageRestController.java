/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.customer.model.AccountUsage;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;

import java.net.URL;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

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
@RequestMapping("/accounts/{id}/usage")
@RestController
public class AccountUsageRestController extends BaseRestController {

	@GetMapping
	public ResponseEntity<String> get(
			@AuthenticationPrincipal Jwt jwt, @PathVariable("id") long id)
		throws Exception {

		try {
			AccountResource accountResource = _getAccountResource(jwt);

			Account account = accountResource.getAccount(id);

			AccountUsage accountUsage = new AccountUsage();

			accountUsage.setAccountId(account.getId());

			Random random = new Random();

			int apvsMax = (random.nextInt(4) + 1) * 1000000;

			accountUsage.setAPVsMax(apvsMax);
			accountUsage.setAPVsUsed(random.nextInt(apvsMax));

			int malusMax = (random.nextInt(10) + 1) * 100000;

			accountUsage.setMALUsMax(malusMax);
			accountUsage.setMALUsUsed(random.nextInt(malusMax));

			int sitesMax = random.nextInt(19) + 1;

			accountUsage.setSitesMax(sitesMax);
			accountUsage.setSitesUsed(random.nextInt(sitesMax));

			JSONObject jsonObject = accountUsage.toJSONObject();

			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
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
			new URL(lxcDXPServerProtocol + "://" + lxcDXPMainDomain)
		).build();
	}

	private static final Log _log = LogFactory.getLog(
		AccountUsageRestController.class);

}