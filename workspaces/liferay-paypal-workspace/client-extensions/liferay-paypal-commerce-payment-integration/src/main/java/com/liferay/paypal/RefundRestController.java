/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.paypal;

import com.liferay.petra.string.StringBundler;

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

			JSONObject commercePaymentEntryJSONObject =
				jsonObject.getJSONObject("commercePaymentEntry");

			JSONObject typeSettingsJSONObject = jsonObject.getJSONObject(
				"typeSettings");

			JSONObject refundResponseJSONObject = new JSONObject(
				WebClient.create(
					getPayPalURL(typeSettingsJSONObject.getString("mode"))
				).post(
				).uri(
					StringBundler.concat(
						"v2/payments/captures/",
						commercePaymentEntryJSONObject.getString(
							"transactionCode"),
						"/refund")
				).contentType(
					MediaType.APPLICATION_JSON
				).header(
					HttpHeaders.AUTHORIZATION,
					"Bearer " + getAuthorization(typeSettingsJSONObject)
				).header(
					"PayPal-Partner-Attribution-Id", "Liferay_SP_PPCP_API"
				).header(
					"Prefer", "return=representation"
				).bodyValue(
					new JSONObject(
					).put(
						"amount",
						new JSONObject(
						).put(
							"currency_code",
							commercePaymentEntryJSONObject.getString(
								"currencyCode")
						).put(
							"value",
							BigDecimal.valueOf(
								commercePaymentEntryJSONObject.getDouble(
									"amount")
							).longValue()
						)
					).toString()
				).retrieve(
				).bodyToMono(
					String.class
				).block());

			if (Objects.equals(
					refundResponseJSONObject.getString("status"),
					"COMPLETED")) {

				paymentStatus = "17";
				transactionCode = refundResponseJSONObject.getString("id");
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

	private static final Log _log = LogFactory.getLog(
		RefundRestController.class);

}