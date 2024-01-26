/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner.rabbitmq;

import com.liferay.partner.consumer.MessageConsumer;
import com.liferay.petra.string.StringBundler;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Jair Medeiros
 */
public class QueueConsumer extends DefaultConsumer {

	public QueueConsumer(
		Channel channel, Map<String, MessageConsumer> messageConsumerMap) {

		super(channel);

		_messageConsumerMap = messageConsumerMap;
	}

	@Override
	public void handleDelivery(
		String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
		byte[] body) {

		long deliveryTag = envelope.getDeliveryTag();
		String routingKey = envelope.getRoutingKey();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Received message ", deliveryTag, " with routing key ",
					routingKey));
		}

		String message = null;

		try {
			message = new String(body, "UTF-8");
		}
		catch (Exception exception) {
			_log.error(exception);

			return;
		}

		Channel channel = getChannel();

		MessageConsumer messageConsumer = _messageConsumerMap.get(routingKey);

		try {
			if (messageConsumer == null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"No parser found for message with routing key " +
							routingKey);
				}

				channel.basicAck(deliveryTag, false);

				return;
			}

			if ((message == null) || message.isEmpty()) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Message ", deliveryTag, " with routing key ",
							routingKey, " contained no data"));
				}

				channel.basicReject(deliveryTag, false);

				return;
			}

			try {
				messageConsumer.consume(message);
			}
			catch (Exception exception) {
				_log.error(
					StringBundler.concat(
						"Message ", deliveryTag, " with routing key ",
						routingKey, " cannot be processed", exception));

				channel.basicReject(deliveryTag, false);

				return;
			}

			channel.basicAck(deliveryTag, false);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactory.getLog(QueueConsumer.class);

	private final Map<String, MessageConsumer> _messageConsumerMap;

}