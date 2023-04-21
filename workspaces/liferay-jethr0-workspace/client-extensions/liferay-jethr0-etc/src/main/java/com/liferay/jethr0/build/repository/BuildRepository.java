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

package com.liferay.jethr0.build.repository;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.dalo.BuildDALO;
import com.liferay.jethr0.build.dalo.BuildToBuildParametersDALO;
import com.liferay.jethr0.build.dalo.BuildToBuildRunsDALO;
import com.liferay.jethr0.build.dalo.BuildToEnvironmentsDALO;
import com.liferay.jethr0.build.dalo.BuildToTasksDALO;
import com.liferay.jethr0.build.parameter.BuildParameter;
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.dalo.ProjectToBuildsDALO;
import com.liferay.jethr0.task.Task;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildRepository extends BaseEntityRepository<Build> {

	public Set<Build> getAll(Project project) {
		Set<Build> projectBuilds = new HashSet<>();

		Set<Long> buildIds = _projectToBuildsDALO.getChildEntityIds(project);

		for (Build build : getAll()) {
			if (!buildIds.contains(build.getId())) {
				continue;
			}

			build.setProject(project);

			project.addBuild(build);

			projectBuilds.add(build);
		}

		return projectBuilds;
	}

	@Override
	public EntityDALO<Build> getEntityDALO() {
		return _buildDALO;
	}

	@Override
	protected Build updateEntityRelationshipsFromDatabase(Build build) {
		build.addBuildParameters(
			_buildToBuildParametersDALO.getChildEntities(build));
		build.addBuildRuns(_buildToBuildRunsDALO.getChildEntities(build));
		build.addEnvironments(_buildToEnvironmentsDALO.getChildEntities(build));
		build.addTasks(_buildToTasksDALO.getChildEntities(build));

		return build;
	}

	@Override
	protected Build updateEntityRelationshipsInDatabase(Build build) {
		for (BuildParameter buildParameter :
				_buildToBuildParametersDALO.getChildEntities(build)) {

			buildParameter.setBuild(build);

			build.addBuildParameter(buildParameter);
		}

		for (BuildRun buildRun :
				_buildToBuildRunsDALO.getChildEntities(build)) {

			buildRun.setBuild(build);

			build.addBuildRun(buildRun);
		}

		for (Environment environment :
				_buildToEnvironmentsDALO.getChildEntities(build)) {

			environment.setBuild(build);

			build.addEnvironment(environment);
		}

		for (Task task : _buildToTasksDALO.getChildEntities(build)) {
			task.setBuild(build);

			build.addTask(task);
		}

		return build;
	}

	@Autowired
	private BuildDALO _buildDALO;

	@Autowired
	private BuildToBuildParametersDALO _buildToBuildParametersDALO;

	@Autowired
	private BuildToBuildRunsDALO _buildToBuildRunsDALO;

	@Autowired
	private BuildToEnvironmentsDALO _buildToEnvironmentsDALO;

	@Autowired
	private BuildToTasksDALO _buildToTasksDALO;

	@Autowired
	private ProjectToBuildsDALO _projectToBuildsDALO;

}