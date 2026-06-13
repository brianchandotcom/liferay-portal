/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.controller;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.seo.studio.service.GSCCredentialsService;
import com.liferay.seo.studio.service.GoogleOAuth2Service;
import com.liferay.seo.studio.service.StateTokenService;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Pei-Jung Lan
 */
@CrossOrigin("*")
@RequestMapping("/authorize")
@RestController
public class AuthorizeRestController extends BaseRestController {

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> post(
		HttpServletRequest httpServletRequest, @RequestParam String redirectURL,
		@RequestParam long seoStudioDomainId) {

		URI redirectURI;

		try {
			redirectURI = URI.create(redirectURL);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			_log.error(
				"Unable to parse redirect URL", illegalArgumentException);

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!_mainDomain.equals(redirectURI.getAuthority()) ||
			!_protocol.equals(redirectURI.getScheme())) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		GSCCredentialsService.Credentials credentials =
			_gscCredentialsService.fetchCredentials(seoStudioDomainId);

		if (credentials == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(
			new JSONObject(
			).put(
				"authorizationURL",
				_googleOAuth2Service.buildAuthorizationURL(
					ServletUriComponentsBuilder.fromContextPath(
						httpServletRequest
					).path(
						"/callback"
					).toUriString(),
					credentials.getClientId(),
					_stateTokenService.generateState(
						redirectURL, seoStudioDomainId))
			).toString());
	}

	private static final Log _log = LogFactory.getLog(
		AuthorizeRestController.class);

	@Autowired
	private GoogleOAuth2Service _googleOAuth2Service;

	@Autowired
	private GSCCredentialsService _gscCredentialsService;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _mainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _protocol;

	@Autowired
	private StateTokenService _stateTokenService;

}