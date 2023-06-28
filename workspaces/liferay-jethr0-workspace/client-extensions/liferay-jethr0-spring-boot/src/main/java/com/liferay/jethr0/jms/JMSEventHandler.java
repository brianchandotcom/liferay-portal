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

package com.liferay.jethr0.jms;

import com.liferay.jethr0.event.handler.EventHandler;
import com.liferay.jethr0.event.handler.EventHandlerFactory;
import com.liferay.jethr0.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JMSEventHandler {

	@JmsListener(destination = "${jms.jenkins.event.queue}")
	public void process(String message) {
		if (_log.isDebugEnabled()) {
			_log.debug("Received " + message);
		}

		JSONObject messageJSONObject = new JSONObject(message);

		EventHandler.EventType eventType = EventHandler.EventType.valueOf(
			messageJSONObject.optString("eventTrigger"));

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
			(eventType == EventHandler.EventType.CREATE_PROJECT) ||
			(eventType == EventHandler.EventType.QUEUE_PROJECT)) {

			EventHandler eventHandler = _eventHandlerFactory.newEventHandler(
				messageJSONObject);

			if (eventHandler == null) {
				throw new RuntimeException();
			}

			try {
				eventHandler.process();
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}

				throw new RuntimeException();
			}
		}
	}

	public void send(String message) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringUtil.combine(
					"[", _jmsJenkinsBuildQueue, "] Send ", message));
		}

		_jmsTemplate.convertAndSend(_jmsJenkinsBuildQueue, message);
	}

	private static final Log _log = LogFactory.getLog(JMSEventHandler.class);

	@Autowired
	private EventHandlerFactory _eventHandlerFactory;

	@Value("${jms.jenkins.build.queue}")
	private String _jmsJenkinsBuildQueue;

	@Value("${jms.jenkins.event.queue}")
	private String _jmsJenkinsEventQueue;

	@Autowired
	private JmsTemplate _jmsTemplate;

}