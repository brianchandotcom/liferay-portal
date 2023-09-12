/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.controller;

import com.liferay.jethr0.event.handler.EventHandler;
import com.liferay.jethr0.event.handler.EventHandlerFactory;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
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
public class EventJmsController {

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
			(eventType == EventHandler.EventType.CREATE_JENKINS_COHORT) ||
			(eventType == EventHandler.EventType.CREATE_JOB) ||
			(eventType == EventHandler.EventType.QUEUE_JOB)) {

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

				throw new RuntimeException(exception);
			}
		}
	}

	public void send(JenkinsServerEntity jenkinsServerEntity, String message) {
		String queueName = StringUtil.combine(
			"jenkins-builds[", jenkinsServerEntity.getName(), "]");

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringUtil.combine("[", queueName, "] Send message ", message));
		}

		_jmsTemplate.convertAndSend(queueName, message);
	}

	private static final Log _log = LogFactory.getLog(EventJmsController.class);

	@Autowired
	private EventHandlerFactory _eventHandlerFactory;

	@Value("${jms.jenkins.event.queue}")
	private String _jmsJenkinsEventQueue;

	@Autowired
	private JmsTemplate _jmsTemplate;

}