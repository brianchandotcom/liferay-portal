/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.google.api.core.ApiService;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.NotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.TopicName;

import com.liferay.marketplace.constants.MarketplaceConstants;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Caleb Hall
 */
@Service
public class MarketplacePubsubSubscriber {

	@PreDestroy
	public void tearDown() {
		for (Subscriber subscriber : _subscribers) {
			if (subscriber != null) {
				subscriber.stopAsync(
				).awaitTerminated();

				if (_log.isInfoEnabled()) {
					_log.info("Subscriber shut down cleanly");
				}
			}
		}

		if (_subscriptionAdminClient != null) {
			_subscriptionAdminClient.close();

			if (_log.isInfoEnabled()) {
				_log.info("Subscription admin client closed");
			}
		}
	}

	@PostConstruct
	protected void activate() throws Exception {
		CredentialsProvider credentialsProvider = getCredentialsProvider();

		if (credentialsProvider == null) {
			return;
		}

		SubscriptionAdminSettings subscriptionAdminSettings =
			SubscriptionAdminSettings.newBuilder(
			).setCredentialsProvider(
				credentialsProvider
			).build();

		try {
			_subscriptionAdminClient = SubscriptionAdminClient.create(
				subscriptionAdminSettings);
		}
		catch (Exception exception) {
			_log.error("Failed to create Admin Client", exception);

			throw exception;
		}

		for (String topic : MarketplaceConstants.PUBSUB_TOPICS) {
			try {
				String subscriptionName =
					"marketplace_" + topic + "-subscription";

				ProjectSubscriptionName projectSubscriptionName =
					ProjectSubscriptionName.of(_projectId, subscriptionName);

				try {
					_subscriptionAdminClient.getSubscription(
						projectSubscriptionName);

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Found subscription " +
								projectSubscriptionName.toString());
					}
				}
				catch (NotFoundException notFoundException) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Creating a new subscription \n",
							notFoundException);
					}

					TopicName topicName = TopicName.ofProjectTopicName(
						_projectId, topic);

					Subscription subscription = Subscription.newBuilder(
					).setAckDeadlineSeconds(
						30
					).setName(
						projectSubscriptionName.toString()
					).setTopic(
						topicName.toString()
					).build();

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Creating subscription " + subscription.toString());
					}

					_subscriptionAdminClient.createSubscription(subscription);
				}

				Subscriber subscriber = Subscriber.newBuilder(
					projectSubscriptionName,
					new MarketplaceMessageReceiver(topic)
				).setCredentialsProvider(
					credentialsProvider
				).build();

				_subscribers.add(subscriber);

				ApiService apiService = subscriber.startAsync();

				apiService.awaitRunning();

				if (_log.isInfoEnabled()) {
					_log.info(
						"Listening for messages on " +
							subscriber.getSubscriptionNameString());
				}
			}
			catch (Exception exception) {
				_log.error(
					"Failed to initialize PubSub subscription for topic: " +
						topic,
					exception);

				throw exception;
			}
		}
	}

	protected CredentialsProvider getCredentialsProvider() throws IOException {
		GoogleCredentials googleCredentials =
			ServiceAccountCredentials.fromStream(
				new ByteArrayInputStream(
					_gcpServiceAccountKey.getBytes(StandardCharsets.UTF_8))
			).createScoped(
				Collections.singletonList(_SCOPE)
			);

		return FixedCredentialsProvider.create(googleCredentials);
	}

	private static final String _SCOPE =
		"https://www.googleapis.com/auth/cloud-platform";

	private static final Log _log = LogFactory.getLog(
		MarketplacePubsubSubscriber.class);

	@Value("${liferay.marketplace.pubsub.gcp.service.account.key}")
	private String _gcpServiceAccountKey;

	@Value("${liferay.marketplace.pubsub.gcp.project.id}")
	private String _projectId;

	private final List<Subscriber> _subscribers = new ArrayList<>();
	private SubscriptionAdminClient _subscriptionAdminClient;

}