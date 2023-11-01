/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.evp;

import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import com.liferay.petra.string.StringBundler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

		JSONObject evpOrganizationJSONObject = new JSONObject(json);

		JSONObject objectEntryDTOEVPOrganizationJSONObject =
			evpOrganizationJSONObject.getJSONObject(
				"objectEntryDTOEVPOrganization");

		JSONObject responseJSONObject = get(	
			jwt,
			uriBuilder -> uriBuilder.path(
				"/o/c/evprequests"
			).queryParam(
				"filter",StringBundler.concat(
				"r_organization_c_evpOrganizationId eq '"+
				objectEntryDTOEVPOrganizationJSONObject.getLong("id"),"'")
			).build());

		if (responseJSONObject.getInt("totalCount") == 0) {
			return new ResponseEntity<>(json, HttpStatus.OK);
		}

		JSONObject propertiesJSONObject =
			objectEntryDTOEVPOrganizationJSONObject.getJSONObject("properties");

		JSONObject evpOrganizationStatusJSONObject =
			propertiesJSONObject.getJSONObject("organizationStatus");

		JSONArray itemsJSONArray = responseJSONObject.getJSONArray("items");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			JSONObject evpRequestsStatusJSONObject =
				itemJSONObject.getJSONObject("requestStatus");

			if (Objects.equals(
					evpOrganizationStatusJSONObject.getString("key"),
					"verified")) {

				_setRequestStatus(
					evpRequestsStatusJSONObject,
					itemJSONObject.getJSONObject("requestType"));
			}
			else if (Objects.equals(
						evpOrganizationStatusJSONObject.getString("key"),
						"rejected")) {

				evpRequestsStatusJSONObject.put(
					"key", "rejected"
				).put(
					"name", "Rejected"
				);
			}
		}

		put(
			itemsJSONArray.toString(), jwt,
			uriBuilder -> uriBuilder.path(
				"/o/c/evprequests/batch"
			).build());

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private void _setRequestStatus(
		JSONObject evpRequestsStatusJSONObject,
		JSONObject evpRequestTypeJSONObject) {

		if (Objects.equals(
				evpRequestTypeJSONObject.getString("key"), "grant")) {

			evpRequestsStatusJSONObject.put(
				"key", "awaitingApprovalOnEVP"
			).put(
				"name", "Awaiting Approval On EVP"
			);

			return;
		}

		evpRequestsStatusJSONObject.put(
			"key", "awaitingApprovalOnManager"
		).put(
			"name", "Awaiting Approval on Manager"
		);
	}

}