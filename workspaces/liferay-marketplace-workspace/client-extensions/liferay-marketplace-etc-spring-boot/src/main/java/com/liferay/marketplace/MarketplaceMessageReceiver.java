/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

import com.liferay.marketplace.constants.MarketplaceConstants;

import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Caleb Hall
 */
public class MarketplaceMessageReceiver implements MessageReceiver {

	public MarketplaceMessageReceiver(String topic) {
		_topic = topic;
	}

	@Override
	public void receiveMessage(
		PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {

		if (_log.isInfoEnabled()) {
			ByteString byteString = pubsubMessage.getData();

			String messageBody = byteString.toStringUtf8();

			_log.info("Found message: " + messageBody);
		}

		try {
			if (Objects.equals(
					_topic, MarketplaceConstants.KORONEIKI_ACCOUNT_CREATE)) {

				// PLACEHOLDER

			}
			else if (Objects.equals(
						_topic,
						MarketplaceConstants.KORONEIKI_ACCOUNT_UPDATE)) {

				// PLACEHOLDER

			}
			else if (Objects.equals(
						_topic,
						MarketplaceConstants.KORONEIKI_ENTITLEMENT_CREATE)) {

				// PLACEHOLDER

			}

			ackReplyConsumer.ack();
		}
		catch (Exception exception) {
			_log.error("Error processing message", exception);

			ackReplyConsumer.nack();
		}
	}

	private static final Log _log = LogFactory.getLog(
		MarketplaceMessageReceiver.class);

	private final String _topic;

}