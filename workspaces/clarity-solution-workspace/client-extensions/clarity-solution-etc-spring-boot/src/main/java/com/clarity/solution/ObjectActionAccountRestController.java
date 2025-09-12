/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.clarity.solution;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Invoked when a Distributor Application is approved and an Account
 * needs to be created.
 *
 * @author dnebing
 * @author Mumen Tayyem
 */
@RequestMapping("/object/action/account")
@RestController
public class ObjectActionAccountRestController extends BaseRestController {

	@Autowired
	public ObjectActionAccountRestController(
		AccountCreationRequestQueueManager queueManager) {

		_queueManager = queueManager;
	}

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		AccountCreationRequest accountCreationRequest =
			new AccountCreationRequest(json, jwt);

		_queueManager.enqueue(accountCreationRequest);

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private final AccountCreationRequestQueueManager _queueManager;

}