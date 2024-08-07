/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.paypal;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.string.StringBundler;

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
			JSONObject payPalJSONObject = new JSONObject(json);

			if (!payPalJSONObject.isEmpty()) {
				String errorMessages = null;
				String eventType = payPalJSONObject.getString("event_type");
				String paymentStatus;

				if (StringUtils.equals(
						eventType, "PAYMENT.CAPTURE.COMPLETED")) {

					paymentStatus = "0";
				}
				else if (StringUtils.equals(
							eventType, "PAYMENT.CAPTURE.DENIED")) {

					paymentStatus = "4";
					errorMessages = payPalJSONObject.getString("summary");
				}
				else {
					return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
				}

				JSONObject payPalResourceJSONObject =
					payPalJSONObject.getJSONObject("resource");

				String transactionCode = payPalResourceJSONObject.getString(
					"id");

				if (!_hasAuthentication(headers, json, transactionCode)) {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}

				_updatePayment(
					errorMessages, json, paymentStatus, transactionCode);

				delete(
					_liferayOAuth2AccessTokenManager.getAuthorization(
						"liferay-paypal-oauth-application-headless-server"),
					"/o/c/paypalwebhooks/by-external-reference-code/" +
						transactionCode);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(ExceptionUtils.getMessage(exception));
			}

			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	private boolean _hasAuthentication(
		Map<String, String> headers, String json, String transactionCode) {

		JSONObject payPalWebhookJSONObject = get(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-paypal-oauth-application-headless-server"),
			"/o/c/paypalwebhooks/by-external-reference-code/" +
				transactionCode);

		if (payPalWebhookJSONObject == null) {
			return false;
		}

		StringBundler sb = new StringBundler(15);

		String transmissionId =
			"\"" + headers.get("paypal-transmission-id") + "\"";
		String transmissionTime =
			"\"" + headers.get("paypal-transmission-time") + "\"";
		String certURL = "\"" + headers.get("paypal-cert-url") + "\"";
		String authAlgo = "\"" + headers.get("paypal-auth-algo") + "\"";
		String transmissionSig =
			"\"" + headers.get("paypal-transmission-sig") + "\"";
		String webhookId =
			"\"" + payPalWebhookJSONObject.getString("webhookId") + "\"";

		sb.append("{\"transmission_id\": ");
		sb.append(transmissionId);
		sb.append(",\"transmission_time\": ");
		sb.append(transmissionTime);
		sb.append(",\"cert_url\": ");
		sb.append(certURL);
		sb.append(",\"auth_algo\": ");
		sb.append(authAlgo);
		sb.append(",\"transmission_sig\": ");
		sb.append(transmissionSig);
		sb.append(",\"webhook_id\": ");
		sb.append(webhookId);
		sb.append(",\"webhook_event\": ");
		sb.append(json);
		sb.append("}");

		String authorization = getAuthorization(
			payPalWebhookJSONObject.getString("clientId"),
			payPalWebhookJSONObject.getString("clientSecret"),
			payPalWebhookJSONObject.getString("mode"));

		String verifySignatureResponse = WebClient.create(
			getEnvironmentURL(payPalWebhookJSONObject.getString("mode"))
		).post(
		).uri(
			"v1/notifications/verify-webhook-signature"
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + authorization
		).bodyValue(
			sb.toString()
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		JSONObject verifySignatureResponseJSONObject = new JSONObject(
			verifySignatureResponse);

		if (Objects.equals(
				verifySignatureResponseJSONObject.getString(
					"verification_status"),
				"SUCCESS")) {

			return true;
		}

		return false;
	}

	private void _updatePayment(
		String errorMessages, String json, String paymentStatus,
		String transactionCode) {

		JSONObject payPalWebhookJSONObject = get(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-paypal-oauth-application-headless-server"),
			"/o/c/paypalwebhooks/by-external-reference-code/" +
				transactionCode);

		patch(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-paypal-oauth-application-headless-server"),
			new JSONObject(
			).put(
				"errorMessages", errorMessages
			).put(
				"payload", json
			).put(
				"paymentStatus", paymentStatus
			).toString(),
			"/o/headless-commerce-admin-payment/v1.0/payments/" +
				payPalWebhookJSONObject.getLong("paymentEntryId"));
	}

	private static final Log _log = LogFactory.getLog(
		NotificationsRestController.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}