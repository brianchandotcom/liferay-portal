/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ups;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alessio Antonio Rendina
 */
@RequestMapping("/options-enabled")
@RestController
public class OptionsEnabledRestController extends OptionsRestController {

	@Override
	@PostMapping
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		return post(jwt, json, _log);
	}

	private static final Log _log = LogFactory.getLog(
		OptionsEnabledRestController.class);

}