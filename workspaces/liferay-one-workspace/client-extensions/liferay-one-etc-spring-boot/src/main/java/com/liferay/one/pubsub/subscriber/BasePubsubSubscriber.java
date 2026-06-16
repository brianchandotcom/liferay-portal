/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.pubsub.subscriber;

import com.google.api.gax.rpc.NotFoundException;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.DeadLetterPolicy;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.TopicName;

import com.liferay.one.pubsub.BasePubsubClient;
import com.liferay.one.pubsub.Message;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
public abstract class BasePubsubSubscriber extends BasePubsubClient {

	@PostConstruct
	public void initialize() throws Exception {
		String subscriptionName = getSubscriptionName();

		if (Validator.isNull(subscriptionName)) {
			Class<?> clazz = getClass();

			subscriptionName = clazz.getName();
		}

		subscriptionName = getNamespace() + subscriptionName;

		ProjectSubscriptionName projectSubscriptionName =
			ProjectSubscriptionName.of(getProjectId(), subscriptionName);

		if (isAutoCreateTopic()) {
			ensureTopicExists(getTopic());

			_ensureSubscriptionExists(projectSubscriptionName, getTopic());
		}

		_subscriber = Subscriber.newBuilder(
			projectSubscriptionName,
			new MessageReceiver() {

				@Override
				public void receiveMessage(
					PubsubMessage pubsubMessage,
					AckReplyConsumer ackReplyConsumer) {

					try {
						ByteString byteString = pubsubMessage.getData();

						receive(
							new Message(
								pubsubMessage.getAttributesMap(),
								byteString.toStringUtf8(), getTopic()));

						ackReplyConsumer.ack();
					}
					catch (Exception exception) {
						_log.error("Unable to process message", exception);

						ackReplyConsumer.nack();
					}
				}

			}
		).setCredentialsProvider(
			getCredentialsProvider()
		).build();

		_subscriber.startAsync();

		_subscriber.awaitRunning();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Listening for messages on " +
					_subscriber.getSubscriptionNameString());
		}
	}

	@PreDestroy
	public void stop() {
		if (_subscriber != null) {
			try {
				_subscriber.stopAsync();

				_subscriber.awaitTerminated();

				if (_log.isInfoEnabled()) {
					_log.info(
						"Stopped listening for messages on " +
							_subscriber.getSubscriptionNameString());
				}
			}
			catch (Exception exception) {
				_log.error("Unable to stop the subscriber", exception);
			}
		}
	}

	protected int getAckDeadlineSeconds() {
		return 30;
	}

	protected int getMaxDeliveryAttempts() {
		return 5;
	}

	protected String getMessageFilter() {
		return StringPool.BLANK;
	}

	protected String getSubscriptionName() {
		return StringPool.BLANK;
	}

	protected String getTopic() {
		return StringPool.BLANK;
	}

	protected abstract void receive(Message message) throws Exception;

	private void _ensureSubscriptionExists(
			ProjectSubscriptionName projectSubscriptionName, String topic)
		throws Exception {

		SubscriptionAdminSettings subscriptionAdminSettings =
			SubscriptionAdminSettings.newBuilder(
			).setCredentialsProvider(
				getCredentialsProvider()
			).build();

		try (SubscriptionAdminClient subscriptionAdminClient =
				SubscriptionAdminClient.create(subscriptionAdminSettings)) {

			try {
				subscriptionAdminClient.getSubscription(
					projectSubscriptionName);

				if (_log.isDebugEnabled()) {
					_log.debug("Found subscription " + projectSubscriptionName);
				}

				return;
			}
			catch (NotFoundException notFoundException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find subscription. Creating subscription " +
							projectSubscriptionName,
						notFoundException);
				}
			}

			TopicName topicName = TopicName.ofProjectTopicName(
				getProjectId(), getNamespace() + topic);

			Subscription.Builder builder = Subscription.newBuilder(
			).setAckDeadlineSeconds(
				getAckDeadlineSeconds()
			).setEnableMessageOrdering(
				true
			).setFilter(
				getMessageFilter()
			).setName(
				projectSubscriptionName.toString()
			).setTopic(
				topicName.toString()
			);

			if (isDeadLetterTopicEnabled()) {
				TopicName deadLetterTopicName = TopicName.ofProjectTopicName(
					getProjectId(), getNamespace() + getDeadLetterTopic(topic));

				builder.setDeadLetterPolicy(
					DeadLetterPolicy.newBuilder(
					).setDeadLetterTopic(
						deadLetterTopicName.toString()
					).setMaxDeliveryAttempts(
						getMaxDeliveryAttempts()
					).build());
			}

			subscriptionAdminClient.createSubscription(builder.build());
		}
	}

	private static final Log _log = LogFactory.getLog(
		BasePubsubSubscriber.class);

	private Subscriber _subscriber;

}