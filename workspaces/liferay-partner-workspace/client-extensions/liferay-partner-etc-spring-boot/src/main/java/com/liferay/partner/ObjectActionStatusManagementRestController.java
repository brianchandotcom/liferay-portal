/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import org.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Elias Santos
 */
@RequestMapping("/statusManagement")
@RestController
public class ObjectActionStatusManagementRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(@RequestBody String json) {
		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryDTOMDFClaimJSONObject = jsonObject.getJSONObject(
			"originalObjectEntryDTOMDFClaim");

		String mdfClaimStatus = objectEntryDTOMDFClaimJSONObject.getJSONObject(
			"properties"
		).getJSONObject(
			"mdfClaimStatus"
		).getString(
			"key"
		);

		if (mdfClaimStatus.equals("claimPaid")) {
			Double claimPaid = objectEntryDTOMDFClaimJSONObject.getJSONObject(
				"properties"
			).getDouble(
				"claimPaid"
			);

			Double totalMDFRequestedAmount =
				objectEntryDTOMDFClaimJSONObject.getJSONObject(
					"properties"
				).getDouble(
					"totalMDFRequestedAmount"
				);

			if (claimPaid >= totalMDFRequestedAmount) {
				_updateMdfRequestStatus(
					objectEntryDTOMDFClaimJSONObject.getJSONObject(
						"properties"
					).getString(
						"mdfReqToMDFClmsERC"
					));
			}
			else {
				String mdfRequestExternalReferenceCode =
					objectEntryDTOMDFClaimJSONObject.getJSONObject(
						"properties"
					).getString(
						"mdfReqToMDFClmsERC"
					);

				JSONObject responseJSONObject = get(
					uriBuilder -> uriBuilder.path(
						"/o/c/mdfrequests/by-external-reference-code/" +
							mdfRequestExternalReferenceCode
					).build());

				Double totalMDFRequestAmount = responseJSONObject.getDouble(
					"totalMDFRequestAmount");

				Double totalPaidAmount = responseJSONObject.getDouble(
					"totalPaidAmount");

				if (totalPaidAmount >= totalMDFRequestAmount) {
					_updateMdfRequestStatus(
						responseJSONObject.getString("externalReferenceCode"));
				}
			}
		}

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private void _updateMdfRequestStatus(
		String mdfRequestExternalReferenceCode) {

		JSONObject newMdfRequestStatusJSONObject = new JSONObject();

		newMdfRequestStatusJSONObject.put(
			"key", "completed"
		).put(
			"name", "Completed"
		);

		JSONObject mdfRequestStatusJSONObject = new JSONObject();

		mdfRequestStatusJSONObject.put(
			"mdfRequestStatus", newMdfRequestStatusJSONObject);

		patch(
			mdfRequestStatusJSONObject.toString(),
			"/o/c/mdfrequests/by-external-reference-code/" +
				mdfRequestExternalReferenceCode);
	}

}