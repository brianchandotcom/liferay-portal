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

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.build.repository.BuildRunRepository;
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.event.handler.EventHandler;
import com.liferay.jethr0.event.handler.EventHandlerFactory;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeRepository;
import com.liferay.jethr0.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

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
			_log.debug("Received " + message);
		}

		JSONObject messageJSONObject = new JSONObject(message);

		String eventTrigger = messageJSONObject.getString("eventTrigger");

		if (eventTrigger.equals("BUILD_COMPLETED") ||
			eventTrigger.equals("BUILD_STARTED") ||
			eventTrigger.equals("CREATE_BUILD") ||
			eventTrigger.equals("CREATE_PROJECT") ||
			eventTrigger.equals("QUEUE_PROJECT")) {

			EventHandler eventHandler = _eventHandlerFactory.newEventHandler(
				messageJSONObject);

			if (eventHandler == null) {
				throw new RuntimeException();
			}

			try {
				eventHandler.process();
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}

				throw new RuntimeException();
			}
		}
		else if (eventTrigger.equals("COMPUTER_BUSY") ||
				 eventTrigger.equals("COMPUTER_IDLE") ||
				 eventTrigger.equals("COMPUTER_OFFLINE") ||
				 eventTrigger.equals("COMPUTER_ONLINE") ||
				 eventTrigger.equals("COMPUTER_TEMPORARILY_OFFLINE") ||
				 eventTrigger.equals("COMPUTER_TEMPORARILY_ONLINE")) {

			_updateJenkinsNode(messageJSONObject);

			if (eventTrigger.equals("COMPUTER_IDLE")) {
				_eventTriggerComputerIdle(messageJSONObject);
			}
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

	private void _eventTriggerComputerIdle(JSONObject messageJSONObject) {
		JenkinsNode jenkinsNode = _getJenkinsNode(messageJSONObject);

		if (jenkinsNode == null) {
			return;
		}

		Build build = _buildQueue.nextBuild(jenkinsNode);

		if (build == null) {
			return;
		}

		build.setState(Build.State.QUEUED);

		BuildRun buildRun = _buildRunRepository.add(
			build, BuildRun.State.QUEUED);

		send(String.valueOf(buildRun.getInvokeJSONObject()));

		_buildRepository.update(build);
		_buildRunRepository.update(buildRun);
	}

	private JenkinsNode _getJenkinsNode(JSONObject messageJSONObject) {
		JSONObject computerJSONObject = messageJSONObject.getJSONObject(
			"computer");

		return _jenkinsNodeRepository.get(computerJSONObject.getString("name"));
	}

	private JenkinsNode _updateJenkinsNode(JSONObject messageJSONObject) {
		JenkinsNode jenkinsNode = _getJenkinsNode(messageJSONObject);

		JSONObject computerJSONObject = messageJSONObject.getJSONObject(
			"computer");

		computerJSONObject.put(
			"idle", !computerJSONObject.getBoolean("busy")
		).put(
			"offline", !computerJSONObject.getBoolean("online")
		);

		jenkinsNode.update(computerJSONObject);

		return jenkinsNode;
	}

	private static final Log _log = LogFactory.getLog(JMSEventHandler.class);

	@Autowired
	private BuildQueue _buildQueue;

	@Autowired
	private BuildRepository _buildRepository;

	@Autowired
	private BuildRunRepository _buildRunRepository;

	@Autowired
	private EventHandlerFactory _eventHandlerFactory;

	@Autowired
	private JenkinsNodeRepository _jenkinsNodeRepository;

	@Value("${jms.jenkins.build.queue}")
	private String _jmsJenkinsBuildQueue;

	@Value("${jms.jenkins.event.queue}")
	private String _jmsJenkinsEventQueue;

	@Autowired
	private JmsTemplate _jmsTemplate;

}