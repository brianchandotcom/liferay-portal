/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import com.liferay.petra.string.StringBundler;

import com.rabbitmq.client.Channel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author Jair Medeiros
 */
@Component
public class QueueListener {

	@RabbitListener(
		bindings = {
			@QueueBinding(
				exchange = @Exchange("koroneiki"), key = "account.update",
				value = @Queue("${spring.rabbitmq.template.default-receive-queue}")
			)
		}
	)
	public void accountUpdateListener(
		Message message, Channel channel,
		@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {

		String receivedRoutingKey = message.getMessageProperties(
		).getReceivedRoutingKey();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Received message ", deliveryTag, " with routing key ",
					receivedRoutingKey));
		}

		try {
			String body = new String(message.getBody(), "UTF-8");

			if ((body == null) || body.isEmpty()) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Message ", deliveryTag, " with routing key ",
							receivedRoutingKey, " contained no data"));
				}

				channel.basicReject(deliveryTag, false);

				return;
			}

			System.out.println("Update: " + body);

			channel.basicAck(deliveryTag, false);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactory.getLog(QueueListener.class);

}