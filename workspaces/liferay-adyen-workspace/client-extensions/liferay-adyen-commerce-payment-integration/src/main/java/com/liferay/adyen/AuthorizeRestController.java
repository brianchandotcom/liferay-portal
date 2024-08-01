/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adyen;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.model.checkout.SessionResultResponse;
import com.adyen.service.checkout.PaymentsApi;

import com.liferay.client.extension.util.spring.boot.BaseRestController;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
 * @author Crescenzo Rega
 */
@RequestMapping("/authorize")
@RestController
public class AuthorizeRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		String errorMessages = null;
		String paymentStatus = "4";

		try {
			JSONObject jsonObject = new JSONObject(json);

			JSONObject commercePaymentEntryJSONObject =
				jsonObject.getJSONObject("commercePaymentEntry");

			JSONObject httpServletRequestParameterMapJSONObject =
				jsonObject.getJSONObject("httpServletRequestParameterMap");

			JSONArray sessionResultJSONArray =
				httpServletRequestParameterMapJSONObject.getJSONArray(
					"sessionResult");

			JSONObject typeSettingsJSONObject = jsonObject.getJSONObject(
				"typeSettings");

			SessionResultResponse sessionResultResponse = new PaymentsApi(
				new Client(
					typeSettingsJSONObject.getString("apiKey"),
					Environment.valueOf(
						typeSettingsJSONObject.getString("environment")))
			).getResultOfPaymentSession(
				commercePaymentEntryJSONObject.getString("transactionCode"),
				sessionResultJSONArray.getString(0), null
			);

			if (SessionResultResponse.StatusEnum.CANCELED.compareTo(
					sessionResultResponse.getStatus()) == 0) {

				JSONObject payloadJSONObject = new JSONObject(
					commercePaymentEntryJSONObject.getString("payload"));

				delete(
					"Bearer " + jwt.getTokenValue(), null,
					"/o/c/n1a0adyenwebhooks/by-external-reference-code/" +
						payloadJSONObject.getString("id"));

				paymentStatus = "8";
			}
			else if (SessionResultResponse.StatusEnum.COMPLETED.compareTo(
						sessionResultResponse.getStatus()) == 0) {

				paymentStatus = "2";
			}
		}
		catch (Exception exception) {
			errorMessages = ExceptionUtils.getStackTrace(exception);

			_log.error(errorMessages);
		}

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"errorMessages", errorMessages
			).put(
				"paymentStatus", paymentStatus
			).toString(),
			HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		AuthorizeRestController.class);

}