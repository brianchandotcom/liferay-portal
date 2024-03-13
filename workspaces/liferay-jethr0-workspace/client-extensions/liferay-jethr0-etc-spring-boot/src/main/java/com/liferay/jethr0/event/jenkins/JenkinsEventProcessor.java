/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jenkins;

import com.liferay.jethr0.event.BaseEventProcessor;
import com.liferay.jethr0.event.EventHandlerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsEventProcessor extends BaseEventProcessor {

	@JmsListener(
		destination = "${JETHR0_JMS_QUEUE_JENKINS_TO_JETHR0:jenkins-to-jethr0}"
	)
	@Override
	public void receiveMessage(String message) {
		super.receiveMessage(message);
	}

	@Override
	protected EventHandlerFactory getEventHandlerFactory() {
		return _jenkinsEventHandlerFactory;
	}

	@Override
	protected String getOutboundQueueName() {
		return _outboundQueueName;
	}

	@Autowired
	private JenkinsEventHandlerFactory _jenkinsEventHandlerFactory;

	@Value("${JETHR0_JMS_QUEUE_JETHR0_TO_JENKINS:jethr0-to-jenkins}")
	private String _outboundQueueName;

}