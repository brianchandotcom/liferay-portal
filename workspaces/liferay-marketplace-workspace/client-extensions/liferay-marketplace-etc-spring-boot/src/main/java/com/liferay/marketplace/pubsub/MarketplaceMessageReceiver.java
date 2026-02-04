/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.pubsub;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;

import com.liferay.marketplace.constants.MarketplaceConstants;

import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Caleb Hall
 */
public class MarketplaceMessageReceiver implements MessageReceiver {

	public MarketplaceMessageReceiver(String topicName) {
		_topicName = topicName;
	}

	@Override
	public void receiveMessage(
		PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {

		try {
			if (Objects.equals(
					_topicName,
					MarketplaceConstants.
						PUBSUB_TOPIC_NAME_KORONEIKI_ACCOUNT_CREATE)) {

				// TODO

			}
			else if (Objects.equals(
						_topicName,
						MarketplaceConstants.
							PUBSUB_TOPIC_NAME_KORONEIKI_ACCOUNT_UPDATE)) {

				// TODO

			}
			else if (Objects.equals(
						_topicName,
						MarketplaceConstants.
							PUBSUB_TOPIC_NAME_KORONEIKI_ENTITLEMENT_CREATE)) {

				// TODO

			}

			ackReplyConsumer.ack();
		}
		catch (Exception exception) {
			_log.error(exception);

			ackReplyConsumer.nack();
		}
	}

	private static final Log _log = LogFactory.getLog(
		MarketplaceMessageReceiver.class);

	private final String _topicName;

}