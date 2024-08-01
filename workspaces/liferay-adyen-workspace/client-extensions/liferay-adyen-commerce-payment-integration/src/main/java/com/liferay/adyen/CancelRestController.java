/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adyen;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.model.checkout.PaymentCancelRequest;
import com.adyen.model.checkout.PaymentCancelResponse;
import com.adyen.model.notification.NotificationRequest;
import com.adyen.model.notification.NotificationRequestItem;
import com.adyen.service.checkout.ModificationsApi;

import com.liferay.client.extension.util.spring.boot.BaseRestController;

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
@RequestMapping("/cancel")
@RestController
public class CancelRestController extends BaseRestController {

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
			JSONObject typeSettingsJSONObject = jsonObject.getJSONObject(
				"typeSettings");

			PaymentCancelResponse paymentCancelResponse = new ModificationsApi(
				new Client(
					typeSettingsJSONObject.getString("apiKey"),
					Environment.valueOf(
						typeSettingsJSONObject.getString("environment")))
			).cancelAuthorisedPaymentByPspReference(
				_getPspReference(typeSettingsJSONObject),
				new PaymentCancelRequest(
				).merchantAccount(
					typeSettingsJSONObject.getString("merchantAccount")
				).reference(
					commercePaymentEntryJSONObject.getString(
						"externalReferenceCode")
				)
			);

			if ((paymentCancelResponse != null) &&
				(PaymentCancelResponse.StatusEnum.RECEIVED.compareTo(
					paymentCancelResponse.getStatus()) == 0)) {

				paymentStatus = "8";
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

	private String _getPspReference(JSONObject typeSettingsJSONObject)
		throws Exception {

		NotificationRequest notificationRequest = NotificationRequest.fromJson(
			typeSettingsJSONObject.getString("payload"));

		List<NotificationRequestItem> notificationRequestItems =
			notificationRequest.getNotificationItems();

		if (notificationRequestItems.isEmpty()) {
			return null;
		}

		NotificationRequestItem notificationRequestItem =
			notificationRequestItems.get(0);

		return notificationRequestItem.getPspReference();
	}

	private static final Log _log = LogFactory.getLog(
		CancelRestController.class);

}