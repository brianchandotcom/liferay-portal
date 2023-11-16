/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.BaseEventProcessor;
import com.liferay.jethr0.event.EventHandlerFactory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitHubEventProcessor extends BaseEventProcessor {

	@JmsListener(
		destination = "${JETHR0_JMS_QUEUE_GITHUB_TO_JETHR0:github-to-jethr0}"
	)
	public void receiveMessage(String message) {
		super.receiveMessage(message);
	}

	@Override
	public void sendMessage(
		String message, Map<String, String> messageProperties) {

		throw new UnsupportedOperationException(
			"Unable to send messages to GitHub");
	}

	@Override
	protected EventHandlerFactory getEventHandlerFactory() {
		return _gitHubEventHandlerFactory;
	}

	@Override
	protected String getOutboundQueueName() {
		return null;
	}

	@Autowired
	private GitHubEventHandlerFactory _gitHubEventHandlerFactory;

}