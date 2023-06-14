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

import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.repository.ProjectRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class QueueProjectEventHandler extends BaseEventHandler {

	@Override
	public String process(String body) throws Exception {
		JSONObject bodyJSONObject = new JSONObject(body);

		Project project = getProject(bodyJSONObject.optJSONObject("project"));

		project.setState(Project.State.QUEUED);

		EventHandlerContext eventHandlerContext = getEventHandlerContext();

		ProjectRepository projectRepository =
			eventHandlerContext.getProjectRepository();

		projectRepository.update(project);

		BuildQueue buildQueue = eventHandlerContext.getBuildQueue();

		buildQueue.addProject(project);

		JenkinsQueue jenkinsQueue = eventHandlerContext.getJenkinsQueue();

		jenkinsQueue.invoke();

		return project.toString();
	}

	protected QueueProjectEventHandler(
		EventHandlerContext eventHandlerContext) {

		super(eventHandlerContext);
	}

}