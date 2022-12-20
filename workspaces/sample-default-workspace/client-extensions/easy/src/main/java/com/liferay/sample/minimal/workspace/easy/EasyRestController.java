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

package com.liferay.sample.minimal.workspace.easy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
@RestController
public class EasyRestController {

	@GetMapping("/dad-joke")
	public ResponseEntity<String> getDadJoke(@AuthenticationPrincipal Jwt jwt) {
		return new ResponseEntity<>(
			WebClient.create(
			).get(
			).uri(
				"https://icanhazdadjoke.com"
			).accept(
				MediaType.TEXT_PLAIN
			).retrieve(
			).bodyToMono(
				String.class
			).block(),
			HttpStatus.OK);
	}

	@PostMapping("/easy-object/action/1")
	public ResponseEntity<String> postEasyObjectAction1(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (_log.isInfoEnabled()) {
			_log.info("JWT Claims: " + jwt.getClaims());
			_log.info("JWT ID: " + jwt.getId());
			_log.info("JWT Subject: " + jwt.getSubject());

			try {
				JSONObject jsonObject = new JSONObject(json);

				_log.info("\n\n" + jsonObject.toString(4) + "\n");
			}
			catch (Exception exception) {
				_log.error("JSON: " + json, exception);
			}
		}

		return new ResponseEntity<>(json, HttpStatus.CREATED);
	}

	@PostMapping("/easy-object/action/2")
	public ResponseEntity<String> postEasyObjectAction2(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (_log.isInfoEnabled()) {
			_log.info("JWT Claims: " + jwt.getClaims());
			_log.info("JWT ID: " + jwt.getId());
			_log.info("JWT Subject: " + jwt.getSubject());

			try {
				JSONObject jsonObject = new JSONObject(json);

				_log.info("\n\n" + jsonObject.toString(4) + "\n");
			}
			catch (Exception exception) {
				_log.error("JSON: " + json, exception);
			}
		}

		return new ResponseEntity<>(json, HttpStatus.CREATED);
	}

	@PostMapping("/easy-workflow/action/1")
	public ResponseEntity<String> postEasyWorkflowAction1(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (_log.isInfoEnabled()) {
			_log.info("JWT Claims: " + jwt.getClaims());
			_log.info("JWT ID: " + jwt.getId());
			_log.info("JWT Subject: " + jwt.getSubject());
		}

		try {
			_easyWorkflowHelper.transitionTheTask(
				json, jwt.getTokenValue(),
				(workflowDetails, transitionNames) -> "approve");
		}
		catch (Exception exception) {
			_log.error("JSON: " + json, exception);

			return new ResponseEntity<>(json, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(EasyRestController.class);

	@Autowired
	private EasyWorkflowHelper _easyWorkflowHelper;

}