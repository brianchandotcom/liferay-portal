/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.paypal;

import com.liferay.portal.kernel.util.HashMapBuilder;

import org.apache.tomcat.util.codec.binary.Base64;

import org.json.JSONObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
public class BaseRestController
	extends com.liferay.client.extension.util.spring.boot3.BaseRestController {

	protected String getAuthorization(JSONObject jsonObject) {
		JSONObject authorizationRequestJSONObject = new JSONObject(
			post(
				"grant_type=client_credentials",
				HashMapBuilder.put(
					HttpHeaders.AUTHORIZATION,
					() -> {
						String authorization =
							jsonObject.getString("clientId") + ":" +
								jsonObject.getString("clientSecret");

						return "Basic " +
							Base64.encodeBase64String(authorization.getBytes());
					}
				).put(
					HttpHeaders.CONTENT_TYPE,
					MediaType.APPLICATION_FORM_URLENCODED_VALUE
				).build(),
				createURI(
					getPayPalURL(jsonObject.getString("mode")),
					"/v1/oauth2/token")));

		return authorizationRequestJSONObject.getString("access_token");
	}

	protected String getLiferayURL() {
		return lxcDXPServerProtocol + "://" + lxcDXPMainDomain;
	}

	protected String getPayPalURL(String mode) {
		if (mode.equals("live")) {
			return "https://api-m.paypal.com";
		}

		return "https://api-m.sandbox.paypal.com";
	}

}