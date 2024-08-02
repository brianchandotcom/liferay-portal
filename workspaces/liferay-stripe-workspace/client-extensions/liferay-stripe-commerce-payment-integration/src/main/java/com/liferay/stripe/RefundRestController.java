/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stripe;

import com.liferay.client.extension.util.spring.boot.BaseRestController;

import com.stripe.Stripe;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.param.RefundCreateParams;

import java.math.BigDecimal;

import java.util.Objects;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
@RequestMapping("/refund")
@RestController
public class RefundRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		String errorMessages = null;
		String paymentStatus = "4";

		try {
			JSONObject jsonObject = new JSONObject(json);

			JSONObject typeSettingsJSONObject = jsonObject.getJSONObject(
				"typeSettings");

			Stripe.apiKey = typeSettingsJSONObject.getString("apiKey");

			JSONObject commercePaymentEntryJSONObject =
				jsonObject.getJSONObject("commercePaymentEntry");

			Session session = Session.retrieve(
				commercePaymentEntryJSONObject.getString("transactionCode"));

			Refund refund = Refund.create(
				RefundCreateParams.builder(
				).setAmount(
					BigDecimal.valueOf(
						commercePaymentEntryJSONObject.getDouble("amount")
					).multiply(
						BigDecimal.valueOf(100)
					).longValue()
				).setPaymentIntent(
					session.getPaymentIntent()
				).setReason(
					_getReason(
						commercePaymentEntryJSONObject.getString("reasonKey"))
				).build());

			if (Objects.equals(refund.getStatus(), "succeeded")) {
				paymentStatus = "17";
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

	private RefundCreateParams.Reason _getReason(String reasonKey) {
		if (Objects.equals(
				reasonKey, RefundCreateParams.Reason.DUPLICATE.getValue())) {

			return RefundCreateParams.Reason.DUPLICATE;
		}
		else if (Objects.equals(
					reasonKey,
					RefundCreateParams.Reason.FRAUDULENT.getValue())) {

			return RefundCreateParams.Reason.FRAUDULENT;
		}

		return RefundCreateParams.Reason.REQUESTED_BY_CUSTOMER;
	}

	private static final Log _log = LogFactory.getLog(
		RefundRestController.class);

}