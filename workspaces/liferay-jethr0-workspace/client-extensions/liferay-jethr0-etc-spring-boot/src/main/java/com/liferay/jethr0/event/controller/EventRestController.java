/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.controller;

import com.liferay.jethr0.event.handler.EventHandler;
import com.liferay.jethr0.event.handler.EventHandlerFactory;
import com.liferay.jethr0.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Hashimoto
 */
@RequestMapping("/events")
@RestController
public class EventRestController {

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> process(@RequestBody String message) {
		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + message);
		}

		JSONObject messageJSONObject = null;

		try {
			messageJSONObject = new JSONObject(message);
		}
		catch (JSONException jsonException) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsonException);
			}

			return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
		}

		String eventTypeString = messageJSONObject.optString("eventType");

		if (StringUtil.isNullOrEmpty(eventTypeString)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Missing \"eventType\" from message JSON");
			}

			return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
		}

		EventHandler.EventType eventType = EventHandler.EventType.get(
			eventTypeString);

		if (eventType == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Invalid \"eventType\": " + eventTypeString);
			}

			return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
		}

		EventHandler eventHandler = null;

		try {
			eventHandler = _eventHandlerFactory.newEventHandler(
				eventType, messageJSONObject);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			if (_log.isWarnEnabled()) {
				_log.warn(illegalArgumentException);
			}

			return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
		}

		try {
			return new ResponseEntity<>(eventHandler.process(), HttpStatus.OK);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(eventType + ": " + exception.getMessage());
			}

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	private static final Log _log = LogFactory.getLog(
		EventRestController.class);

	@Autowired
	private EventHandlerFactory _eventHandlerFactory;

}