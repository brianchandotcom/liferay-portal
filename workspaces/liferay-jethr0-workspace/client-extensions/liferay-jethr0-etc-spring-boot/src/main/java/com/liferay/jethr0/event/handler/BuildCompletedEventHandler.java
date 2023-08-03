/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.repository.BuildRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunRepository;
import com.liferay.jethr0.bui1d.run.BuildRun;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.repository.ProjectRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BuildCompletedEventHandler extends BaseJenkinsEventHandler {

	@Override
	public String process() throws Exception {
		BuildRun buildRun = getBuildRun();

		buildRun.setDuration(getBuildDuration());
		buildRun.setResult(getBuildRunResult());
		buildRun.setState(BuildRun.State.COMPLETED);

		Build build = buildRun.getBuild();

		build.setState(Build.State.COMPLETED);

		Project project = build.getProject();

		Project.State projectState = Project.State.COMPLETED;

		for (Build projectBuild : project.getBuilds()) {
			Build.State buildState = projectBuild.getState();

			if (buildState != Build.State.COMPLETED) {
				projectState = Project.State.RUNNING;

				break;
			}
		}

		if (projectState == Project.State.COMPLETED) {
			project.setState(projectState);

			ProjectRepository projectRepository = getProjectRepository();

			projectRepository.update(project);
		}

		BuildRepository buildRepository = getBuildRepository();

		buildRepository.update(build);

		BuildRunRepository buildRunRepository = getBuildRunRepository();

		buildRunRepository.update(buildRun);

		return buildRun.toString();
	}

	protected BuildCompletedEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

}