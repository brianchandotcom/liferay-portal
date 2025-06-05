/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.evp;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.petra.string.StringBundler;

import java.util.Objects;

import org.json.JSONArray;
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
 * @author Elvison Victor
 */
@RequestMapping("/object/action/organization/status/update")
@RestController
public class ObjectActionOrganizationStatusRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryDTOEVPOrganizationJSONObject =
			jsonObject.getJSONObject("objectEntryDTOEVPOrganization");

		JSONObject responseJSONObject = new JSONObject(
			get(
				jwt.toString(),
				UriComponentsBuilder.fromPath(
					"/o/c/evprequests"
				).queryParam(
					"filter",
					StringBundler.concat(
						"r_organization_c_evpOrganizationId eq '",
						objectEntryDTOEVPOrganizationJSONObject.getLong("id"),
						"'")
				).build(
				).toUri()));

		if (responseJSONObject.getInt("totalCount") == 0) {
			return new ResponseEntity<>(json, HttpStatus.OK);
		}

		JSONObject propertiesJSONObject =
			objectEntryDTOEVPOrganizationJSONObject.getJSONObject("properties");

		JSONObject organizationStatusJSONObject =
			propertiesJSONObject.getJSONObject("organizationStatus");

		JSONArray itemsJSONArray = responseJSONObject.getJSONArray("items");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			JSONObject requestStatusJSONObject = itemJSONObject.getJSONObject(
				"requestStatus");

			if (Objects.equals(
					organizationStatusJSONObject.getString("key"),
					"rejected")) {

				requestStatusJSONObject.put(
					"key", "rejected"
				).put(
					"name", "Rejected"
				);
			}
			else if (Objects.equals(
						organizationStatusJSONObject.getString("key"),
						"verified")) {

				JSONObject requestTypeJSONObject = itemJSONObject.getJSONObject(
					"requestType");

				if (Objects.equals(
						requestTypeJSONObject.getString("key"), "grant")) {

					requestStatusJSONObject.put(
						"key", "awaitingApprovalOnEVP"
					).put(
						"name", "Awaiting Approval On EVP"
					);
				}
				else {
					requestStatusJSONObject.put(
						"key", "awaitingApprovalOnManager"
					).put(
						"name", "Awaiting Approval on Manager"
					);
				}
			}
		}

		put(
			jwt.toString(), itemsJSONArray.toString(),
			UriComponentsBuilder.fromPath(
				"/o/c/evprequests/batch"
			).build(
			).toUri());

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

}