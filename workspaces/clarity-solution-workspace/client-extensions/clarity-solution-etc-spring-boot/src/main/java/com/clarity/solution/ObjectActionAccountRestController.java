/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.clarity.solution;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import org.apache.commons.lang3.StringUtils;

import org.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author dnebing
 * @author Mumen Tayyem
 */
@RequestMapping("/object/action/account")
@RestController
public class ObjectActionAccountRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject propertiesJSONObject = jsonObject.getJSONObject(
			"objectEntryDTOU3A2DistributorApplication"
		).getJSONObject(
			"properties"
		);

		String accountName = propertiesJSONObject.getString("businessName");
		String accountEmailAddress = propertiesJSONObject.getString(
			"applicantEmailAddress");

		String standardAccountName = StringUtils.replace(
			StringUtil.toUpperCase(accountName), " ", "_");

		String accountExternalReferenceCode = "ACCOUNT_" + standardAccountName;

		String authorizationHeader = "Bearer " + jwt.getTokenValue();

		post(
			authorizationHeader,
			new JSONObject(
			).put(
				"externalReferenceCode", accountExternalReferenceCode
			).put(
				"name", accountName
			).put(
				"type", "business"
			).toString(),
			UriComponentsBuilder.fromUriString(
				"/o/headless-admin-user/v1.0/accounts"
			).build(
			).toUri());

		post(
			authorizationHeader, "",
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					"/o/headless-admin-user/v1.0/accounts",
					"/by-external-reference-code/",
					accountExternalReferenceCode,
					"/user-accounts/by-email-address/", accountEmailAddress)
			).build(
			).toUri());

		long adminAccountRoleId = new JSONObject(
			get(
				authorizationHeader,
				UriComponentsBuilder.fromUriString(
					StringBundler.concat(
						"/o/headless-admin-user/v1.0/accounts",
						"/by-external-reference-code/",
						accountExternalReferenceCode, "/account-roles",
						"?filter=name eq 'Account Administrator'")
				).build(
				).toUri())
		).getJSONArray(
			"items"
		).getJSONObject(
			0
		).getLong(
			"id"
		);

		post(
			authorizationHeader, "",
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					"/o/headless-admin-user/v1.0/accounts",
					"/by-external-reference-code/",
					accountExternalReferenceCode, "/account-roles/",
					adminAccountRoleId, "/user-accounts/by-email-address/",
					accountEmailAddress)
			).build(
			).toUri());

		return new ResponseEntity<>(HttpStatus.OK);
	}

}