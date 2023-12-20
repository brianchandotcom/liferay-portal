/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
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
 * @author Luca Pellizzon
 */
@RequestMapping("/shipping-engine-options-enabled")
@RestController
public class ShippingEngineOptionsEnabledController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		log(jwt, _log, json);

		JSONObject shippingOptionsJSONObject = new JSONObject();

		JSONArray shippingOptionJSONArray = new JSONArray();

		List<ShippingEngineOption> shippingEngineOptionList =
			ShippingEngineOptionsUtil.getShippingEngineOptionList();

		ObjectWriter objectWriter = new ObjectMapper(
		).writer(
		).withDefaultPrettyPrinter();

		for (int i = 1; i < shippingEngineOptionList.size(); i++) {
			JSONObject shippingOptionJSONObject = new JSONObject(
				objectWriter.writeValueAsString(
					shippingEngineOptionList.get(i)));

			shippingOptionJSONArray.put(shippingOptionJSONObject);
		}

		shippingOptionsJSONObject.put(
			"shippingOptions", shippingOptionJSONArray);

		log(jwt, _log, shippingOptionsJSONObject.toString());

		return new ResponseEntity<>(
			shippingOptionsJSONObject.toString(), HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		ShippingEngineOptionsEnabledController.class);

}