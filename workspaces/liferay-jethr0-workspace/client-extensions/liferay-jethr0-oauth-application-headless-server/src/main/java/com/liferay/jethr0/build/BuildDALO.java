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

package com.liferay.jethr0.build;

import com.liferay.jethr0.build.parameter.BuildParameter;
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.dalo.BuildToBuildParametersDALO;
import com.liferay.jethr0.dalo.BuildToBuildRunsDALO;
import com.liferay.jethr0.dalo.BuildToTasksDALO;
import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.ProjectRepository;
import com.liferay.jethr0.task.Task;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildDALO extends BaseEntityDALO<Build> {

	@Override
	public Build create(Build build) {
		build = super.create(build);

		_buildToBuildParametersDALO.updateRelationships(build);
		_buildToBuildRunsDALO.updateRelationships(build);
		_buildToTasksDALO.updateRelationships(build);

		return build;
	}

	@Override
	public void delete(Build build) {
		for (BuildParameter buildParameter : build.getBuildParameters()) {
			_buildToBuildParametersDALO.deleteRelationship(
				build, buildParameter);
		}

		for (BuildRun buildRun : build.getBuildRuns()) {
			_buildToBuildRunsDALO.deleteRelationship(build, buildRun);
		}

		for (Task task : build.getTasks()) {
			_buildToTasksDALO.deleteRelationship(build, task);
		}

		super.delete(build);
	}

	@Override
	public List<Build> getAll() {
		List<Build> builds = new ArrayList<>();

		for (Build build : super.getAll()) {
			build.addBuildParameters(
				_buildToBuildParametersDALO.retrieveBuildParameters(build));
			build.addBuildRuns(_buildToBuildRunsDALO.retrieveBuildRuns(build));
			build.addTasks(_buildToTasksDALO.retrieveTasks(build));

			builds.add(build);
		}

		return builds;
	}

	@Override
	public Build update(Build build) {
		build = super.update(build);

		_buildToBuildParametersDALO.updateRelationships(build);
		_buildToBuildRunsDALO.updateRelationships(build);
		_buildToTasksDALO.updateRelationships(build);

		return build;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Build";
	}

	@Override
	protected Build newEntity(JSONObject jsonObject) {
		Project project = _projectRepository.getById(
			jsonObject.getLong("r_projectToBuilds_c_projectId"));

		Build build = _buildFactory.newBuild(jsonObject);

		build.setProject(project);

		return build;
	}

	@Autowired
	private BuildFactory _buildFactory;

	@Autowired
	private BuildToBuildParametersDALO _buildToBuildParametersDALO;

	@Autowired
	private BuildToBuildRunsDALO _buildToBuildRunsDALO;

	@Autowired
	private BuildToTasksDALO _buildToTasksDALO;

	@Autowired
	private ProjectRepository _projectRepository;

}