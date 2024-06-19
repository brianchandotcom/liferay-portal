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

import java.security.SignatureException;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * @author Crescenzo Rega
 */
@RequestMapping("/notifications")
@RestController
public class WebhookRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@RequestHeader Map<String, String> headers, @RequestBody String json) {

		try {
			NotificationRequest notificationRequest =
				NotificationRequest.fromJson(json);

			List<NotificationRequestItem> notificationItems =
				notificationRequest.getNotificationItems();

			if (!CollectionUtils.isEmpty(notificationItems)) {
				NotificationRequestItem notificationRequestItem =
					notificationItems.get(0);

				String externalReferenceCode = _getExternalReferenceCode(
					notificationRequestItem);

				JSONObject adyenWebhookJSONObject = _get(
					_liferayOAuth2AccessTokenManager.getAuthorization(
						"liferay-adyen-payment-integration-oauth-application-" +
							"headless-server"),
					"/o/c/n1a0adyenwebhooks/by-external-reference-code/" +
						externalReferenceCode);

				if (!_checkAuthentication(
						headers.get("authorization"), adyenWebhookJSONObject)) {

					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}

				if (!_isValidateHMAC(
						notificationRequestItem, adyenWebhookJSONObject)) {

					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				String commercePaymentEntryId = null;
				String errorMessages = null;
				String paymentStatus = "4";

				if (StringUtils.equalsAny(
						notificationRequestItem.getEventCode(), "AUTHORISATION",
						"CAPTURE")) {

					if (notificationRequestItem.isSuccess()) {
						paymentStatus = "0";
						commercePaymentEntryId =
							notificationRequestItem.getMerchantReference();
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
						paymentStatus = "17";

						commercePaymentEntryId = _getCommercePaymentEntryId(
							notificationRequestItem);
					}
					else {
						errorMessages = notificationRequestItem.getReason();
					}
				}

				if (StringUtils.isNotBlank(commercePaymentEntryId)) {
					_updatePayment(
						json, commercePaymentEntryId, errorMessages,
						paymentStatus);

					_delete(
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

	private boolean _checkAuthentication(
		String authorization, JSONObject adyenWebhookJSONObject) {

		if (StringUtils.isBlank(authorization) &&
			!StringUtils.contains(authorization, "Basic")) {

			return false;
		}

		final String[] credentials = new String(
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

		String webhookUserName = credentials[0];
		String webhookPassword = credentials[1];

		if (webhookPassword.equals(
				adyenWebhookJSONObject.getString("webhookPassword")) &&
			webhookUserName.equals(
				adyenWebhookJSONObject.getString("webhookUsername"))) {

			return true;
		}

		return false;
	}

	private void _delete(String authorization, String path) {
		_getWebClient(
		).delete(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).retrieve(
		).bodyToMono(
			String.class
		).subscribe();
	}

	private JSONObject _get(String authorization, String path) {
		Mono<String> response = _getWebClient(
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

	private String _getCommercePaymentEntryId(
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

		JSONArray itemsjsonArray = paymentsJSONObject.getJSONArray("items");

		String paymentPspReference =
			notificationRequestItem.getOriginalReference();
		String pspReference = notificationRequestItem.getPspReference();

		for (int i = 0; i < itemsjsonArray.length(); i++) {
			JSONObject itemjsonObject = itemsjsonArray.getJSONObject(i);

			String payload = itemjsonObject.getString("payload");

			if (StringUtils.contains(payload, paymentPspReference) &&
				StringUtils.contains(payload, pspReference)) {

				return String.valueOf(itemjsonObject.getInt("id"));
			}
		}

		return null;
	}

	private String _getExternalReferenceCode(
		NotificationRequestItem notificationRequestItem) {

		String externalReferenceCode;

		if (StringUtils.equalsAny(
				notificationRequestItem.getEventCode(), "AUTHORISATION",
				"CAPTURE")) {

			Map<String, String> additionalData =
				notificationRequestItem.getAdditionalData();

			externalReferenceCode = additionalData.get("paymentLinkId");
		}
		else if (StringUtils.equals(
					notificationRequestItem.getEventCode(), "REFUND")) {

			externalReferenceCode =
				notificationRequestItem.getOriginalReference();
		}
		else {
			externalReferenceCode = null;
		}

		return externalReferenceCode;
	}

	private WebClient _getWebClient() {
		return WebClient.builder(
		).baseUrl(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).build();
	}

	private boolean _isValidateHMAC(
			NotificationRequestItem notificationRequestItem,
			JSONObject adyenWebhookJSONObject)
		throws SignatureException {

		return new HMACValidator(
		).validateHMAC(
			notificationRequestItem,
			adyenWebhookJSONObject.getString("hmacSignature")
		);
	}

	private void _patch(String authorization, String body, String path) {
		_getWebClient(
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
		String json, String commercePaymentEntryId, String errorMessages,
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
			"/o/headless-commerce-admin-payment/v1.0/payments/" +
				commercePaymentEntryId);
	}

	private static final Log _log = LogFactory.getLog(
		WebhookRestController.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}