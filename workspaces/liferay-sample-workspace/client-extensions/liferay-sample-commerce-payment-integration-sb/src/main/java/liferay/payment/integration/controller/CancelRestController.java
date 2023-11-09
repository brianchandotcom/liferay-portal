/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package liferay.payment.integration.controller;

import liferay.payment.integration.constants.CommercePaymentIntegrationConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Crescenzo Rega
 */
@RequestMapping("/cancel")
@RestController
public class CancelRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		try {
			String commercePaymentEntryJSON = processPayment(
				json, CommercePaymentIntegrationConstants.STATUS_CANCELLED);

			if (_log.isInfoEnabled()) {
				_log.info(
					"CommercePaymentEntryDto: " + commercePaymentEntryJSON);
			}

			return new ResponseEntity<>(
				commercePaymentEntryJSON, HttpStatus.OK);
		}
		catch (Exception exception) {
			log(jwt, _log, exception.toString());

			return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
		}
	}

	private static final Log _log = LogFactory.getLog(
		CancelRestController.class);

}