/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jethr0;

import com.liferay.client.extension.util.spring.boot.ClientExtensionUtilSpringBootComponentScan;
import com.liferay.client.extension.util.spring.boot.LiferayOAuth2Util;
import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.entity.repository.EntityRepository;
import com.liferay.jethr0.event.handler.EventHandlerContext;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jms.JMSEventHandler;
import com.liferay.jethr0.project.queue.ProjectQueue;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

/**
 * @author Michael Hashimoto
 */
@Import(ClientExtensionUtilSpringBootComponentScan.class)
@SpringBootApplication
public class Jethr0SpringBootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext =
			SpringApplication.run(Jethr0SpringBootApplication.class, args);

		EventHandlerContext eventHandlerContext =
			configurableApplicationContext.getBean(EventHandlerContext.class);

		eventHandlerContext.setJMSEventHandler(
			configurableApplicationContext.getBean(JMSEventHandler.class));

		for (String beanDefinitionName :
				configurableApplicationContext.getBeanDefinitionNames()) {

			Object bean = configurableApplicationContext.getBean(
				beanDefinitionName);

			if (bean instanceof EntityRepository) {
				EntityRepository entityRepository = (EntityRepository)bean;

				entityRepository.initialize();
			}
		}

		ProjectQueue projectQueue = configurableApplicationContext.getBean(
			ProjectQueue.class);

		projectQueue.initialize();

		BuildQueue buildQueue = configurableApplicationContext.getBean(
			BuildQueue.class);

		buildQueue.initialize();

		JenkinsQueue jenkinsQueue = configurableApplicationContext.getBean(
			JenkinsQueue.class);

		jenkinsQueue.setJmsEventHandler(
			configurableApplicationContext.getBean(JMSEventHandler.class));

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
		jmsTemplate.setDefaultDestinationName(_jmsJenkinsBuildQueue);

		return jmsTemplate;
	}

	@Bean
	public OAuth2AccessToken getOAuth2AccessToken(
		AuthorizedClientServiceOAuth2AuthorizedClientManager
			authorizedClientServiceOAuth2AuthorizedClientManager) {

		return LiferayOAuth2Util.getOAuth2AccessToken(
			authorizedClientServiceOAuth2AuthorizedClientManager,
			_liferayOAuthApplicationExternalReferenceCodes);
	}

	@Value("${jms.broker.url}")
	private String _jmsBrokerURL;

	@Value("${jms.jenkins.build.queue}")
	private String _jmsJenkinsBuildQueue;

	@Value("${jms.user.name}")
	private String _jmsUserName;

	@Value("${jms.user.password}")
	private String _jmsUserPassword;

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;

}