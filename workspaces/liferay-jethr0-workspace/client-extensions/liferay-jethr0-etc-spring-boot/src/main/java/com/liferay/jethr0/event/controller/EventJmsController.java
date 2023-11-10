/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.controller;

import com.liferay.jethr0.event.handler.EventHandler;
import com.liferay.jethr0.event.handler.EventHandlerFactory;
import com.liferay.jethr0.util.StringUtil;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONException;
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
		destination = "${JETHR0_JMS_QUEUE_JENKINS_TO_JETHR0:jenkins-to-jethr0}"
	)
	public void processFromJenkins(String message) {
		_process(message);
	}

	@JmsListener(
		destination = "${JETHR0_JMS_QUEUE_JRP_TO_JETHR0:jrp-to-jethr0}"
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

		JSONObject messageJSONObject = null;

		try {
			messageJSONObject = new JSONObject(message);
		}
		catch (JSONException jsonException) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsonException);
			}

			return;
		}

		String eventTypeString = messageJSONObject.optString("eventType");

		if (StringUtil.isNullOrEmpty(eventTypeString)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Missing 'eventType' from message json");
			}

			return;
		}

		EventHandler.EventType eventType = EventHandler.EventType.get(
			eventTypeString);

		if (eventType == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Invalid 'eventType': " + eventTypeString);
			}

			return;
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

			return;
		}

		try {
			eventHandler.process();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(eventType + ": " + exception.getMessage());
			}
		}
	}

	private static final Log _log = LogFactory.getLog(EventJmsController.class);

	@Autowired
	private EventHandlerFactory _eventHandlerFactory;

	@Value("${JETHR0_JMS_QUEUE_JETHR0_TO_JENKINS:jethr0-to-jenkins}")
	private String _jmsQueueJethr0ToJenkins;

	@Value("${JETHR0_JMS_QUEUE_JETHR0_TO_JRP:jethr0-to-jrp}")
	private String _jmsQueueJethr0ToJRP;

	@Autowired
	private JmsTemplate _jmsTemplate;

}