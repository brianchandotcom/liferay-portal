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

package com.liferay.jethr0.jenkins;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.build.repository.BuildRunRepository;
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerRepository;
import com.liferay.jethr0.jenkins.server.JenkinsServer;
import com.liferay.jethr0.jms.JMSEventHandler;
import com.liferay.jethr0.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Michael Hashimoto
 */
@Configuration
@EnableScheduling
public class JenkinsQueue {

	public void initialize() {
		if ((_jenkinsServerURLs != null) && !_jenkinsServerURLs.isEmpty()) {
			for (String jenkinsServerURL : _jenkinsServerURLs.split(",")) {
				JenkinsServer jenkinsServer = _jenkinsServerRepository.getByURL(
					StringUtil.toURL(jenkinsServerURL));

				if (jenkinsServer != null) {
					continue;
				}

				jenkinsServer = _jenkinsServerRepository.add(jenkinsServerURL);

				_jenkinsNodeRepository.addAll(jenkinsServer);
			}
		}

		for (JenkinsServer jenkinsServer : _jenkinsServerRepository.getAll()) {
			for (JenkinsNode jenkinsNode :
					_jenkinsNodeRepository.getAll(jenkinsServer)) {

				jenkinsServer.addJenkinsNode(jenkinsNode);

				jenkinsNode.setJenkinsServer(jenkinsServer);
			}

			jenkinsServer.update();
		}

		invoke();
	}

	public void invoke() {
		update();
	}

	public void setJmsEventHandler(JMSEventHandler jmsEventHandler) {
		_jmsEventHandler = jmsEventHandler;
	}

	@Scheduled(cron = "${liferay.jethr0.jenkins.queue.update.cron}")
	public void update() {
		if (_log.isInfoEnabled()) {
			_log.info("Updating jenkins queue");
		}

		_buildQueue.sort();

		for (JenkinsServer jenkinsServer : _jenkinsServerRepository.getAll()) {
			for (JenkinsNode jenkinsNode : jenkinsServer.getJenkinsNodes()) {
				if (!jenkinsNode.isAvailable()) {
					continue;
				}

				Build build = _buildQueue.nextBuild(jenkinsNode);

				if (build == null) {
					continue;
				}

				build.setState(Build.State.QUEUED);

				BuildRun buildRun = _buildRunRepository.add(
					build, BuildRun.State.QUEUED);

				_jmsEventHandler.send(
					jenkinsServer,
					String.valueOf(buildRun.getInvokeJSONObject()));

				_buildRepository.update(build);
				_buildRunRepository.update(buildRun);
			}
		}
	}

	private static final Log _log = LogFactory.getLog(JenkinsQueue.class);

	@Autowired
	private BuildQueue _buildQueue;

	@Autowired
	private BuildRepository _buildRepository;

	@Autowired
	private BuildRunRepository _buildRunRepository;

	@Autowired
	private JenkinsNodeRepository _jenkinsNodeRepository;

	@Autowired
	private JenkinsServerRepository _jenkinsServerRepository;

	@Value("${jenkins.server.urls}")
	private String _jenkinsServerURLs;

	private JMSEventHandler _jmsEventHandler;

}