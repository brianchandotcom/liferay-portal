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

package com.liferay.jethr0.jms;

import com.liferay.jethr0.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JMSEventHandler {

	@JmsListener(destination = "${jms.jenkins.event.queue}")
	public void process(String message) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringUtil.combine(
					"[", _jmsJenkinsEventQueue, "] Receive ", message));
		}
	}

	public void send(String message) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringUtil.combine(
					"[", _jmsJenkinsBuildQueue, "] Send ", message));
		}

		_jmsTemplate.convertAndSend(_jmsJenkinsBuildQueue, message);
	}

	private static final Log _log = LogFactory.getLog(JMSEventHandler.class);

	@Value("${jms.jenkins.build.queue}")
	private String _jmsJenkinsBuildQueue;

	@Value("${jms.jenkins.event.queue}")
	private String _jmsJenkinsEventQueue;

	@Autowired
	private JmsTemplate _jmsTemplate;

}