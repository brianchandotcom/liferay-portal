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

package com.liferay.jethr0.dalo;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.BuildFactory;
import com.liferay.jethr0.project.Project;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildDALO extends BaseDALO {

	public Build createBuild(
		Project project, String buildName, String jobName, Build.State state) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"buildName", buildName
		).put(
			"jobName", jobName
		).put(
			"r_projectToBuilds_c_projectId", project.getId()
		).put(
			"state", state.getJSONObject()
		);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return BuildFactory.newBuild(project, responseJSONObject);
	}

	public void deleteBuild(Build build) {
		if (build == null) {
			return;
		}

		delete(build.getId());

		BuildFactory.removeBuild(build);
	}

	public List<Build> retrieveBuilds(Project project) {
		List<Build> builds = new ArrayList<>();

		for (JSONObject jsonObject : retrieve()) {
			Build build = BuildFactory.newBuild(project, jsonObject);

			build.addBuildParameters(
				_buildToBuildParametersDALO.retrieveBuildParameters(build));
			build.addBuildRuns(_buildToBuildRunsDALO.retrieveBuildRuns(build));
			build.addTasks(_buildToTasksDALO.retrieveTasks(build));

			builds.add(build);
		}

		return builds;
	}

	public Build updateBuild(Build build) {
		_buildToBuildParametersDALO.updateRelationships(build);
		_buildToBuildRunsDALO.updateRelationships(build);
		_buildToTasksDALO.updateRelationships(build);

		JSONObject responseJSONObject = update(build.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return build;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Build";
	}

	@Autowired
	private BuildToBuildParametersDALO _buildToBuildParametersDALO;

	@Autowired
	private BuildToBuildRunsDALO _buildToBuildRunsDALO;

	@Autowired
	private BuildToTasksDALO _buildToTasksDALO;

}