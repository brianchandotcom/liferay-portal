/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.clarity.solution;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mumen Tayyem
 */
@RequestMapping("/ready")
@RestController
public class ReadyRestController extends BaseRestController {

	@GetMapping
	public String get() {
		return "READY";
	}

	private static final Log _log = LogFactory.getLog(
		ReadyRestController.class);

}