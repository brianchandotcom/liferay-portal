/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.controller;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.seo.studio.service.GSCCredentialsService;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pei-Jung Lan
 */
@CrossOrigin("*")
@RequestMapping("/status")
@RestController
public class StatusRestController extends BaseRestController {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> get(@RequestParam long seoStudioDomainId) {
		GSCCredentialsService.Credentials credentials =
			_gscCredentialsService.fetchCredentials(seoStudioDomainId);

		if ((credentials != null) &&
			Validator.isNotNull(credentials.getEmailAddress())) {

			return ResponseEntity.ok(
				new JSONObject(
				).put(
					"connected", true
				).put(
					"emailAddress", credentials.getEmailAddress()
				).toString());
		}

		return ResponseEntity.ok(
			new JSONObject(
			).put(
				"connected", false
			).toString());
	}

	@Autowired
	private GSCCredentialsService _gscCredentialsService;

}