/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.event.controller.EventJmsController;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;

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
		update();

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

	public void setEventJmsController(EventJmsController eventJmsController) {
		_eventJmsController = eventJmsController;
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

				BuildEntity buildEntity = _buildQueue.nextBuildEntity(
					jenkinsNodeEntity);

				if (buildEntity == null) {
					continue;
				}

				buildEntity.setState(BuildEntity.State.QUEUED);

				BuildRunEntity buildRunEntity =
					_buildRunEntityRepository.create(
						buildEntity, BuildRunEntity.State.QUEUED);

				_eventJmsController.send(
					jenkinsServerEntity,
					String.valueOf(
						buildRunEntity.getInvokeJSONObject(jenkinsNodeEntity)));

				_buildEntityRepository.update(buildEntity);
				_buildRunEntityRepository.update(buildRunEntity);
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

	private EventJmsController _eventJmsController;
	private boolean _initialized;

	@Autowired
	private JenkinsServerEntityRepository _jenkinsServerEntityRepository;

	@Value("${jenkins.server.urls}")
	private String _jenkinsServerURLs;

}