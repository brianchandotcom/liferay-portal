/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunRepository;
import com.liferay.jethr0.bui1d.run.BuildRun;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.repository.ProjectRepository;

import java.util.Date;

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
			project.setStartDate(new Date());
			project.setState(Project.State.RUNNING);

			ProjectRepository projectRepository = getProjectRepository();

			projectRepository.update(project);

			BuildQueue buildQueue = getBuildQueue();

			buildQueue.sort();
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