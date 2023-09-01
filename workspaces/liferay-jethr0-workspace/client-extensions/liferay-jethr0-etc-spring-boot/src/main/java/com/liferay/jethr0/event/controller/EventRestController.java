/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.controller;

import com.liferay.jethr0.event.handler.EventHandler;
import com.liferay.jethr0.event.handler.EventHandlerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	public ResponseEntity<String> process(@RequestBody String body) {
		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + body);
		}

		JSONObject bodyJSONObject = new JSONObject(body);

		EventHandler.EventType eventType = EventHandler.EventType.valueOf(
			bodyJSONObject.optString("eventTrigger"));

		if ((eventType == EventHandler.EventType.BUILD_COMPLETED) ||
			(eventType == EventHandler.EventType.BUILD_STARTED) ||
			(eventType == EventHandler.EventType.COMPUTER_BUSY) ||
			(eventType == EventHandler.EventType.COMPUTER_IDLE) ||
			(eventType == EventHandler.EventType.COMPUTER_OFFLINE) ||
			(eventType == EventHandler.EventType.COMPUTER_ONLINE) ||
			(eventType ==
				EventHandler.EventType.COMPUTER_TEMPORARILY_OFFLINE) ||
			(eventType == EventHandler.EventType.COMPUTER_TEMPORARILY_ONLINE) ||
			(eventType == EventHandler.EventType.CREATE_BUILD) ||
			(eventType == EventHandler.EventType.CREATE_JENKINS_COHORT) ||
			(eventType == EventHandler.EventType.CREATE_JOB) ||
			(eventType == EventHandler.EventType.QUEUE_JOB)) {

			EventHandler eventHandler = _eventHandlerFactory.newEventHandler(
				bodyJSONObject);

			if (eventHandler == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			try {
				return new ResponseEntity<>(
					eventHandler.process(), HttpStatus.OK);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}

				return new ResponseEntity<>(
					exception.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<>("{}", HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		EventRestController.class);

	@Autowired
	private EventHandlerFactory _eventHandlerFactory;

}