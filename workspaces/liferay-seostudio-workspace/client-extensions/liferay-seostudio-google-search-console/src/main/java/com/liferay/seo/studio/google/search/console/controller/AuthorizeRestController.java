/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.google.search.console.controller;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.seo.studio.google.search.console.service.GoogleOAuth2Service;
import com.liferay.seo.studio.google.search.console.service.LiferayObjectService;
import com.liferay.seo.studio.google.search.console.service.StateTokenService;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import java.net.URI;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Pei-Jung Lan
 */
@RequestMapping("/authorize")
@RestController
public class AuthorizeRestController extends BaseRestController {

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> post(
			HttpServletRequest httpServletRequest,
			@RequestParam String redirectURL,
			@RequestParam long seoStudioInstanceId)
		throws InterruptedException, IOException {

		URI redirectURI = URI.create(redirectURL);

		if (!_mainDomain.equals(redirectURI.getAuthority()) ||
			!_protocol.equals(redirectURI.getScheme())) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		LiferayObjectService.CredentialEntry credentialEntry =
			_liferayObjectService.fetchCredentialEntry(seoStudioInstanceId);

		if (credentialEntry == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		String stateString = _stateTokenService.generateState(
			redirectURL, seoStudioInstanceId);

		String callbackURL = ServletUriComponentsBuilder.fromContextPath(
			httpServletRequest
		).path(
			"/callback"
		).toUriString();

		String authorizationURL = _googleOAuth2Service.buildAuthorizationURL(
			callbackURL, credentialEntry.getClientId(), stateString);

		return ResponseEntity.ok(
			new JSONObject(
			).put(
				"authorizationUrl", authorizationURL
			).toString());
	}

	@Autowired
	private GoogleOAuth2Service _googleOAuth2Service;

	@Autowired
	private LiferayObjectService _liferayObjectService;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _mainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _protocol;

	@Autowired
	private StateTokenService _stateTokenService;

}