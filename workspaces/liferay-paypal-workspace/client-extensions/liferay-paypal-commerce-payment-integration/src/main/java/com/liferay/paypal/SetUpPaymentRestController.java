/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.paypal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.math.BigDecimal;

import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Brian I. Kim
 */
@RequestMapping("/set-up-payment")
@RestController
public class SetUpPaymentRestController extends BaseRestController {

	@GetMapping("get-environment/{clientId}")
	public ResponseEntity<String> getGoogleEnvironmentInfo(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable("clientId") String clientId) {

		String mode = "TEST";

		if (StringUtils.equals(clientId.substring(0, 3), "li_")) {
			mode = "PRODUCTION";
		}

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"mode", mode
			).toString(),
			HttpStatus.OK);
	}

	@GetMapping("get-order/{orderId}/{countryCode}")
	public ResponseEntity<String> getGoogleOrderInfo(
		@AuthenticationPrincipal Jwt jwt, @PathVariable("orderId") long orderId,
		@PathVariable("countryCode") String countryCode) {

		JSONObject orderJSONObject = _getOrderJSONObject(jwt, orderId);

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"countryCode", countryCode
			).put(
				"currencyCode", orderJSONObject.getString("currencyCode")
			).put(
				"displayItems",
				new JSONArray(
				).put(
					new JSONObject(
					).put(
						"label", "Subtotal"
					).put(
						"type", "SUBTOTAL"
					).put(
						"price",
						BigDecimal.valueOf(
							orderJSONObject.getDouble("subtotalAmount")
						).toString()
					)
				).put(
					new JSONObject(
					).put(
						"label", "Tax"
					).put(
						"type", "TAX"
					).put(
						"price",
						BigDecimal.valueOf(
							orderJSONObject.getDouble("taxAmount")
						).toString()
					)
				)
			).put(
				"totalPrice",
				BigDecimal.valueOf(
					orderJSONObject.getDouble("totalAmount")
				).toString()
			).put(
				"totalPriceLabel", "Total"
			).put(
				"totalPriceStatus", "FINAL"
			).toString(),
			HttpStatus.OK);
	}

	@GetMapping("get/{orderId}")
	public ResponseEntity<String> getPayPalOrderInfo(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable("orderId") long orderId) {

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"id", _getTransactionCode(jwt, orderId)
			).toString(),
			HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		String errorMessages = null;
		String paymentStatus = "4";
		String payload = null;
		String transactionCode = null;

		try {
			JSONObject jsonObject = new JSONObject(json);

			JSONObject payPalRequestJSONObject = new JSONObject();

			JSONObject commercePaymentEntryJSONObject =
				jsonObject.getJSONObject("commercePaymentEntry");

			JSONObject httpServletRequestParameterMapJSONObject =
				jsonObject.getJSONObject("httpServletRequestParameterMap");

			JSONArray fundingSourceJSONArray =
				httpServletRequestParameterMapJSONObject.getJSONArray(
					"fundingSource");

			JSONObject typeSettingsJSONObject = jsonObject.getJSONObject(
				"typeSettings");

			payPalRequestJSONObject.put(
				"intent", "CAPTURE"
			).put(
				"payment_source",
				_getPaymentSourceJSONObject(
					jwt, commercePaymentEntryJSONObject,
					String.valueOf(fundingSourceJSONArray.get(0)))
			).put(
				"purchase_units",
				_getPurchaseUnitJSONArray(
					jwt, commercePaymentEntryJSONObject,
					typeSettingsJSONObject.getString("merchantId"))
			);

			String createOrderResponse = WebClient.create(
				getPayPalURL(typeSettingsJSONObject.getString("mode"))
			).post(
			).uri(
				"/v2/checkout/orders"
			).accept(
				MediaType.APPLICATION_JSON
			).contentType(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION,
				"Bearer " + getAuthorization(typeSettingsJSONObject)
			).header(
				"PayPal-Partner-Attribution-Id", "Liferay_SP_PPCP_API"
			).header(
				"PayPal-Request-Id",
				commercePaymentEntryJSONObject.getString(
					"commercePaymentEntryId")
			).header(
				"Prefer", "return=representation"
			).bodyValue(
				payPalRequestJSONObject.toString()
			).retrieve(
			).bodyToMono(
				String.class
			).block();

			JSONObject createOrderResponseJSONObject = new JSONObject(
				createOrderResponse);

			transactionCode = createOrderResponseJSONObject.getString("id");

			paymentStatus = "18";
			payload = createOrderResponse;

			post(
				"Bearer " + jwt.getTokenValue(),
				new JSONObject(
				).put(
					"externalReferenceCode",
					String.valueOf(
						commercePaymentEntryJSONObject.getLong("classPK"))
				).put(
					"transactionCode", transactionCode
				).toString(),
				"/o/c/b9k3paypaltransactions");

			post(
				"Bearer " + jwt.getTokenValue(),
				new JSONObject(
				).put(
					"externalReferenceCode", transactionCode
				).put(
					"clientId", typeSettingsJSONObject.getString("clientId")
				).put(
					"clientSecret",
					typeSettingsJSONObject.getString("clientSecret")
				).put(
					"mode", typeSettingsJSONObject.getString("mode")
				).put(
					"paymentEntryId",
					commercePaymentEntryJSONObject.getLong(
						"commercePaymentEntryId")
				).put(
					"webhookId", typeSettingsJSONObject.getString("webhookId")
				).toString(),
				"/o/c/b9k3paypalwebhooks");
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
				"payload", payload
			).put(
				"transactionCode", transactionCode
			).toString(),
			HttpStatus.OK);
	}

	private JSONObject _getAmountJSONObject(
		String currencyCode, JSONObject orderJSONObject) {

		return new JSONObject(
		).put(
			"breakdown", _getBreakdownJSONObject(currencyCode, orderJSONObject)
		).put(
			"currency_code", currencyCode
		).put(
			"value",
			BigDecimal.valueOf(
				orderJSONObject.getDouble("totalAmount")
			).longValue()
		);
	}

	private JSONObject _getBreakdownJSONObject(
		String currencyCode, JSONObject orderJSONObject) {

		return new JSONObject(
		).put(
			"item_total",
			new JSONObject(
			).put(
				"currency_code", currencyCode
			).put(
				"value",
				BigDecimal.valueOf(
					orderJSONObject.getDouble("subtotalAmount")
				).longValue()
			)
		).put(
			"shipping",
			new JSONObject(
			).put(
				"currency_code", currencyCode
			).put(
				"value",
				BigDecimal.valueOf(
					orderJSONObject.getDouble("shippingAmountValue")
				).longValue()
			)
		).put(
			"tax_total",
			new JSONObject(
			).put(
				"currency_code", currencyCode
			).put(
				"value",
				BigDecimal.valueOf(
					orderJSONObject.getDouble("taxAmount")
				).longValue()
			)
		);
	}

	private JSONObject _getExperienceContextJSONObject(
		String callbackURL, String cancelURL, String fundingSource) {

		JSONObject experienceContextJSONObject = new JSONObject();

		if (!Objects.equals(fundingSource, "google_pay") &&
			!Objects.equals(fundingSource, "apple_pay")) {

			experienceContextJSONObject.put(
				"shipping_preference", "SET_PROVIDED_ADDRESS");
		}

		if (ArrayUtils.contains(_FUNDING_SOURCES, fundingSource)) {
			experienceContextJSONObject.put(
				"cancel_url", cancelURL
			).put(
				"return_url", callbackURL
			);
		}

		if (Objects.equals(fundingSource, "paypal")) {
			experienceContextJSONObject.put(
				"cancel_url", cancelURL
			).put(
				"return_url", callbackURL
			).put(
				"user_action", "PAY_NOW"
			);
		}

		return experienceContextJSONObject;
	}

	private JSONArray _getItemsJSONArray(
		String currencyCode, String languageId, JSONObject orderJSONObject) {

		JSONArray itemsJSONArray = new JSONArray();

		JSONArray orderItemsJSONArray = orderJSONObject.getJSONArray(
			"orderItems");

		for (int i = 0; i < orderItemsJSONArray.length(); i++) {
			JSONObject orderItemJSONObject = orderItemsJSONArray.getJSONObject(
				i);

			BigDecimal quantity = BigDecimal.valueOf(
				orderItemJSONObject.getLong("quantity"));

			itemsJSONArray.put(
				new JSONObject(
				).put(
					"name",
					orderItemJSONObject.getJSONObject(
						"name"
					).getString(
						languageId
					)
				).put(
					"quantity", quantity.stripTrailingZeros()
				).put(
					"sku", orderItemJSONObject.getString("sku")
				).put(
					"unit_amount",
					new JSONObject(
					).put(
						"currency_code", currencyCode
					).put(
						"value",
						BigDecimal.valueOf(
							orderItemJSONObject.getDouble("finalPrice")
						).divide(
							quantity
						).longValue()
					)
				));
		}

		return itemsJSONArray;
	}

	private JSONObject _getOrderJSONObject(Jwt jwt, long orderId) {
		return new JSONObject(
			get(
				"Bearer " + jwt.getTokenValue(),
				StringBundler.concat(
					"/o/headless-commerce-admin-order/v1.0/orders/", orderId,
					"?nestedFields=orderItems,shippingAddress")));
	}

	private JSONObject _getPaymentSourceJSONObject(
		Jwt jwt, JSONObject commercePaymentEntryJSONObject,
		String fundingSource) {

		JSONObject paymentSourceJSONObject = new JSONObject();

		paymentSourceJSONObject.put(
			fundingSource,
			_getPayPalPaymentSourceJSONObject(
				commercePaymentEntryJSONObject, fundingSource,
				_getOrderJSONObject(
					jwt, commercePaymentEntryJSONObject.getLong("classPK")
				).getJSONObject(
					"shippingAddress"
				)));

		return paymentSourceJSONObject;
	}

	private JSONObject _getPayPalPaymentSourceJSONObject(
		JSONObject commercePaymentEntryJSONObject, String fundingSource,
		JSONObject shippingAddressJSONObject) {

		JSONObject paymentSourceJSONObject = new JSONObject();

		if (ArrayUtils.contains(_FUNDING_SOURCES, fundingSource)) {
			paymentSourceJSONObject.put(
				"country_code",
				shippingAddressJSONObject.getString("countryISOCode")
			).put(
				"name", shippingAddressJSONObject.getString("name")
			);
		}

		return paymentSourceJSONObject.put(
			"experience_context",
			_getExperienceContextJSONObject(
				commercePaymentEntryJSONObject.getString("callbackURL"),
				commercePaymentEntryJSONObject.getString("cancelURL"),
				fundingSource));
	}

	private JSONArray _getPurchaseUnitJSONArray(
		Jwt jwt, JSONObject commercePaymentEntryJSONObject, String merchantId) {

		JSONObject purchaseUnitJSONObject = new JSONObject();

		if (Objects.equals(
				commercePaymentEntryJSONObject.getString("className"),
				"com.liferay.commerce.model.CommerceOrder")) {

			JSONObject orderJSONObject = _getOrderJSONObject(
				jwt, commercePaymentEntryJSONObject.getLong("classPK"));

			purchaseUnitJSONObject.put(
				"amount",
				_getAmountJSONObject(
					commercePaymentEntryJSONObject.getString("currencyCode"),
					orderJSONObject)
			).put(
				"items",
				_getItemsJSONArray(
					commercePaymentEntryJSONObject.getString("currencyCode"),
					commercePaymentEntryJSONObject.getString("languageId"),
					orderJSONObject)
			).put(
				"shipping",
				_getShippingJSONObject(
					orderJSONObject.getJSONObject("shippingAddress"))
			);
		}

		long commercePaymentEntryId = commercePaymentEntryJSONObject.getLong(
			"commercePaymentEntryId");

		return new JSONArray(
		).put(
			purchaseUnitJSONObject.put(
				"description", "Payment: " + commercePaymentEntryId
			).put(
				"payee",
				new JSONObject(
				).put(
					"merchant_id", merchantId
				)
			).put(
				"reference_id", commercePaymentEntryId
			)
		);
	}

	private JSONObject _getShippingJSONObject(
		JSONObject shippingAddressJSONObject) {

		return new JSONObject(
		).put(
			"name",
			new JSONObject(
			).put(
				"full_name", shippingAddressJSONObject.getString("name")
			)
		).put(
			"address",
			new JSONObject(
			).put(
				"address_line_1", shippingAddressJSONObject.getString("street1")
			).put(
				"address_line_2", shippingAddressJSONObject.getString("street2")
			).put(
				"admin_area_1",
				shippingAddressJSONObject.getString("regionISOCode")
			).put(
				"admin_area_2", shippingAddressJSONObject.getString("city")
			).put(
				"country_code",
				shippingAddressJSONObject.getString("countryISOCode")
			).put(
				"postal_code", shippingAddressJSONObject.getString("zip")
			)
		);
	}

	private String _getTransactionCode(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable("orderId") long orderId) {

		String transactionCode = null;

		for (int i = 0; i < 10; i++) {
			try {
				JSONObject orderPaymentJSONObject = new JSONObject(
					get(
						"Bearer " + jwt.getTokenValue(),
						"/o/c/b9k3paypaltransactions" +
							"/by-external-reference-code/" + orderId));

				transactionCode = orderPaymentJSONObject.getString(
					"transactionCode");
			}
			catch (Exception exception) {
				_log.error(ExceptionUtils.getMessage(exception));
			}
		}

		if (StringUtils.isNotBlank(transactionCode)) {
			delete(
				"Bearer " + jwt.getTokenValue(), StringPool.BLANK,
				"/o/c/b9k3paypaltransactions/by-external-reference-code/" +
					orderId);
		}

		return transactionCode;
	}

	private static final String[] _FUNDING_SOURCES = {
		"bancontact", "eps", "giropay", "ideal", "mybank", "p24", "trustly"
	};

	private static final Log _log = LogFactory.getLog(
		SetUpPaymentRestController.class);

}