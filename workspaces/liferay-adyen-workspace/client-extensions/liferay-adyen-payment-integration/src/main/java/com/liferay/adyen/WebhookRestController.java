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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Crescenzo Rega
 */
@RequestMapping("/notifications")
@RestController
public class WebhookRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@RequestHeader Map<String, String> headers, @RequestBody String json) {

		if (_log.isDebugEnabled()) {
			try {
				JSONObject jsonObject = new JSONObject(headers);

				_log.debug("HEADERS: " + jsonObject.toString(4));

				jsonObject = new JSONObject(json);

				_log.debug("debug: " + jsonObject.toString(4));
			}
			catch (Exception exception) {
				_log.error(ExceptionUtils.getStackTrace(exception));
			}
		}

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

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Get adyen webhook with externalReferenceCode: " +
							externalReferenceCode);
				}

				JSONObject adyenWebhookJSONObject = get(
					_liferayOAuth2AccessTokenManager.getAuthorization(
						"liferay-adyen-payment-integration-oauth-application-" +
							"headless-server"),
					_log,
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

					delete(
						_liferayOAuth2AccessTokenManager.getAuthorization(
							"liferay-adyen-payment-integration-oauth-" +
								"application-headless-server"),
						_log,
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

		if (webhookUserName.equals(
				adyenWebhookJSONObject.getString("webhookUsername")) &&
			webhookPassword.equals(
				adyenWebhookJSONObject.getString("webhookPassword"))) {

			return true;
		}

		return false;
	}

	private String _getCommercePaymentEntryId(
		NotificationRequestItem notificationRequestItem) {

		if (_log.isDebugEnabled()) {
			_log.debug("Get payments with id: " + notificationRequestItem);
		}

		JSONObject paymentsJSONObject = get(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-adyen-payment-integration-oauth-application-" +
					"headless-server"),
			_log,
			new StringBuilder(
			).append(
				"/o/headless-commerce-admin-payment/v1.0/payments"
			).append(
				"/?filter=classPK eq "
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

	private void _updatePayment(
		String json, String commercePaymentEntryId, String errorMessages,
		String paymentStatus) {

		if (_log.isDebugEnabled()) {
			_log.debug("Update payments with id " + commercePaymentEntryId);
		}

		patch(
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
			_log,
			"/o/headless-commerce-admin-payment/v1.0/payments/" +
				commercePaymentEntryId);
	}

	private static final Log _log = LogFactory.getLog(
		WebhookRestController.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}