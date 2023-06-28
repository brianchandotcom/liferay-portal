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
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.build.repository.BuildRunRepository;
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.repository.ProjectRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BuildStartedEventHandler extends BaseJenkinsEventHandler {

	@Override
	public String process() throws Exception {
		BuildRun buildRun = getBuildRun();

		buildRun.setBuildURL(getBuildURL());
		buildRun.setState(BuildRun.State.RUNNING);

		Build build = buildRun.getBuild();

		build.setState(Build.State.RUNNING);

		Project project = build.getProject();

		if (project.getState() != Project.State.RUNNING) {
			project.setState(Project.State.RUNNING);

			ProjectRepository projectRepository = getProjectRepository();

			projectRepository.update(project);
		}

		BuildRepository buildRepository = getBuildRepository();

		buildRepository.update(build);

		BuildRunRepository buildRunRepository = getBuildRunRepository();

		buildRunRepository.update(buildRun);

		return buildRun.toString();
	}

	protected BuildStartedEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject jsonObject) {

		super(eventHandlerContext, jsonObject);
	}

}