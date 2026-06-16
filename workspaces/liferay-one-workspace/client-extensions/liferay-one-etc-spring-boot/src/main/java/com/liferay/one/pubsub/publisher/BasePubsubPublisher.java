/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.pubsub.publisher;

import com.google.api.core.ApiFuture;
import com.google.api.gax.retrying.RetrySettings;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import com.liferay.one.pubsub.BasePubsubClient;
import com.liferay.one.pubsub.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
public abstract class BasePubsubPublisher extends BasePubsubClient {

	public synchronized void publish(Message message) throws Exception {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Publishing message " + message);
			}

			doPublish(message);
		}
		catch (Exception exception) {
			if ((exception instanceof ExecutionException) &&
				(exception.getCause() instanceof Exception)) {

				handleError((Exception)exception.getCause(), message);
			}
			else {
				handleError(exception, message);
			}
		}
	}

	@PreDestroy
	public void shutdown() {
		try {
			for (Publisher publisher : _publisherMap.values()) {
				publisher.shutdown();

				publisher.awaitTermination(1, TimeUnit.MINUTES);
			}
		}
		catch (Exception exception) {
			_log.error("Unable to shut down publishers", exception);
		}
	}

	protected RetrySettings buildRetrySettings() {
		return null;
	}

	protected void doPublish(Message message) throws Exception {
		Publisher publisher = _publisherMap.get(message.getTopic());

		if (publisher == null) {
			publisher = _createPublisher(message.getTopic());

			_publisherMap.put(message.getTopic(), publisher);
		}

		PubsubMessage pubsubMessage = PubsubMessage.newBuilder(
		).putAllAttributes(
			message.getAttributes()
		).setData(
			ByteString.copyFromUtf8(message.getPayload())
		).setOrderingKey(
			getOrderingKey(message)
		).build();

		ApiFuture<String> apiFuture = publisher.publish(pubsubMessage);

		apiFuture.get(getPublishTimeoutSeconds(), TimeUnit.SECONDS);

		if (_log.isDebugEnabled()) {
			_log.debug("Published message " + message);
		}
	}

	protected String getOrderingKey(Message message) {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	protected long getPublishTimeoutSeconds() {
		return 60;
	}

	protected void handleError(Exception exception, Message message)
		throws Exception {

		_log.error("Unable to publish message " + message, exception);

		throw exception;
	}

	private Publisher _createPublisher(String topic) throws Exception {
		if (isAutoCreateTopic()) {
			ensureTopicExists(topic);
		}

		Publisher.Builder builder = Publisher.newBuilder(
			TopicName.ofProjectTopicName(getProjectId(), getNamespace() + topic)
		).setCredentialsProvider(
			getCredentialsProvider()
		).setEnableMessageOrdering(
			true
		);

		RetrySettings retrySettings = buildRetrySettings();

		if (retrySettings != null) {
			builder.setRetrySettings(retrySettings);
		}

		return builder.build();
	}

	private static final Log _log = LogFactory.getLog(
		BasePubsubPublisher.class);

	private final Map<String, Publisher> _publisherMap = new HashMap<>();

}