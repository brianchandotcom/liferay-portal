/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.controller;

import com.liferay.jethr0.event.handler.EventHandler;
import com.liferay.jethr0.event.handler.EventHandlerFactory;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class EventJmsController {

	@JmsListener(
		destination = "${jethr0-jms-queue-jenkins-to-jethr0:jenkins-to-jethr0}"
	)
	public void processFromJenkins(String message) {
		_process(message);
	}

	@JmsListener(
		destination = "${jethr0-jms-queue-jrp-to-jethr0:jrp-to-jethr0}"
	)
	public void processFromJRP(String message) {
		_process(message);
	}

	public void sendToJenkins(
		String message, Map<String, String> messageProperties) {

		_jmsTemplate.convertAndSend(
			_jmsQueueJethr0ToJenkins, message,
			new MessagePostProcessor() {

				@Override
				public Message postProcessMessage(Message message)
					throws JMSException {

					for (Map.Entry<String, String> messageProperty :
							messageProperties.entrySet()) {

						message.setStringProperty(
							messageProperty.getKey(),
							messageProperty.getValue());
					}

					return message;
				}

			});
	}

	public void sendToJRP(
		String message, Map<String, String> messageProperties) {

		_jmsTemplate.convertAndSend(
			_jmsQueueJethr0ToJRP, message,
			new MessagePostProcessor() {

				@Override
				public Message postProcessMessage(Message message)
					throws JMSException {

					for (Map.Entry<String, String> messageProperty :
							messageProperties.entrySet()) {

						message.setStringProperty(
							messageProperty.getKey(),
							messageProperty.getValue());
					}

					return message;
				}

			});
	}

	private void _process(String message) {
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

	private static final Log _log = LogFactory.getLog(EventJmsController.class);

	@Autowired
	private EventHandlerFactory _eventHandlerFactory;

	@Value("${jethr0-jms-queue-jethr0-to-jenkins:jethr0-to-jenkins}")
	private String _jmsQueueJethr0ToJenkins;

	@Value("${jethr0-jms-queue-jethr0-to-jrp:jethr0-to-jrp}")
	private String _jmsQueueJethr0ToJRP;

	@Autowired
	private JmsTemplate _jmsTemplate;

}