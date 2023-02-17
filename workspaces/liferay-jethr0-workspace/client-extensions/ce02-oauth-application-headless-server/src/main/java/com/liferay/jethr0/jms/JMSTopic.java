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

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Michael Hashimoto
 */
public class JMSTopic {

	public String getTopicName() {
		return _topicName;
	}

	public void publish(String message) {
		try {
			MessageProducer messageProducer = _session.createProducer(_topic);

			TextMessage textMessage = _session.createTextMessage();

			textMessage.setText(message);

			messageProducer.send(textMessage);
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}
	}

	public void subscribe(String sessionName, MessageListener messageListener) {
		synchronized (_messageConsumers) {
			try {
				MessageConsumer messageConsumer = _messageConsumers.remove(
					sessionName);

				if (messageConsumer == null) {
					messageConsumer = _session.createConsumer(_topic);

					_messageConsumers.put(sessionName, messageConsumer);
				}

				messageConsumer.setMessageListener(messageListener);

				if (_log.isDebugEnabled()) {
					_log.debug("[" + _topicName + "] Subscribed to topic");
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
		}
	}

	public void unsubscribe(String sessionName) {
		synchronized (_messageConsumers) {
			MessageConsumer messageConsumer = _messageConsumers.remove(
				sessionName);

			if (messageConsumer == null) {
				return;
			}

			try {
				messageConsumer.close();

				if (_log.isDebugEnabled()) {
					_log.debug("[" + _topicName + "] Unsubscribed to topic");
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
		}
	}

	protected JMSTopic(Connection connection, String topicName) {
		_topicName = topicName;

		try {
			_session = connection.createSession(
				false, Session.AUTO_ACKNOWLEDGE);

			_topic = _session.createTopic(_topicName);
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}
	}

	private static final Log _log = LogFactory.getLog(JMSTopic.class);

	private final Map<String, MessageConsumer> _messageConsumers =
		new HashMap<>();
	private final Session _session;
	private final Topic _topic;
	private final String _topicName;

}