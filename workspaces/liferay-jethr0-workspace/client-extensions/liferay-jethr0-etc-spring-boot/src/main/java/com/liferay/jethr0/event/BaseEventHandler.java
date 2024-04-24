/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.jenkins.JenkinsEventProcessor;
import com.liferay.jethr0.event.jenkins.client.JenkinsClient;
import com.liferay.jethr0.event.jrp.JRPEventProcessor;
import com.liferay.jethr0.git.repository.GitBranchEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.queue.JobQueue;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;
import com.liferay.jethr0.routine.scheduler.RoutineEntityScheduler;
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

	protected BuildQueue getBuildQueue() {
		return _eventHandlerContext.getBuildQueue();
	}

	protected BuildEntityRepository getBuildRepository() {
		return _eventHandlerContext.getBuildRepository();
	}

	protected BuildRunEntityRepository getBuildRunRepository() {
		return _eventHandlerContext.getBuildRunRepository();
	}

	protected GitBranchEntityRepository getGitBranchEntityRepository() {
		return _eventHandlerContext.getGitBranchEntityRepository();
	}

	protected GitHubClient getGitHubClient() {
		return _eventHandlerContext.getGitHubClient();
	}

	protected GitHubFactory getGitHubFactory() {
		return _eventHandlerContext.getGitHubFactory();
	}

	protected JenkinsClient getJenkinsClient() {
		return _eventHandlerContext.getJenkinsClient();
	}

	protected JenkinsCohortEntityRepository getJenkinsCohortEntityRepository() {
		return _eventHandlerContext.getJenkinsCohortEntityRepository();
	}

	protected JenkinsEventProcessor getJenkinsEventProcessor() {
		return _eventHandlerContext.getJenkinsEventProcessor();
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

	protected JobQueue getJobQueue() {
		return _eventHandlerContext.getJobQueue();
	}

	protected JRPEventProcessor getJRPEventProcessor() {
		return _eventHandlerContext.getJRPEventProcessor();
	}

	protected String getLiferayPortalURL() {
		return _eventHandlerContext.getLiferayPortalURL();
	}

	protected JSONObject getMessageJSONObject() {
		return _messageJSONObject;
	}

	protected RoutineEntityRepository getRoutineEntityRepository() {
		return _eventHandlerContext.getRoutineEntityRepository();
	}

	protected RoutineEntityScheduler getRoutineEntityScheduler() {
		return _eventHandlerContext.getRoutineEntityScheduler();
	}

	protected void updateJRPStatus(
		BuildRunEntity buildRunEntity, BuildEntity buildEntity,
		JobEntity jobEntity, String status) {

		if (buildEntity == null) {
			return;
		}

		String jenkinsBuildId = buildEntity.getBuildParameterValue(
			"JENKINS_BUILD_ID");

		if (StringUtil.isNullOrEmpty(jenkinsBuildId)) {
			return;
		}

		JRPEventProcessor jrpEventProcessor = getJRPEventProcessor();

		Map<String, String> messageProperties = HashMapBuilder.put(
			"jenkinsBuildId", jenkinsBuildId
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
				"jethr0BuildId", String.valueOf(buildEntity.getId())
			).put(
				"jethr0BuildURL",
				StringUtil.combine(
					getLiferayPortalURL(), "/#/jobs/builds/",
					buildEntity.getId())
			);
		}

		if (buildRunEntity != null) {
			jsonObject.put(
				"jenkinsBuildURL",
				String.valueOf(buildRunEntity.getJenkinsBuildURL()));

			BuildRunEntity.Result buildRunResult = buildRunEntity.getResult();

			if (buildRunResult != null) {
				jsonObject.put("result", buildRunResult.getKey());
			}
		}

		jsonObject.put("status", status);

		jrpEventProcessor.sendMessage(jsonObject.toString(), messageProperties);
	}

	private final EventHandlerContext _eventHandlerContext;
	private final JSONObject _messageJSONObject;

}