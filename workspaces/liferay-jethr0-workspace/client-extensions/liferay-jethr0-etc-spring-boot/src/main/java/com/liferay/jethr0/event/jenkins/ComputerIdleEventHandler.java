/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jenkins;

import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ComputerIdleEventHandler extends ComputerUpdateEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		JenkinsQueue jenkinsQueue = Jethr0ContextUtil.getJenkinsQueue();

		if (!jenkinsQueue.isInitialized()) {
			return "{\"message\": \"Jenkins queue is not initialized\"}";
		}

		super.process();

		JenkinsNodeEntity jenkinsNodeEntity = getJenkinsNodeEntity();

		if (jenkinsNodeEntity == null) {
			return null;
		}

		BuildQueue buildQueue = Jethr0ContextUtil.getBuildQueue();

		BuildEntity buildEntity = buildQueue.nextBuildEntity(jenkinsNodeEntity);

		if (buildEntity == null) {
			return null;
		}

		buildEntity.setState(BuildEntity.State.QUEUED);

		BuildRunEntityRepository buildRunEntityRepository =
			Jethr0ContextUtil.getBuildRunEntityRepository();

		BuildRunEntity buildRunEntity = buildRunEntityRepository.create(
			buildEntity, BuildRunEntity.State.QUEUED);

		JenkinsEventProcessor jenkinsEventProcessor =
			Jethr0ContextUtil.getJenkinsEventProcessor();

		jenkinsEventProcessor.sendMessage(
			String.valueOf(
				buildRunEntity.getInvokeJSONObject(jenkinsNodeEntity)),
			HashMapBuilder.put(
				"jenkinsMasterName",
				() -> {
					JenkinsServerEntity jenkinsServerEntity =
						jenkinsNodeEntity.getJenkinsServerEntity();

					if (jenkinsServerEntity == null) {
						return null;
					}

					return jenkinsServerEntity.getName();
				}
			).build());

		BuildEntityRepository buildEntityRepository =
			Jethr0ContextUtil.getBuildEntityRepository();

		buildEntityRepository.update(buildEntity);

		buildRunEntityRepository.update(buildRunEntity);

		return jenkinsNodeEntity.toString();
	}

	protected ComputerIdleEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

}