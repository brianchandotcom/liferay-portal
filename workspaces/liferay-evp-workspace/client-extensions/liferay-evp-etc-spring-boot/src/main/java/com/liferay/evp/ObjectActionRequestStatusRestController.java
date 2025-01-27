/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.evp;

import com.liferay.client.extension.util.spring.boot2.BaseRestController;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;

import org.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * @author Elvison Victor
 */
@RequestMapping("/object/action/request/status")
@RestController
public class ObjectActionRequestStatusRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryDTOEVPRequestJSONObject =
			jsonObject.getJSONObject("objectEntryDTOEVPRequest");

		JSONObject propertiesJSONObject =
			objectEntryDTOEVPRequestJSONObject.getJSONObject("properties");

		long evpOrganizationId = propertiesJSONObject.getLong(
			"r_organization_c_evpOrganizationId");

		JSONObject evpOrganizationJSONObject = new JSONObject(
			get(
				jwt.toString(),
				_defaultUriBuilderFactory.builder(
				).path(
					"/o/c/evporganizations/" + evpOrganizationId
				).build(
				).toString()));

		String organizationStatus = evpOrganizationJSONObject.getJSONObject(
			"organizationStatus"
		).getString(
			"key"
		);

		if (organizationStatus.equals("awaitingApprovalOnEVP")) {
			put(
				jwt.toString(),
				new JSONObject(
					HashMapBuilder.<String, HashMap<String, String>>put(
						"requestStatus",
						HashMapBuilder.put(
							"key", "awaitOrganizationReview"
						).put(
							"name", "Awaiting Organization Review"
						).build()
					).build()
				).toString(),
				_defaultUriBuilderFactory.builder(
				).path(
					"/o/c/evprequests/" +
						objectEntryDTOEVPRequestJSONObject.getLong("id")
				).build(
				).toString());
		}

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private final DefaultUriBuilderFactory _defaultUriBuilderFactory =
		new DefaultUriBuilderFactory();

}