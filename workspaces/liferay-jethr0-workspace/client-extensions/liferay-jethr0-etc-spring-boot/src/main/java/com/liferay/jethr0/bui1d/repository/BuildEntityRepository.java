/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.repository;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.dalo.BuildEntityDALO;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.dalo.ProjectToBuildsEntityRelationshipDALO;
import com.liferay.jethr0.project.repository.ProjectEntityRepository;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildEntityRepository extends BaseEntityRepository<Build> {

	public Build add(Project project, JSONObject jsonObject) {
		jsonObject.put("r_projectToBuilds_c_projectId", project.getId());

		Build build = add(jsonObject);

		build.setProject(project);

		project.addBuild(build);

		return build;
	}

	public Build add(
		Project project, String buildName, String jobName, Build.State state) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"buildName", buildName
		).put(
			"jobName", jobName
		).put(
			"state", state.getJSONObject()
		);

		return add(project, jsonObject);
	}

	public Set<Build> getAll(Project project) {
		Set<Build> builds = new HashSet<>(
			_projectToBuildsEntityRelationshipDALO.getChildEntities(project));

		for (Build build : builds) {
			build.setProject(project);
		}

		return addAll(builds);
	}

	@Override
	public EntityDALO<Build> getEntityDALO() {
		return _buildEntityDALO;
	}

	@Override
	public void initialize() {
	}

	@Override
	public synchronized void initializeRelationships() {
		if (_initializedRelationships) {
			return;
		}

		_projectEntityRepository.initializeRelationships();

		for (Build build : getAll()) {
			Project project = null;

			long projectId = build.getProjectId();

			if (projectId != 0) {
				project = _projectEntityRepository.getById(projectId);
			}

			build.setProject(project);

			build.addBuildParameters(
				_buildParameterEntityRepository.getAll(build));

			build.addBuildRuns(_buildRunEntityRepository.getAll(build));
		}

		_initializedRelationships = true;
	}

	public void setBuildParameterRepository(
		BuildParameterEntityRepository buildParameterEntityRepository) {

		_buildParameterEntityRepository = buildParameterEntityRepository;
	}

	public void setBuildRunRepository(
		BuildRunEntityRepository buildRunEntityRepository) {

		_buildRunEntityRepository = buildRunEntityRepository;
	}

	public void setProjectRepository(
		ProjectEntityRepository projectEntityRepository) {

		_projectEntityRepository = projectEntityRepository;
	}

	@Autowired
	private BuildEntityDALO _buildEntityDALO;

	private BuildParameterEntityRepository _buildParameterEntityRepository;
	private BuildRunEntityRepository _buildRunEntityRepository;
	private boolean _initializedRelationships;
	private ProjectEntityRepository _projectEntityRepository;

	@Autowired
	private ProjectToBuildsEntityRelationshipDALO
		_projectToBuildsEntityRelationshipDALO;

}