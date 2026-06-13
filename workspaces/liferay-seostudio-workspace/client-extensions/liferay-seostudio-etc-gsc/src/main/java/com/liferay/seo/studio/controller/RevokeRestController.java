/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.controller;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.seo.studio.service.GSCCredentialsService;
import com.liferay.seo.studio.service.GoogleOAuth2Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pei-Jung Lan
 */
@CrossOrigin("*")
@RequestMapping("/revoke")
@RestController
public class RevokeRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<Void> post(@RequestParam long seoStudioDomainId) {
		GSCCredentialsService.Credentials credentials =
			_gscCredentialsService.fetchCredentials(seoStudioDomainId);

		if (credentials == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		String token = credentials.getRefreshToken();

		if (Validator.isNull(token)) {
			token = credentials.getAccessToken();
		}

		if (Validator.isNotNull(token)) {
			try {
				_googleOAuth2Service.revokeToken(token);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to revoke Google token", exception);
				}
			}
		}

		_gscCredentialsService.deleteCredentials(credentials.getId());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		RevokeRestController.class);

	@Autowired
	private GoogleOAuth2Service _googleOAuth2Service;

	@Autowired
	private GSCCredentialsService _gscCredentialsService;

}