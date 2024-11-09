/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.paypal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Objects;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Brian I. Kim
 */
@RequestMapping("/render")
@RestController
public class RenderRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log);

		StringBuilder sb = new StringBuilder();

		JSONObject jsonObject = new JSONObject(json);

		long orderId = jsonObject.getLong("orderId");

		sb.append(
			WebClient.create(
				StringBundler.concat(
					getWebClientBaseURL(),
					"/o/headless-commerce-delivery-cart/v1.0/carts/", orderId,
					"/payment-url")
			).get(
			).accept(
				MediaType.TEXT_PLAIN
			).header(
				HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		if (jsonObject.has("callbackURL")) {
			sb.append("&callbackURL=");
			sb.append(jsonObject.getString("callbackURL"));
		}

		if (jsonObject.has("cancel")) {
			sb.append("&cancel=");
			sb.append(jsonObject.getBoolean("cancel"));
			delete(
				"Bearer " + jwt.getTokenValue(), StringPool.BLANK,
				"/o/c/b9k3paypalwebhooks/by-external-reference-code/" +
					jsonObject.getString("transactionCode"));
		}

		if (jsonObject.has("transactionCode")) {
			sb.append("&entryId=");
			sb.append(
				_getPaymentEntryId(
					jwt, orderId, jsonObject.getString("transactionCode")));
		}

		if (jsonObject.has("fundingSource")) {
			sb.append("&fundingSource=");
			sb.append(jsonObject.getString("fundingSource"));
		}

		if (jsonObject.has("redirect")) {
			sb.append("&redirect=");
			sb.append(jsonObject.getBoolean("redirect"));
		}

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"url", sb.toString()
			).toString(),
			HttpStatus.OK);
	}

	private String _getPaymentEntryId(
		Jwt jwt, long orderId, String transactionCode) {

		JSONObject paymentsJSONObject = new JSONObject(
			get(
				"Bearer " + jwt.getTokenValue(),
				StringBundler.concat(
					"/o/headless-commerce-admin-payment/v1.0/payments/?filter=",
					"relatedItemId eq ", orderId)));

		JSONArray itemsJSONArray = paymentsJSONObject.getJSONArray("items");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			if (Objects.equals(
					transactionCode,
					itemJSONObject.getString("transactionCode"))) {

				return String.valueOf(itemJSONObject.getInt("id"));
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactory.getLog(
		RenderRestController.class);

}