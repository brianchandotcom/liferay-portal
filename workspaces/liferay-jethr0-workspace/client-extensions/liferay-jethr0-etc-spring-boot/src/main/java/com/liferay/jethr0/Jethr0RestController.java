/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0;

import com.liferay.jethr0.entity.dalo.BaseDALO;
import com.liferay.jethr0.event.EventHandler;
import com.liferay.jethr0.event.github.GitHubEventProcessor;
import com.liferay.jethr0.event.liferay.LiferayEventHandlerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Hashimoto
 */
@RestController
public class Jethr0RestController {

	@PostMapping("/object-actions")
	public ResponseEntity<String> action(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String body) {

		try {
			EventHandler eventHandler =
				_liferayEventHandlerFactory.newEventHandler(
					new JSONObject(body));

			return new ResponseEntity<>(eventHandler.process(), HttpStatus.OK);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@GetMapping("/ready")
	public String ready() {
		return "READY";
	}

	@PostMapping("/webhook")
	public String webhook(@RequestBody String payload) {
		try {
			JSONObject payloadJSONObject = new JSONObject(payload);

			_gitHubEventProcessor.sendMessage(payloadJSONObject.toString());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return "{}";
	}

	private static final Log _log = LogFactory.getLog(BaseDALO.class);

	@Autowired
	private GitHubEventProcessor _gitHubEventProcessor;

	@Autowired
	private LiferayEventHandlerFactory _liferayEventHandlerFactory;

}