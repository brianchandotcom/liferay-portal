/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adyen;

import com.adyen.model.notification.NotificationRequest;
import com.adyen.model.notification.NotificationRequestItem;
import com.adyen.util.HMACValidator;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;

import java.nio.charset.StandardCharsets;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * @author Crescenzo Rega
 */
@RequestMapping("/notifications")
@RestController
public class NotificationsRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@RequestHeader Map<String, String> headers, @RequestBody String json) {

		try {
			NotificationRequest notificationRequest =
				NotificationRequest.fromJson(json);

			List<NotificationRequestItem> notificationRequestItems =
				notificationRequest.getNotificationItems();

			if (!notificationRequestItems.isEmpty()) {
				NotificationRequestItem notificationRequestItem =
					notificationRequestItems.get(0);

				String externalReferenceCode = _getExternalReferenceCode(
					notificationRequestItem);

				JSONObject adyenWebhookJSONObject = _get(
					_liferayOAuth2AccessTokenManager.getAuthorization(
						"liferay-adyen-payment-integration-oauth-application-" +
							"headless-server"),
					"/o/c/n1a0adyenwebhooks/by-external-reference-code/" +
						externalReferenceCode);

				if (!_hasAuthentication(
						headers.get("authorization"), adyenWebhookJSONObject)) {

					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}

				HMACValidator hmacValidator = new HMACValidator();

				if (!hmacValidator.validateHMAC(
						notificationRequestItem,
						adyenWebhookJSONObject.getString("hmacSignature"))) {

					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				String paymentId = null;
				String errorMessages = null;
				String paymentStatus = "4";

				if (StringUtils.equalsAny(
						notificationRequestItem.getEventCode(), "AUTHORISATION",
						"CAPTURE")) {

					if (notificationRequestItem.isSuccess()) {
						paymentId =
							notificationRequestItem.getMerchantReference();
						paymentStatus = "0";
					}
					else {
						errorMessages = notificationRequestItem.getReason();
					}
				}
				else if (StringUtils.equals(
							notificationRequestItem.getEventCode(),
							"CANCELLATION")) {

					if (notificationRequestItem.isSuccess()) {
						paymentStatus = "8";
					}
					else {
						errorMessages = notificationRequestItem.getReason();
					}
				}
				else if (StringUtils.equals(
							notificationRequestItem.getEventCode(), "REFUND")) {

					if (notificationRequestItem.isSuccess()) {
						paymentId = _getPaymentId(notificationRequestItem);
						paymentStatus = "17";
					}
					else {
						errorMessages = notificationRequestItem.getReason();
					}
				}

				if (StringUtils.isNotBlank(paymentId)) {
					_updatePayment(
						errorMessages, json, paymentId, paymentStatus);

					delete(
						_liferayOAuth2AccessTokenManager.getAuthorization(
							"liferay-adyen-payment-integration-oauth-" +
								"application-headless-server"),
						"/o/c/n1a0adyenwebhooks/by-external-reference-code/" +
							externalReferenceCode);
				}
			}
		}
		catch (Exception exception) {
			_log.error(ExceptionUtils.getStackTrace(exception));

			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	private JSONObject _get(String authorization, String path) {
		Mono<String> response = getWebClient(
		).get(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).retrieve(
		).bodyToMono(
			String.class
		);

		response.subscribe();

		return new JSONObject(Objects.requireNonNull(response.block()));
	}

	private String _getExternalReferenceCode(
		NotificationRequestItem notificationRequestItem) {

		if (StringUtils.equalsAny(
				notificationRequestItem.getEventCode(), "AUTHORISATION",
				"CAPTURE")) {

			Map<String, String> additionalData =
				notificationRequestItem.getAdditionalData();

			return additionalData.get("paymentLinkId");
		}

		if (StringUtils.equals(
				notificationRequestItem.getEventCode(), "REFUND")) {

			return notificationRequestItem.getOriginalReference();
		}

		return null;
	}

	private String _getPaymentId(
		NotificationRequestItem notificationRequestItem) {

		JSONObject paymentsJSONObject = _get(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-adyen-payment-integration-oauth-application-" +
					"headless-server"),
			new StringBuilder(
			).append(
				"/o/headless-commerce-admin-payment/v1.0/payments/"
			).append(
				"?filter=classPK eq "
			).append(
				notificationRequestItem.getMerchantReference()
			).toString());

		JSONArray itemsJSONArray = paymentsJSONObject.getJSONArray("items");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			String payload = itemJSONObject.getString("payload");

			if (StringUtils.contains(
					payload, notificationRequestItem.getOriginalReference()) &&
				StringUtils.contains(
					payload, notificationRequestItem.getPspReference())) {

				return String.valueOf(itemJSONObject.getInt("id"));
			}
		}

		return null;
	}

	private boolean _hasAuthentication(
		String authorization, JSONObject adyenWebhookJSONObject) {

		if (StringUtils.isBlank(authorization) &&
			!StringUtils.contains(authorization, "Basic")) {

			return false;
		}

		String[] authorizationParts = new String(
			Base64.getDecoder(
			).decode(
				authorization.substring(
					"Basic".length()
				).trim()
			),
			StandardCharsets.UTF_8
		).split(
			":", 2
		);

		String webhookPassword = authorizationParts[1];
		String webhookUserName = authorizationParts[0];

		if (webhookPassword.equals(
				adyenWebhookJSONObject.getString("webhookPassword")) &&
			webhookUserName.equals(
				adyenWebhookJSONObject.getString("webhookUsername"))) {

			return true;
		}

		return false;
	}

	private void _patch(String authorization, String body, String path) {
		getWebClient(
		).patch(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).bodyValue(
			body
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).retrieve(
		).bodyToMono(
			String.class
		).subscribe();
	}

	private void _updatePayment(
		String errorMessages, String json, String paymentId,
		String paymentStatus) {

		_patch(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-adyen-payment-integration-oauth-application-" +
					"headless-server"),
			new JSONObject(
			).put(
				"errorMessages", errorMessages
			).put(
				"payload",
				json.replaceAll(
					"\\n", ""
				).replaceAll(
					"\\s", ""
				)
			).put(
				"paymentStatus", paymentStatus
			).toString(),
			"/o/headless-commerce-admin-payment/v1.0/payments/" + paymentId);
	}

	private static final Log _log = LogFactory.getLog(
		NotificationsRestController.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}