/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adyen;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.model.checkout.Amount;
import com.adyen.model.checkout.PaymentRefundRequest;
import com.adyen.model.checkout.PaymentRefundResponse;
import com.adyen.model.notification.NotificationRequest;
import com.adyen.model.notification.NotificationRequestItem;
import com.adyen.service.checkout.ModificationsApi;

import com.liferay.client.extension.util.spring.boot.BaseRestController;

import java.math.BigDecimal;

import java.util.List;

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
		String payload = null;
		String paymentStatus = "4";

		try {
			JSONObject jsonObject = new JSONObject(json);

			JSONObject commercePaymentEntryJSONObject =
				jsonObject.getJSONObject("commercePaymentEntry");

			NotificationRequest notificationRequest =
				NotificationRequest.fromJson(
					commercePaymentEntryJSONObject.getString("payload"));

			List<NotificationRequestItem> notificationRequestItems =
				notificationRequest.getNotificationItems();

			if (notificationRequestItems.isEmpty()) {
				return new ResponseEntity<>(
					new JSONObject(
					).put(
						"errorMessages", errorMessages
					).put(
						"payload", payload
					).put(
						"paymentStatus", paymentStatus
					).toString(),
					HttpStatus.OK);
			}

			NotificationRequestItem notificationRequestItem =
				notificationRequestItems.get(0);

			JSONObject typeSettingsJSONObject = jsonObject.getJSONObject(
				"typeSettings");

			PaymentRefundResponse paymentRefundResponse = new ModificationsApi(
				new Client(
					typeSettingsJSONObject.getString("apiKey"),
					Environment.valueOf(
						typeSettingsJSONObject.getString("environment")))
			).refundCapturedPayment(
				notificationRequestItem.getPspReference(),
				new PaymentRefundRequest(
				).amount(
					new Amount(
					).currency(
						commercePaymentEntryJSONObject.getString("currencyCode")
					).value(
						BigDecimal.valueOf(
							commercePaymentEntryJSONObject.getDouble("amount")
						).multiply(
							BigDecimal.valueOf(100)
						).longValue()
					)
				).merchantAccount(
					typeSettingsJSONObject.getString("merchantAccount")
				).merchantRefundReason(
					PaymentRefundRequest.MerchantRefundReasonEnum.
						CUSTOMER_REQUEST
				).reference(
					commercePaymentEntryJSONObject.getString("classPK")
				)
			);

			if ((paymentRefundResponse != null) &&
				(PaymentRefundResponse.StatusEnum.RECEIVED.compareTo(
					paymentRefundResponse.getStatus()) == 0)) {

				post(
					"Bearer " + jwt.getTokenValue(),
					new JSONObject(
					).put(
						"externalReferenceCode",
						notificationRequestItem.getPspReference()
					).put(
						"hmacSignature",
						typeSettingsJSONObject.getString("hmacSignature")
					).put(
						"webhookPassword",
						typeSettingsJSONObject.getString("webhookPassword")
					).put(
						"webhookUsername",
						typeSettingsJSONObject.getString("webhookUsername")
					).toString(),
					"/o/c/n1a0adyenwebhooks");

				payload = paymentRefundResponse.toJson();
				paymentStatus = "18";
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
				"payload", payload
			).put(
				"paymentStatus", paymentStatus
			).toString(),
			HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		RefundRestController.class);

}