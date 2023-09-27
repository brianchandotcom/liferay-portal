/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample;

import java.util.Objects;

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
 * @author Mateus Santana
 */
@RequestMapping("/object/validation/rule/1")
@RestController
public class ObjectValidationRule1RestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		JSONObject jsonObject = new JSONObject(json);

		if (Objects.equals(jsonObject.get("name"), "Invalid Name")) {
			jsonObject.put("validationCriteriaMet", false);
		}
		else {
			jsonObject.put("validationCriteriaMet", true);
		}

		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		ObjectValidationRule1RestController.class);

}