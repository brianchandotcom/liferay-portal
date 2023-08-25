/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRun;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jms.JMSEventHandler;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ComputerIdleEventHandler extends ComputerUpdateEventHandler {

	public ComputerIdleEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	@Override
	public String process() throws Exception {
		JenkinsQueue jenkinsQueue = getJenkinsQueue();

		if (!jenkinsQueue.isInitialized()) {
			return "{\"message\": \"Jenkins queue is not initialized\"}";
		}

		super.process();

		JenkinsNodeEntity jenkinsNodeEntity = getJenkinsNodeEntity();

		if (jenkinsNodeEntity == null) {
			return null;
		}

		BuildQueue buildQueue = getBuildQueue();

		Build build = buildQueue.nextBuild(jenkinsNodeEntity);

		if (build == null) {
			return null;
		}

		build.setState(Build.State.QUEUED);

		BuildRunEntityRepository buildRunEntityRepository =
			getBuildRunRepository();

		BuildRun buildRun = buildRunEntityRepository.add(
			build, BuildRun.State.QUEUED);

		JMSEventHandler jmsEventHandler = getJMSEventHandler();

		jmsEventHandler.send(
			jenkinsNodeEntity.getJenkinsServerEntity(),
			String.valueOf(buildRun.getInvokeJSONObject(jenkinsNodeEntity)));

		BuildEntityRepository buildEntityRepository = getBuildRepository();

		buildEntityRepository.update(build);

		buildRunEntityRepository.update(buildRun);

		return jenkinsNodeEntity.toString();
	}

}