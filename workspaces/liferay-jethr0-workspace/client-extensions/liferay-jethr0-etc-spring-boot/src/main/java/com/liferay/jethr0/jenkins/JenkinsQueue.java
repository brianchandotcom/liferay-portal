/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import java.net.URL;

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
				URL url = StringUtil.toURL(jenkinsServerURL);

				JenkinsServer jenkinsServer = _jenkinsServerRepository.getByURL(
					url);

				if (jenkinsServer != null) {
					continue;
				}

				jenkinsServer = _jenkinsServerRepository.add(url);

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

	@Scheduled(cron = "${liferay.jethr0.jenkins.queue.update.cron}")
	public void scheduledUpdate() {
		if (_log.isInfoEnabled()) {
			_log.info("Updating Jenkins queue");
		}

		update();
	}

	public void setJmsEventHandler(JMSEventHandler jmsEventHandler) {
		_jmsEventHandler = jmsEventHandler;
	}

	public void update() {
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