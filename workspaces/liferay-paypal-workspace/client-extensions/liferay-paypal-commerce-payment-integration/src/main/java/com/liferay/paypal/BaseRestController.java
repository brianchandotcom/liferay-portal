/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.paypal;

import org.apache.tomcat.util.codec.binary.Base64;

import org.json.JSONObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
public class BaseRestController
	extends com.liferay.client.extension.util.spring.boot.BaseRestController {

	protected String getAuthorization(JSONObject jsonObject) {
		String authorization =
			jsonObject.getString("clientId") + ":" +
				jsonObject.getString("clientSecret");

		JSONObject authorizationRequestJSONObject = new JSONObject(
			WebClient.create(
				getPayPalURL(jsonObject.getString("mode"))
			).post(
			).uri(
				"/v1/oauth2/token"
			).accept(
				MediaType.APPLICATION_JSON
			).contentType(
				MediaType.APPLICATION_FORM_URLENCODED
			).header(
				HttpHeaders.AUTHORIZATION,
				"Basic " + Base64.encodeBase64String(authorization.getBytes())
			).bodyValue(
				"grant_type=client_credentials"
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		return authorizationRequestJSONObject.getString("access_token");
	}

	protected String getPayPalURL(String mode) {
		if (mode.equals("live")) {
			return "https://api-m.paypal.com";
		}

		return "https://api-m.sandbox.paypal.com";
	}

}