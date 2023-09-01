/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.job.ProjectEntity;
import com.liferay.jethr0.job.repository.ProjectEntityRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BuildCompletedEventHandler extends BaseJenkinsEventHandler {

	@Override
	public String process() throws Exception {
		BuildRunEntity buildRunEntity = getBuildRun();

		buildRunEntity.setDuration(getBuildDuration());
		buildRunEntity.setResult(getBuildRunResult());
		buildRunEntity.setState(BuildRunEntity.State.COMPLETED);

		BuildEntity buildEntity = buildRunEntity.getBuildEntity();

		buildEntity.setState(BuildEntity.State.COMPLETED);

		ProjectEntity projectEntity = buildEntity.getProjectEntity();

		ProjectEntity.State projectState = ProjectEntity.State.COMPLETED;

		for (BuildEntity projectBuildEntity :
				projectEntity.getBuildEntities()) {

			BuildEntity.State buildState = projectBuildEntity.getState();

			if (buildState != BuildEntity.State.COMPLETED) {
				projectState = ProjectEntity.State.RUNNING;

				break;
			}
		}

		if (projectState == ProjectEntity.State.COMPLETED) {
			projectEntity.setState(projectState);

			ProjectEntityRepository projectEntityRepository =
				getProjectEntityRepository();

			projectEntityRepository.update(projectEntity);
		}

		BuildEntityRepository buildEntityRepository = getBuildRepository();

		buildEntityRepository.update(buildEntity);

		BuildRunEntityRepository buildRunEntityRepository =
			getBuildRunRepository();

		buildRunEntityRepository.update(buildRunEntity);

		return buildRunEntity.toString();
	}

	protected BuildCompletedEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

}