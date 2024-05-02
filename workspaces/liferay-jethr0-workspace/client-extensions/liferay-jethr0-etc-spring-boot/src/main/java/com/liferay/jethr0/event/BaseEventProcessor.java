/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

/**
 * @author Michael Hashimoto
 */
@Configuration
public abstract class BaseEventProcessor implements EventProcessor {

	@Override
	public void receiveMessage(String message) {
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

		EventHandler eventHandler = null;

		try {
			EventHandlerFactory eventHandlerFactory = getEventHandlerFactory();

			eventHandler = eventHandlerFactory.newEventHandler(
				messageJSONObject);
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
				_log.warn(
					eventHandler.getClass() + ": " + exception.getMessage());
			}
		}
	}

	@Override
	public void sendMessage(String message) {
		sendMessage(message, null);
	}

	@Override
	public void sendMessage(
		String message, Map<String, String> messageProperties) {

		_jmsTemplate.convertAndSend(
			getOutboundQueueName(), message,
			new MessagePostProcessor() {

				@Override
				public Message postProcessMessage(Message message)
					throws JMSException {

					if (messageProperties == null) {
						return message;
					}

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

	protected abstract EventHandlerFactory getEventHandlerFactory();

	protected abstract String getOutboundQueueName();

	private static final Log _log = LogFactory.getLog(BaseEventProcessor.class);

	@Autowired
	private JmsTemplate _jmsTemplate;

}