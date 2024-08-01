/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adyen;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.model.checkout.Amount;
import com.adyen.model.checkout.CreateCheckoutSessionRequest;
import com.adyen.model.checkout.CreateCheckoutSessionResponse;
import com.adyen.service.checkout.PaymentsApi;

import com.liferay.client.extension.util.spring.boot.BaseRestController;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/set-up-payment")
@RestController
public class SetUpPaymentRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		String errorMessages = null;
		String payload = null;
		String paymentStatus = "4";
		String redirectURL = null;
		String transactionCode = null;

		try {
			JSONObject jsonObject = new JSONObject(json);

			JSONObject commercePaymentEntryJSONObject =
				jsonObject.getJSONObject("commercePaymentEntry");
			JSONObject typeSettingsJSONObject = jsonObject.getJSONObject(
				"typeSettings");

			CreateCheckoutSessionResponse createCheckoutSessionResponse =
				new PaymentsApi(
					new Client(
						typeSettingsJSONObject.getString("apiKey"),
						Environment.valueOf(
							typeSettingsJSONObject.getString("environment")))
				).sessions(
					new CreateCheckoutSessionRequest(
					).amount(
						new Amount(
						).currency(
							commercePaymentEntryJSONObject.getString(
								"currencyCode")
						).value(
							BigDecimal.valueOf(
								commercePaymentEntryJSONObject.getDouble(
									"amount")
							).multiply(
								BigDecimal.valueOf(100)
							).longValue()
						)
					).merchantAccount(
						typeSettingsJSONObject.getString("merchantAccount")
					).mode(
						CreateCheckoutSessionRequest.ModeEnum.HOSTED
					).reference(
						commercePaymentEntryJSONObject.getString(
							"externalReferenceCode")
					).returnUrl(
						commercePaymentEntryJSONObject.getString("callbackURL")
					).themeId(
						typeSettingsJSONObject.getString("themeId")
					)
				);

			redirectURL = createCheckoutSessionResponse.getUrl();
			transactionCode = createCheckoutSessionResponse.getId();

			if (StringUtils.isNotBlank(redirectURL) &&
				StringUtils.isNotBlank(transactionCode)) {

				payload = createCheckoutSessionResponse.toJson();
				paymentStatus = "18";

				post(
					"Bearer " + jwt.getTokenValue(),
					new JSONObject(
					).put(
						"externalReferenceCode", transactionCode
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
			).put(
				"redirectURL", redirectURL
			).put(
				"transactionCode", transactionCode
			).toString(),
			HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		SetUpPaymentRestController.class);

}