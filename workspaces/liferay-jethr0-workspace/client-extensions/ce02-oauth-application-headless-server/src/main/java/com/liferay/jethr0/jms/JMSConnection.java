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
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration("jmsConnection")
public class JMSConnection {

	public void connect() {
		synchronized (_log) {
			if (_connection != null) {
				return;
			}

			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				_jmsBrokerURL);

			try {
				_connection = connectionFactory.createConnection();

				_connection.start();

				if (_log.isDebugEnabled()) {
					_log.debug("Created JMS connection to " + _jmsBrokerURL);
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
		}
	}

	public void disconnect() {
		synchronized (_log) {
			try {
				if (_connection != null) {
					_connection.close();
				}
			}
			catch (JMSException jmsException) {
				throw new RuntimeException(jmsException);
			}
			finally {
				_connection = null;
			}
		}
	}

	public Connection getConnection() {
		synchronized (_log) {
			connect();

			return _connection;
		}
	}

	private static final Log _log = LogFactory.getLog(JMSConnection.class);

	private Connection _connection;

	@Value("${jms.broker.url}")
	private String _jmsBrokerURL;

}