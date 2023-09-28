/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.evp;

import com.liferay.petra.string.StringBundler;

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

/**
 * @author Elvison Victor
 */
@RequestMapping("/object/action/organization/status")
@RestController
public class ObjectActionOrganizationStatusRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject evpOrganizationJSONObject1 = new JSONObject(json);

		JSONObject originalObjectEntryDTOEVPOrganizationJSONObject =
			evpOrganizationJSONObject1.getJSONObject(
				"originalObjectEntryDTOEVPOrganization");

		long evpOrganizationId =
			originalObjectEntryDTOEVPOrganizationJSONObject.getLong("id");

		get(
			response1 -> {
				JSONObject evpOrganizationJSONObject2 = new JSONObject(
					response1);

				JSONObject evpOrganizationStatusJSONObject =
					evpOrganizationJSONObject2.getJSONObject(
						"organizationStatus");

				get(
					response2 -> {
						JSONObject evpRequestsJSONObject = new JSONObject(
							response2);

						if (evpRequestsJSONObject.getInt("totalCount") < 0) {
							return;
						}

						JSONArray itemsJSONArray =
							evpRequestsJSONObject.getJSONArray("items");

						_checkStatusOrganization(
							evpOrganizationStatusJSONObject, itemsJSONArray);

						put(
							itemsJSONArray.toString(), jwt,
							"/o/c/evprequests/batch");
					},
					jwt,
					StringBundler.concat(
						"/o/c/evprequests?filter=",
						"r_organization_c_evpOrganizationId eq '",
						evpOrganizationId, "'"));
			},
			jwt, "/o/c/evporganizations/" + evpOrganizationId);

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private void _checkStatusOrganization(
		JSONObject evpOrganizationStatusJSONObject, JSONArray itemsJSONArray) {

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			JSONObject evpRequestsStatusJSONObject =
				itemJSONObject.getJSONObject("requestStatus");

			if (evpOrganizationStatusJSONObject.getString(
					"key"
				).equals(
					"verified"
				)) {

				JSONObject evpRequestTypeJSONObject =
					itemJSONObject.getJSONObject("requestType");

				_setRequestStatus(
					evpRequestsStatusJSONObject, evpRequestTypeJSONObject);
			}
			else if (evpOrganizationStatusJSONObject.getString(
						"key"
					).equals(
						"rejected"
					)) {

				evpRequestsStatusJSONObject.put(
					"key", "rejected"
				).put(
					"name", "Rejected"
				);
			}
		}
	}

	private void _setRequestStatus(
		JSONObject evpRequestsStatusJSONObject,
		JSONObject evpRequestTypeJSONObject) {

		if (evpRequestTypeJSONObject.getString(
				"key"
			).equals(
				"grant"
			)) {

			evpRequestsStatusJSONObject.put(
				"key", "awaitingApprovalOnEVP"
			).put(
				"name", "Awaiting Approval On EVP"
			);
		}
		else {
			evpRequestsStatusJSONObject.put(
				"key", "awaitingApprovalOnManager"
			).put(
				"name", "Awaiting Approval on Manager"
			);
		}
	}

}