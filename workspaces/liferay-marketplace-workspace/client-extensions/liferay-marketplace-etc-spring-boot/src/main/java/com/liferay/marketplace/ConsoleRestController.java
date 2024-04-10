/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.marketplace.console.service.ConsoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Keven Leone
 */
@RequestMapping("/console")
@RestController
public class ConsoleRestController extends BaseRestController {

	@GetMapping("projects-usage")
	public String getProjectsUsage(
			@AuthenticationPrincipal Jwt jwt,
			@RequestParam(required = false) String emailAddress)
		throws Exception {

		if (emailAddress == null) {
			emailAddress = String.valueOf(
				jwt.getClaims(
				).get(
					"username"
				));
		}

		String finalEmailAddress = emailAddress;

		return WebClient.create(
			_consoleAuthURL
		).get(
		).uri(
			uriBuilder -> uriBuilder.path(
				"/admin/user-projects-plan-usage"
			).queryParam(
				"userEmail", finalEmailAddress
			).build()
		).header(
			HttpHeaders.AUTHORIZATION,
			"Bearer " + _consoleService.getAuthorization()
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	@Value("${liferay.marketplace.console.auth.url}")
	private String _consoleAuthURL;

	@Autowired
	private ConsoleService _consoleService;

}