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

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Michael Hashimoto
 */
public class JMSQueue {

	public String getQueueName() {
		return _queueName;
	}

	public void publish(String message) {
		try {
			MessageProducer messageProducer = _session.createProducer(_queue);

			TextMessage textMessage = _session.createTextMessage();

			textMessage.setText(message);

			messageProducer.send(textMessage);
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}
	}

	public void subscribe(MessageListener messageListener) {
		synchronized (_log) {
			if (_messageConsumer != null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"[" + _queueName + "] Already subscribed to queue");
				}

				throw new RuntimeException(
					"Unable to subscribe to " + _queueName);
			}

			try {
				_messageConsumer = _session.createConsumer(_queue);

				_messageConsumer.setMessageListener(messageListener);

				if (_log.isDebugEnabled()) {
					_log.debug("[" + _queueName + "] Subscribed to queue");
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
		}
	}

	public void unsubscribe() {
		synchronized (_log) {
			if (_messageConsumer == null) {
				return;
			}

			try {
				_messageConsumer.close();

				_messageConsumer = null;

				if (_log.isDebugEnabled()) {
					_log.debug("[" + _queueName + "] Unsubscribed to queue");
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
		}
	}

	protected JMSQueue(Connection connection, String queueName) {
		_queueName = queueName;

		try {
			_session = connection.createSession(
				false, Session.AUTO_ACKNOWLEDGE);

			_queue = _session.createQueue(_queueName);
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}
	}

	private static final Log _log = LogFactory.getLog(JMSQueue.class);

	private MessageConsumer _messageConsumer;
	private final Queue _queue;
	private final String _queueName;
	private final Session _session;

}