/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.parameter.BuildParameterEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildParameterEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.event.controller.EventJmsController;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEventHandler implements EventHandler {

	protected BaseEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		_eventHandlerContext = eventHandlerContext;
		_messageJSONObject = messageJSONObject;
	}

	protected BuildParameterEntityRepository getBuildParameterRepository() {
		return _eventHandlerContext.getBuildParameterRepository();
	}

	protected BuildQueue getBuildQueue() {
		return _eventHandlerContext.getBuildQueue();
	}

	protected BuildEntityRepository getBuildRepository() {
		return _eventHandlerContext.getBuildRepository();
	}

	protected BuildRunEntityRepository getBuildRunRepository() {
		return _eventHandlerContext.getBuildRunRepository();
	}

	protected EventJmsController getEventJmsController() {
		return _eventHandlerContext.getEventJmsController();
	}

	protected JenkinsCohortEntityRepository getJenkinsCohortEntityRepository() {
		return _eventHandlerContext.getJenkinsCohortEntityRepository();
	}

	protected JenkinsNodeEntityRepository getJenkinsNodeEntityRepository() {
		return _eventHandlerContext.getJenkinsNodeEntityRepository();
	}

	protected JenkinsQueue getJenkinsQueue() {
		return _eventHandlerContext.getJenkinsQueue();
	}

	protected JenkinsServerEntityRepository getJenkinsServerEntityRepository() {
		return _eventHandlerContext.getJenkinsServerEntityRepository();
	}

	protected JobEntityRepository getJobEntityRepository() {
		return _eventHandlerContext.getJobEntityRepository();
	}

	protected String getLiferayPortalURL() {
		return _eventHandlerContext.getLiferayPortalURL();
	}

	protected JSONObject getMessageJSONObject() {
		return _messageJSONObject;
	}

	protected void updateJRPStatus(
		BuildRunEntity buildRunEntity, BuildEntity buildEntity,
		JobEntity jobEntity, String status) {

		EventJmsController eventJmsController = getEventJmsController();

		Map<String, String> messageProperties = HashMapBuilder.put(
			"jenkinsBuildId",
			() -> {
				if (buildEntity == null) {
					return null;
				}

				BuildParameterEntity buildParameterEntity =
					buildEntity.getBuildParameterEntity("JENKINS_BUILD_ID");

				if (buildParameterEntity == null) {
					return null;
				}

				return buildParameterEntity.getValue();
			}
		).put(
			"jethr0JobId",
			() -> {
				if (jobEntity == null) {
					return null;
				}

				return String.valueOf(jobEntity.getId());
			}
		).build();

		JSONObject jsonObject = new JSONObject();

		if (buildEntity != null) {
			jsonObject.put(
				"jethr0BuildURL",
				StringUtil.combine(
					getLiferayPortalURL(), "/#/jobs/builds/",
					buildEntity.getId()));
		}

		if (buildRunEntity != null) {
			jsonObject.put(
				"jenkinsBuildURL",
				String.valueOf(buildRunEntity.getJenkinsBuildURL()));
		}

		jsonObject.put("status", status);

		eventJmsController.sendToJRP(jsonObject.toString(), messageProperties);
	}

	private final EventHandlerContext _eventHandlerContext;
	private final JSONObject _messageJSONObject;

}