/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.pubsub;

import com.google.api.core.ApiService;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.NotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;

import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.Channel;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;

import java.io.ByteArrayInputStream;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Caleb Hall
 */
@Service
public class MarketplaceTopicSubscriber {

	@PreDestroy
	public void tearDown() {
		for (Subscriber subscriber : _subscribers) {
			if (subscriber != null) {
				ApiService apiService = subscriber.stopAsync();

				apiService.awaitTerminated();
			}
		}

		if (_subscriptionAdminClient != null) {
			_subscriptionAdminClient.close();
		}
	}

	@PostConstruct
	protected void activate() throws Exception {
		GoogleCredentials googleCredentials;

		try {
			googleCredentials = ServiceAccountCredentials.fromStream(
				new ByteArrayInputStream(
					_serviceAccountKey.getBytes(StandardCharsets.UTF_8))
			).createScoped(
				Collections.singletonList(
					"https://www.googleapis.com/auth/cloud-platform")
			);
		}
		catch (Exception exception) {
			_log.error("Unable to get Google Credentials", exception);

			return;
		}

		CredentialsProvider credentialsProvider =
			FixedCredentialsProvider.create(googleCredentials);

		Channel channel = _marketplaceService.getChannelByExternalReferenceCode(
			"MARKETPLACE-CHANNEL");

		_subscriptionAdminClient = SubscriptionAdminClient.create(
			SubscriptionAdminSettings.newBuilder(
			).setCredentialsProvider(
				credentialsProvider
			).build());

		_subscribe(
			channel, credentialsProvider,
			MarketplaceConstants.PUBSUB_TOPIC_NAME_KORONEIKI_ACCOUNT_CREATE);
		_subscribe(
			channel, credentialsProvider,
			MarketplaceConstants.PUBSUB_TOPIC_NAME_KORONEIKI_ACCOUNT_UPDATE);
		_subscribe(
			channel, credentialsProvider,
			MarketplaceConstants.
				PUBSUB_TOPIC_NAME_KORONEIKI_PRODUCTPURCHASE_CREATE);
	}

	private void _subscribe(
		Channel channel, CredentialsProvider credentialsProvider,
		String topicName) {

		String subscriptionName = SubscriptionName.of(
			_projectId, _topicPrefix + topicName + "-subscription"
		).toString();

		try {
			_subscriptionAdminClient.getSubscription(subscriptionName);
		}
		catch (NotFoundException notFoundException) {
			_log.error(notFoundException);

			_subscriptionAdminClient.createSubscription(
				Subscription.newBuilder(
				).setAckDeadlineSeconds(
					30
				).setName(
					subscriptionName
				).setTopic(
					String.valueOf(
						TopicName.ofProjectTopicName(_projectId, topicName))
				).build());
		}

		Subscriber subscriber = Subscriber.newBuilder(
			subscriptionName,
			new MarketplaceMessageReceiver(
				channel, _koroneikiService, _marketplaceService,
				_productKeysList, topicName)
		).setCredentialsProvider(
			credentialsProvider
		).build();

		_subscribers.add(subscriber);

		ApiService apiService = subscriber.startAsync();

		apiService.awaitRunning();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Subscribed to " + subscriber.getSubscriptionNameString());
		}
	}

	private static final Log _log = LogFactory.getLog(
		MarketplaceTopicSubscriber.class);

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private MarketplaceService _marketplaceService;

	@Value("${liferay.marketplace.koroneiki.product.keys.list}")
	private List<String> _productKeysList;

	@Value("${liferay.marketplace.pubsub.gcp.project.id}")
	private String _projectId;

	@Value("${liferay.marketplace.pubsub.gcp.service.account.key}")
	private String _serviceAccountKey;

	private final List<Subscriber> _subscribers = new ArrayList<>();
	private SubscriptionAdminClient _subscriptionAdminClient;

	@Value("${liferay.marketplace.pubsub.topic.prefix}")
	private String _topicPrefix;

}