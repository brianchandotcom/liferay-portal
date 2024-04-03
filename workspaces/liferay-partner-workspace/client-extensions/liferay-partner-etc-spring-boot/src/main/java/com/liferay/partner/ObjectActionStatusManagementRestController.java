/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Elias Santos
 */
@RequestMapping("/statusManagement")
@RestController
public class ObjectActionStatusManagementRestController
	extends BaseRestController {

	@GetMapping
	public void closeCompleteRequest() {
		JSONObject mdfClaimsJSONObject = get(
			uriBuilder -> uriBuilder.path(
				"/o/c/mdfclaims/"
			).queryParam(
				"page", "1"
			).queryParam(
				"pageSize", "-1"
			).build());

		JSONArray mdfClaimsJSONArray = mdfClaimsJSONObject.getJSONArray(
			"items");

		Double claimPaidTotal = 0.0;

		for (int i = 0; i < mdfClaimsJSONArray.length(); i++) {
			JSONObject mdfClaimJSONObject = mdfClaimsJSONArray.getJSONObject(i);

			Double claimPaid = mdfClaimJSONObject.getDouble("claimPaid");
			Double totalMDFRequestedAmount = mdfClaimJSONObject.getDouble(
				"totalMDFRequestedAmount");
			String mdfClaimStatus = mdfClaimJSONObject.getJSONObject(
				"mdfClaimStatus"
			).getString(
				"key"
			);

			if (mdfClaimStatus.equals("claimPaid")) {
				if ((claimPaid >= totalMDFRequestedAmount) &&
					(claimPaidTotal >= totalMDFRequestedAmount)) {

					String mdfRequestExternalReferenceCode =
						mdfClaimJSONObject.getString(
							"r_mdfReqToMDFClms_c_mdfRequestERC");

					updateMdfRequestStatus(mdfRequestExternalReferenceCode);

					break;
				}

				claimPaidTotal += claimPaid;

				if (claimPaidTotal >= totalMDFRequestedAmount) {
					String mdfRequestExternalReferenceCode =
						mdfClaimJSONObject.getString(
							"r_mdfReqToMDFClms_c_mdfRequestERC");

					updateMdfRequestStatus(mdfRequestExternalReferenceCode);
				}
			}
		}
	}

	private void updateMdfRequestStatus(
		String mdfRequestExternalReferenceCode) {

		JSONObject newMdfRequestStatus = new JSONObject();

		newMdfRequestStatus.put("key", "completed");
		newMdfRequestStatus.put("name", "Completed");

		JSONObject mdfRequestStatusWrapper = new JSONObject();

		mdfRequestStatusWrapper.put("mdfRequestStatus", newMdfRequestStatus);

		patch(
			mdfRequestStatusWrapper.toString(),
			"/o/c/mdfrequests/by-external-reference-code/" +
				mdfRequestExternalReferenceCode);
	}

}