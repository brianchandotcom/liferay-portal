/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0;

import com.liferay.client.extension.util.spring.boot.ClientExtensionUtilSpringBootComponentScan;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.entity.EntityInitializer;
import com.liferay.jethr0.event.jenkins.JenkinsEventProcessor;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.queue.JobQueue;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * @author Michael Hashimoto
 */
@Import(ClientExtensionUtilSpringBootComponentScan.class)
@SpringBootApplication
public class Jethr0SpringBootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext =
			SpringApplication.run(Jethr0SpringBootApplication.class, args);

		EntityInitializer entityInitializer =
			configurableApplicationContext.getBean(EntityInitializer.class);

		entityInitializer.initialize();

		JobQueue jobQueue = configurableApplicationContext.getBean(
			JobQueue.class);

		jobQueue.initialize();

		BuildQueue buildQueue = configurableApplicationContext.getBean(
			BuildQueue.class);

		buildQueue.initialize();

		JmsListenerEndpointRegistry jmsListenerEndpointRegistry =
			configurableApplicationContext.getBean(
				JmsListenerEndpointRegistry.class);

		jmsListenerEndpointRegistry.start();

		JenkinsQueue jenkinsQueue = configurableApplicationContext.getBean(
			JenkinsQueue.class);

		jenkinsQueue.setJenkinsEventProcessor(
			configurableApplicationContext.getBean(
				JenkinsEventProcessor.class));

		jenkinsQueue.initialize();
	}

	@Bean
	public ActiveMQConnectionFactory getActiveMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory =
			new ActiveMQConnectionFactory();

		activeMQConnectionFactory.setBrokerURL(_jmsBrokerURL);
		activeMQConnectionFactory.setPassword(_jmsUserPassword);
		activeMQConnectionFactory.setUserName(_jmsUserName);

		return activeMQConnectionFactory;
	}

	@Bean
	@Qualifier("extra")
	public ClientRegistration getExtraClientRegistration() {
		return ClientRegistration.withRegistrationId(
			"extra"
		).authorizationGrantType(
			AuthorizationGrantType.CLIENT_CREDENTIALS
		).clientId(
			_extraOAuth2ClientId
		).clientSecret(
			_extraOAuth2ClientSecret
		).scope(
			_extraOAuth2Scope
		).tokenUri(
			_extraOAuth2TokenURL
		).build();
	}

	@Bean
	@Qualifier("extra")
	public InMemoryClientRegistrationRepository
		getInMemoryClientRegistrationRepository(
			@Qualifier("extra") ClientRegistration clientRegistration) {

		return new InMemoryClientRegistrationRepository(clientRegistration);
	}

	@Bean
	@Qualifier("extra")
	public InMemoryReactiveClientRegistrationRepository
		getInMemoryReactiveClientRegistrationRepository(
			@Qualifier("extra") ClientRegistration clientRegistration) {

		return new InMemoryReactiveClientRegistrationRepository(
			clientRegistration);
	}

	@Bean
	public JmsListenerContainerFactory getJmsListenerContainerFactory(
		ActiveMQConnectionFactory activeMQConnectionFactory) {

		DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory =
			new DefaultJmsListenerContainerFactory();

		defaultJmsListenerContainerFactory.setConnectionFactory(
			activeMQConnectionFactory);

		return defaultJmsListenerContainerFactory;
	}

	@Bean
	public JmsTemplate getJmsTemplate(ConnectionFactory connectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate();

		jmsTemplate.setConnectionFactory(connectionFactory);
		jmsTemplate.setDefaultDestinationName("default");

		return jmsTemplate;
	}

	@Value("${JETHR0_EXTRA_OAUTH2_CLIENT_ID:client_id}")
	private String _extraOAuth2ClientId;

	@Value("${JETHR0_EXTRA_OAUTH2_CLIENT_SECRET:client_secret}")
	private String _extraOAuth2ClientSecret;

	@Value("${JETHR0_EXTRA_OAUTH2_SCOPE:api}")
	private String _extraOAuth2Scope;

	@Value(
		"${JETHR0_EXTRA_OAUTH2_TOKEN_URL:https://company.okta.com/oauth2/default/v1/token}"
	)
	private String _extraOAuth2TokenURL;

	@Value("${JETHR0_JMS_BROKER_URL:tcp://localhost:61616}")
	private String _jmsBrokerURL;

	@Value("${JETHR0_JMS_USER_NAME:admin}")
	private String _jmsUserName;

	@Value("${JETHR0_JMS_USER_PASSWORD:admin}")
	private String _jmsUserPassword;

}