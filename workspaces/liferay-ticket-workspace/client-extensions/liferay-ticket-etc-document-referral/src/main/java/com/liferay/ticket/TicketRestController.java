/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.ticket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Allen Ziegenfus
 */
@RestController
public class TicketRestController {

	@GetMapping("/ready")
	public String getReady() {
		return "READY";
	}

	@PostMapping("/ticket/object/action/documentation/referral")
	public ResponseEntity<String> postTicketObjectAction1(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (_log.isInfoEnabled()) {
			_log.info("JWT Claims: " + jwt.getClaims());
			_log.info("JWT ID: " + jwt.getId());
			_log.info("JWT Subject: " + jwt.getSubject());
		}

		try {
			JSONObject jsonObject = new JSONObject(json);

			if (_log.isInfoEnabled()) {
				_log.info("JSON INPUT: \n\n" + jsonObject.toString(4) + "\n");
			}

			_documentationReferral.addDocumentationReferralAndQueue(
				_lxcDXPServerProtocol, _lxcDXPMainDomain,
				jwt.getTokenValue(), jsonObject);
		}
		catch (Exception exception) {
			_log.error("JSON: " + json, exception);
		}

		return new ResponseEntity<>(json, HttpStatus.CREATED);
	}

	private static final Log _log = LogFactory.getLog(
		TicketRestController.class);

	private DocumentationReferral _documentationReferral =
		new DocumentationReferral();

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}