/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import com.liferay.client.extension.util.spring.boot.ClientExtensionUtilSpringBootComponentScan;
import com.liferay.partner.consumer.ContactDeleteKoroneikiMessageConsumer;
import com.liferay.partner.consumer.MessageConsumer;
import com.liferay.partner.rabbitmq.QueueConsumer;
import com.liferay.portal.kernel.util.HashMapBuilder;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author Jair Medeiros
 */
@Import(ClientExtensionUtilSpringBootComponentScan.class)
@SpringBootApplication
public class PartnerSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartnerSpringBootApplication.class, args);
	}

	@PostConstruct
	public void postConstruct() {
		try {
			ConnectionFactory connectionFactory = new ConnectionFactory();

			connectionFactory.setAutomaticRecoveryEnabled(true);
			connectionFactory.setHost(_koroneikiSubscriberRabbitMQHost);
			connectionFactory.setPassword(_koroneikiSubscriberRabbitMQPassword);
			connectionFactory.setPort(_koroneikiSubscriberRabbitMQPort);
			connectionFactory.setUsername(_koroneikiSubscriberRabbitMQUserName);

			if (_koroneikiSubscriberRabbitMQSSL) {
				connectionFactory.useSslProtocol();
			}

			_connection = connectionFactory.newConnection();

			try {
				Channel channel = _connection.createChannel();

				QueueConsumer queueConsumer = new QueueConsumer(
					channel,
					HashMapBuilder.<String, MessageConsumer>put(
						"koroneiki.contact.delete",
						new ContactDeleteKoroneikiMessageConsumer()
					).build());

				channel.basicConsume(
					_koroneikiSubscriberRabbitMQQueueName, _QUEUE_AUTO_ACK,
					queueConsumer);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@PreDestroy
	public void preDestroy() {
		try {
			if (_connection != null) {
				_connection.close();
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Disconnected from RabbitMQ");
		}
	}

	private static final boolean _QUEUE_AUTO_ACK = false;

	private static final Log _log = LogFactory.getLog(
		PartnerSpringBootApplication.class);

	private Connection _connection;

	@Value("${koroneiki.subscriber.rabbitmq.host}")
	private String _koroneikiSubscriberRabbitMQHost;

	@Value("${koroneiki.subscriber.rabbitmq.password}")
	private String _koroneikiSubscriberRabbitMQPassword;

	@Value("${koroneiki.subscriber.rabbitmq.port}")
	private int _koroneikiSubscriberRabbitMQPort;

	@Value("${koroneiki.subscriber.rabbitmq.queuename}")
	private String _koroneikiSubscriberRabbitMQQueueName;

	@Value("${koroneiki.subscriber.rabbitmq.ssl}")
	private Boolean _koroneikiSubscriberRabbitMQSSL;

	@Value("${koroneiki.subscriber.rabbitmq.username}")
	private String _koroneikiSubscriberRabbitMQUserName;

}