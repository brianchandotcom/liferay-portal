/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRun;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
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
		_jenkinsCohortEntityRepository.initialize();
		_jenkinsNodeEntityRepository.initialize();
		_jenkinsServerEntityRepository.initialize();

		_jenkinsCohortEntityRepository.setJenkinsServerEntityRepository(
			_jenkinsServerEntityRepository);

		_jenkinsNodeEntityRepository.setJenkinsServerEntityRepository(
			_jenkinsServerEntityRepository);

		_jenkinsServerEntityRepository.setJenkinsCohortEntityRepository(
			_jenkinsCohortEntityRepository);
		_jenkinsServerEntityRepository.setJenkinsNodeEntityRepository(
			_jenkinsNodeEntityRepository);

		_jenkinsCohortEntityRepository.initializeRelationships();
		_jenkinsNodeEntityRepository.initializeRelationships();
		_jenkinsServerEntityRepository.initializeRelationships();

		invoke();

		_initialized = true;
	}

	public void invoke() {
		update();
	}

	public boolean isInitialized() {
		return _initialized;
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
		for (JenkinsServerEntity jenkinsServerEntity :
				_jenkinsServerEntityRepository.getAll()) {

			jenkinsServerEntity.update();
		}

		_buildQueue.sort();

		for (JenkinsServerEntity jenkinsServerEntity :
				_jenkinsServerEntityRepository.getAll()) {

			for (JenkinsNodeEntity jenkinsNodeEntity :
					jenkinsServerEntity.getJenkinsNodeEntities()) {

				if (!jenkinsNodeEntity.isAvailable()) {
					continue;
				}

				Build build = _buildQueue.nextBuild(jenkinsNodeEntity);

				if (build == null) {
					continue;
				}

				build.setState(Build.State.QUEUED);

				BuildRun buildRun = _buildRunEntityRepository.add(
					build, BuildRun.State.QUEUED);

				_jmsEventHandler.send(
					jenkinsServerEntity,
					String.valueOf(
						buildRun.getInvokeJSONObject(jenkinsNodeEntity)));

				_buildEntityRepository.update(build);
				_buildRunEntityRepository.update(buildRun);
			}
		}
	}

	private static final Log _log = LogFactory.getLog(JenkinsQueue.class);

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildQueue _buildQueue;

	@Autowired
	private BuildRunEntityRepository _buildRunEntityRepository;

	private boolean _initialized;

	@Autowired
	private JenkinsCohortEntityRepository _jenkinsCohortEntityRepository;

	@Autowired
	private JenkinsNodeEntityRepository _jenkinsNodeEntityRepository;

	@Autowired
	private JenkinsServerEntityRepository _jenkinsServerEntityRepository;

	@Value("${jenkins.server.urls}")
	private String _jenkinsServerURLs;

	private JMSEventHandler _jmsEventHandler;

}