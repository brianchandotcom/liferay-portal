/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.paypal;

import java.math.BigDecimal;

import java.util.Objects;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Brian I. Kim
 */
@RequestMapping("/refund")
@RestController
public class RefundRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		String errorMessages = null;
		String paymentStatus = "4";
		String transactionCode = null;

		try {
			JSONObject jsonObject = new JSONObject(json);

			JSONObject typeSettingsJSONObject = jsonObject.getJSONObject(
				"typeSettings");

			JSONObject commercePaymentEntryJSONObject =
				jsonObject.getJSONObject("commercePaymentEntry");

			JSONObject amountJSONObject = new JSONObject(
			).put(
				"amount", _getAmountJSONObject(commercePaymentEntryJSONObject)
			);

			String transactionCodeRefund =
				commercePaymentEntryJSONObject.getString("transactionCode") +
					"/refund";

			String authorization = getAuthorization(
				typeSettingsJSONObject.getString("clientId"),
				typeSettingsJSONObject.getString("clientSecret"),
				typeSettingsJSONObject.getString("mode"));

			String refundOrderResponse = WebClient.create(
				getEnvironmentURL(typeSettingsJSONObject.getString("mode"))
			).post(
			).uri(
				"v2/payments/captures/" + transactionCodeRefund
			).contentType(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, "Bearer " + authorization
			).header(
				"PayPal-Partner-Attribution-Id", "Liferay_SP_PPCP_API"
			).header(
				"Prefer", "return=representation"
			).bodyValue(
				amountJSONObject.toString()
			).retrieve(
			).bodyToMono(
				String.class
			).block();

			JSONObject refundOrderResponseJSONObject = new JSONObject(
				refundOrderResponse);

			if (Objects.equals(
					refundOrderResponseJSONObject.getString("status"),
					"COMPLETED")) {

				paymentStatus = "17";

				transactionCode = refundOrderResponseJSONObject.getString("id");
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
			).put(
				"transactionCode", transactionCode
			).toString(),
			HttpStatus.OK);
	}

	private JSONObject _getAmountJSONObject(
		JSONObject commercePaymentEntryJSONObject) {

		JSONObject amountJSONObject = new JSONObject();

		amountJSONObject.put(
			"currency_code",
			commercePaymentEntryJSONObject.getString("currencyCode")
		).put(
			"value",
			BigDecimal.valueOf(
				commercePaymentEntryJSONObject.getDouble("amount")
			).longValue()
		);

		return amountJSONObject;
	}

	private static final Log _log = LogFactory.getLog(
		RefundRestController.class);

}