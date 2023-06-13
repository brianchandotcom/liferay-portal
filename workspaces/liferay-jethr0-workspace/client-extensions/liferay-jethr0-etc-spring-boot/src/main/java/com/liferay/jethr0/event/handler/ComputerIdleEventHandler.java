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

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.build.repository.BuildRunRepository;
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.jms.JMSEventHandler;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ComputerIdleEventHandler extends ComputerUpdateEventHandler {

	public ComputerIdleEventHandler(
		EventHandlerHelper eventHandlerHelper, JSONObject messageJSONObject) {

		super(eventHandlerHelper, messageJSONObject);
	}

	@Override
	public String process() throws Exception {
		super.process();

		JenkinsNode jenkinsNode = getJenkinsNode();

		if (jenkinsNode == null) {
			return null;
		}

		BuildQueue buildQueue = getBuildQueue();

		Build build = buildQueue.nextBuild(jenkinsNode);

		if (build == null) {
			return null;
		}

		build.setState(Build.State.QUEUED);

		BuildRunRepository buildRunRepository = getBuildRunRepository();

		BuildRun buildRun = buildRunRepository.add(
			build, BuildRun.State.QUEUED);

		JMSEventHandler jmsEventHandler = getJMSEventHandler();

		jmsEventHandler.send(String.valueOf(buildRun.getInvokeJSONObject()));

		BuildRepository buildRepository = getBuildRepository();

		buildRepository.update(build);

		buildRunRepository.update(buildRun);

		return jenkinsNode.toString();
	}

}