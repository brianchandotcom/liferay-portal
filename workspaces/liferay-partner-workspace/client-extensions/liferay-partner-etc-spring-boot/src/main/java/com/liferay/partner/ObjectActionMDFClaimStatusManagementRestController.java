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
@RequestMapping("/object/action/mdf/claim/status/management")
@RestController
public class ObjectActionMDFClaimStatusManagementRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(@RequestBody String json) {
		JSONObject jsonObject = new JSONObject(json);

		JSONObject mdfClaimJSONObject = jsonObject.getJSONObject(
			"objectEntryDTOMDFClaim");

		JSONObject mdfClaimPropertiesJSONObject =
			mdfClaimJSONObject.getJSONObject("properties");

		String mdfClaimStatus = mdfClaimPropertiesJSONObject.getJSONObject(
			"mdfClaimStatus"
		).getString(
			"key"
		);

		if (mdfClaimStatus.equals("claimPaid")) {
			String mdfRequestExternalReferenceCode =
				mdfClaimPropertiesJSONObject.getString("mdfReqToMDFClmsERC");

			if (mdfClaimPropertiesJSONObject.getDouble("claimPaid") >=
					mdfClaimPropertiesJSONObject.getDouble(
						"totalMDFRequestedAmount")) {

				_completeMDFRequestStatus(mdfRequestExternalReferenceCode);
			}
			else {
				JSONObject responseJSONObject = get(
					uriBuilder -> uriBuilder.path(
						"/o/c/mdfrequests/by-external-reference-code/" +
							mdfRequestExternalReferenceCode
					).build());

				if (responseJSONObject.getDouble("totalPaidAmount") >=
						responseJSONObject.getDouble("totalMDFRequestAmount")) {

					_completeMDFRequestStatus(mdfRequestExternalReferenceCode);
				}
			}
		}

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private void _completeMDFRequestStatus(
		String mdfRequestExternalReferenceCode) {

		JSONObject jsonObject = new JSONObject();

		JSONObject mdfRequestStatusJSONObject = new JSONObject();

		mdfRequestStatusJSONObject.put(
			"key", "completed"
		).put(
			"name", "Completed"
		);

		jsonObject.put("mdfRequestStatus", mdfRequestStatusJSONObject);

		patch(
			jsonObject.toString(),
			"/o/c/mdfrequests/by-external-reference-code/" +
				mdfRequestExternalReferenceCode);
	}

}