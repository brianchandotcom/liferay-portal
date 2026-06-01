/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.google.search.console.controller;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.seo.studio.google.search.console.service.GoogleOAuth2Service;
import com.liferay.seo.studio.google.search.console.service.LiferayObjectService;

import java.io.IOException;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pei-Jung Lan
 */
@CrossOrigin("*")
@RequestMapping("/revoke")
@RestController
public class RevokeRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<Void> post(@RequestBody Map<String, Object> body)
		throws InterruptedException, IOException {

		Object seoStudioInstanceIdObject = body.get("seoStudioInstanceId");

		if (!(seoStudioInstanceIdObject instanceof Number)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Number seoStudioInstanceIdNumber = (Number)seoStudioInstanceIdObject;

		long seoStudioInstanceId = seoStudioInstanceIdNumber.longValue();

		LiferayObjectService.CredentialEntry credentialEntry =
			_liferayObjectService.fetchCredentialEntry(seoStudioInstanceId);

		if (credentialEntry == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		String token = credentialEntry.getRefreshToken();

		if (token == null) {
			token = credentialEntry.getAccessToken();
		}

		if (token != null) {
			try {
				_googleOAuth2Service.revokeToken(token);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to revoke Google token", exception);
				}
			}
		}

		_liferayObjectService.deleteCredentialEntry(credentialEntry.getId());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		RevokeRestController.class);

	@Autowired
	private GoogleOAuth2Service _googleOAuth2Service;

	@Autowired
	private LiferayObjectService _liferayObjectService;

}