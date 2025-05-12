/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Manuele Castro
 */
@RequestMapping("/captcha/validation")
@RestController
public class CaptchaValidationRestController extends BaseRestController {

	public CaptchaValidationRestController(
		RestTemplateBuilder restTemplateBuilder) {

		_restTemplate = restTemplateBuilder.build();
	}

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		JSONObject jsonObject = new JSONObject(json);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

		body.add("secret", "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe");
		body.add("remoteip", jsonObject.getString("remoteip"));
		body.add("response", jsonObject.getString("response"));

		JSONObject siteVerifyJSONObject = new JSONObject(
			_verifySite(
				body, "https://www.google.com/recaptcha/api/siteverify"
			).getBody());

		if (!siteVerifyJSONObject.getBoolean("success")) {
			JSONArray errorCodesJSONArray = siteVerifyJSONObject.getJSONArray(
				"error-codes");

			siteVerifyJSONObject.put("error-codes", errorCodesJSONArray);
		}

		return new ResponseEntity<>(
			siteVerifyJSONObject.toString(), HttpStatus.OK);
	}

	private ResponseEntity<String> _verifySite(
		MultiValueMap<String, String> body, String url) {

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		return _restTemplate.postForEntity(
			url, new HttpEntity<>(body, httpHeaders), String.class);
	}

	private static final Log _log = LogFactory.getLog(
		CaptchaValidationRestController.class);

	private final RestTemplate _restTemplate;

}