/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunRepository;
import com.liferay.jethr0.bui1d.run.BuildRun;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerRepository;
import com.liferay.jethr0.jenkins.server.JenkinsServer;
import com.liferay.jethr0.jms.JMSEventHandler;

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
		_jenkinsCohortRepository.initialize();
		_jenkinsNodeRepository.initialize();
		_jenkinsServerRepository.initialize();

		_jenkinsCohortRepository.setJenkinsServerRepository(
			_jenkinsServerRepository);

		_jenkinsNodeRepository.setJenkinsServerRepository(
			_jenkinsServerRepository);

		_jenkinsServerRepository.setJenkinsCohortRepository(
			_jenkinsCohortRepository);
		_jenkinsServerRepository.setJenkinsNodeRepository(
			_jenkinsNodeRepository);

		_jenkinsCohortRepository.initializeRelationships();
		_jenkinsNodeRepository.initializeRelationships();
		_jenkinsServerRepository.initializeRelationships();

		invoke();

		_initialized = true;
	}

	public boolean initialized() {
		return _initialized;
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
		for (JenkinsServer jenkinsServer : _jenkinsServerRepository.getAll()) {
			jenkinsServer.update();
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
					String.valueOf(buildRun.getInvokeJSONObject(jenkinsNode)));

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

	private boolean _initialized;

	@Autowired
	private JenkinsCohortRepository _jenkinsCohortRepository;

	@Autowired
	private JenkinsNodeRepository _jenkinsNodeRepository;

	@Autowired
	private JenkinsServerRepository _jenkinsServerRepository;

	@Value("${jenkins.server.urls}")
	private String _jenkinsServerURLs;

	private JMSEventHandler _jmsEventHandler;

}