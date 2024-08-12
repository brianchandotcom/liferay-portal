/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.paypal;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Brian I. Kim
 */
@RequestMapping("/notifications")
@RestController
public class NotificationsRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@RequestHeader Map<String, String> headers, @RequestBody String json) {

		try {
			JSONObject jsonObject = new JSONObject(json);

			if (jsonObject.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}

			String errorMessages = null;
			String eventType = jsonObject.getString("event_type");
			String paymentStatus = null;

			if (StringUtils.equals(eventType, "PAYMENT.CAPTURE.COMPLETED")) {
				paymentStatus = "0";
			}
			else if (StringUtils.equals(eventType, "PAYMENT.CAPTURE.DENIED")) {
				paymentStatus = "4";
				errorMessages = jsonObject.getString("summary");
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}

			JSONObject resourceJSONObject = jsonObject.getJSONObject(
				"resource");

			String transactionCode = resourceJSONObject.getString("id");

			JSONObject b9k3PayPalWebhookJSONObject = new JSONObject(
				get(
					_liferayOAuth2AccessTokenManager.getAuthorization(
						"liferay-paypal-commerce-payment-integration-oauth-" +
							"application-headless-server"),
					"/o/c/b9k3paypalwebhooks/by-external-reference-code/" +
						transactionCode));

			if (!_hasAuthentication(
					b9k3PayPalWebhookJSONObject, headers, json)) {

				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

			_updatePayment(
				b9k3PayPalWebhookJSONObject, errorMessages, json, paymentStatus,
				transactionCode);
		}
		catch (Exception exception) {
			_log.error(ExceptionUtils.getMessage(exception));

			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	private boolean _hasAuthentication(
		JSONObject b9k3PayPalWebhookJSONObject, Map<String, String> headers,
		String json) {

		if (b9k3PayPalWebhookJSONObject.isEmpty()) {
			return false;
		}

		JSONObject verifyWebhookSignatureResponseJSONObject = new JSONObject(
			WebClient.create(
				getPayPalURL(b9k3PayPalWebhookJSONObject.getString("mode"))
			).post(
			).uri(
				"v1/notifications/verify-webhook-signature"
			).accept(
				MediaType.APPLICATION_JSON
			).contentType(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION,
				"Bearer " + getAuthorization(b9k3PayPalWebhookJSONObject)
			).bodyValue(

				// Ugly string format is what PayPal expects

				StringBundler.concat(
					"{\"transmission_id\": \"",
					headers.get("paypal-transmission-id"),
					"\", \"transmission_time\": \"",
					headers.get("paypal-transmission-time"),
					"\", \"cert_url\": \"",
					headers.get("paypal-cert-url"),
					"\", \"auth_algo\": \"",
					headers.get("paypal-auth-algo"),
					"\", \"transmission_sig\": \"",
					headers.get("paypal-transmission-sig"),
					"\", \"webhook_id\": \"",
					b9k3PayPalWebhookJSONObject.getString("webhookId"),
					"\", \"webhook_event\": ", json, "}")
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		if (Objects.equals(
				verifyWebhookSignatureResponseJSONObject.getString(
					"verification_status"),
				"SUCCESS")) {

			return true;
		}

		return false;
	}

	private void _updatePayment(
		JSONObject b9k3PayPalWebhookJSONObject, String errorMessages,
		String json, String paymentStatus, String transactionCode) {

		if (b9k3PayPalWebhookJSONObject.isEmpty()) {
			return;
		}

		patch(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-paypal-commerce-payment-integration-oauth-" +
					"application-headless-server"),
			new JSONObject(
			).put(
				"errorMessages", errorMessages
			).put(
				"payload", json
			).put(
				"paymentStatus", paymentStatus
			).toString(),
			"/o/headless-commerce-admin-payment/v1.0/payments/" +
				b9k3PayPalWebhookJSONObject.getLong("paymentEntryId"));

		delete(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-paypal-commerce-payment-integration-oauth-" +
					"application-headless-server"),
			StringPool.BLANK,
			"/o/c/b9k3paypalwebhooks/by-external-reference-code/" +
				transactionCode);
	}

	private static final Log _log = LogFactory.getLog(
		NotificationsRestController.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}