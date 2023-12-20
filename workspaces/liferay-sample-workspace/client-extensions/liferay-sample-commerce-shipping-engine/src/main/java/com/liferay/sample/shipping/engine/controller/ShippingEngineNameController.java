/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample.shipping.engine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
@RequestMapping("/shipping/engine/name")
@RestController
public class ShippingEngineNameController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			Map<String, String> requestBodyMap = objectMapper.readValue(
				json, HashMap.class);

			String localeLanguageTag = requestBodyMap.getOrDefault(
				"locale", "en_US");

			Locale locale = Locale.forLanguageTag(localeLanguageTag);

			return new ResponseEntity<>(
				new JSONObject(
				).put(
					"name", getLanguageProperty(locale, "shipping-engine-name")
				).toString(),
				HttpStatus.OK);
		}
		catch (Exception exception) {
			log(jwt, _log, exception.toString());

			return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
		}
	}

	private static final Log _log = LogFactory.getLog(
		ShippingEngineNameController.class);

}