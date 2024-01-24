/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample;

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
@RequestMapping("/option-label")
@RestController
public class OptionLabelRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		log(jwt, _log, json);

		JSONObject jsonObject = new JSONObject(json);

		if (!jsonObject.has("name")) {
			return null;
		}

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"name", "Shipping Option Name"
			).toString(),
			HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		OptionLabelRestController.class);

}