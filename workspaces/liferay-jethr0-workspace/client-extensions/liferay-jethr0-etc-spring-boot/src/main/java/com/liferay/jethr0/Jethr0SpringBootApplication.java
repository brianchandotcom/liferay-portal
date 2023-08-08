/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0;

import com.liferay.client.extension.util.spring.boot.ClientExtensionUtilSpringBootComponentScan;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.entity.repository.EntityRepository;
import com.liferay.jethr0.event.handler.EventHandlerContext;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jms.JMSEventHandler;
import com.liferay.jethr0.project.queue.ProjectQueue;

import java.util.Map;

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
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.core.JmsTemplate;

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

		Map<String, EntityRepository> entityRepositories =
			configurableApplicationContext.getBeansOfType(
				EntityRepository.class);

		for (EntityRepository entityRepository : entityRepositories.values()) {
			entityRepository.initialize();
		}

		ProjectQueue projectQueue = configurableApplicationContext.getBean(
			ProjectQueue.class);

		projectQueue.initialize();

		BuildQueue buildQueue = configurableApplicationContext.getBean(
			BuildQueue.class);

		buildQueue.initialize();

		JmsListenerEndpointRegistry jmsListenerEndpointRegistry =
			configurableApplicationContext.getBean(
				JmsListenerEndpointRegistry.class);

		jmsListenerEndpointRegistry.start();

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
		jmsTemplate.setDefaultDestinationName("default");

		return jmsTemplate;
	}

	@Value("${jms.broker.url}")
	private String _jmsBrokerURL;

	@Value("${jms.user.name}")
	private String _jmsUserName;

	@Value("${jms.user.password}")
	private String _jmsUserPassword;

}