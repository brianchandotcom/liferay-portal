/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Elias Santos
 */
@RequestMapping("/statusManagement")
@RestController
public class StatusManagementController extends BaseRestController {

	@GetMapping
	public void closeCompleteRequest() {
		JSONObject mdfRequestsJSONObject = get(
			uriBuilder -> uriBuilder.path(
				"/o/c/mdfrequests"
			).queryParam(
				"nestedFields", "mdfReqToMDFClms"
			).queryParam(
				"page", "1"
			).queryParam(
				"pageSize", "-1"
			).build());

		JSONArray mdfRequestsJSONArray = mdfRequestsJSONObject.getJSONArray(
			"items");

		for (int i = 0; i < mdfRequestsJSONArray.length(); i++) {
			try {
				JSONObject mdfRequestJSONObject =
					mdfRequestsJSONArray.getJSONObject(i);

				String mdfRequestStatus = mdfRequestJSONObject.getJSONObject(
					"mdfRequestStatus"
				).getString(
					"key"
				);

				Double mdfRequestAmount = mdfRequestJSONObject.getDouble(
					"totalMDFRequestAmount");

				if (mdfRequestStatus.equals("approved")) {
					JSONArray mdfClaims = mdfRequestJSONObject.getJSONArray(
						"mdfReqToMDFClms");

					Double claimPaidTotal = 0.0;

					for (int j = 0; j < mdfClaims.length(); j++) {
						JSONObject mdfClaimJSONObject = mdfClaims.getJSONObject(
							j);

						String mdfClaimStatus =
							mdfClaimJSONObject.getJSONObject(
								"mdfClaimStatus"
							).getString(
								"key"
							);
						Double mdfClaimPaid = mdfClaimJSONObject.getDouble(
							"claimPaid");

						claimPaidTotal += mdfClaimPaid;

						if (mdfClaimStatus.equals("claimPaid") &&
							(claimPaidTotal >= mdfRequestAmount)) {

							String mdfRequestExternalReferenceCode =
								mdfRequestJSONObject.getString(
									"externalReferenceCode");

							JSONObject newMdfRequestStatus = new JSONObject();

							newMdfRequestStatus.put("key", "completed");
							newMdfRequestStatus.put("name", "Completed");

							JSONObject mdfRequestStatusWrapper =
								new JSONObject();

							mdfRequestStatusWrapper.put(
								"mdfRequestStatus", newMdfRequestStatus);

							patch(
								mdfRequestStatusWrapper.toString(),
								"/o/c/mdfrequests/by-external-reference-code/" +
									mdfRequestExternalReferenceCode);
						}
					}
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}