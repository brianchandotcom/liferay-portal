/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.repository;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.dalo.BuildEntityDALO;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.ProjectEntity;
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
public class BuildEntityRepository extends BaseEntityRepository<BuildEntity> {

	public BuildEntity add(ProjectEntity projectEntity, JSONObject jsonObject) {
		jsonObject.put("r_projectToBuilds_c_projectId", projectEntity.getId());

		BuildEntity buildEntity = add(jsonObject);

		buildEntity.setProjectEntity(projectEntity);

		projectEntity.addBuildEntity(buildEntity);

		return buildEntity;
	}

	public BuildEntity add(
		ProjectEntity projectEntity, String buildName, String jobName,
		BuildEntity.State state) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"buildName", buildName
		).put(
			"jobName", jobName
		).put(
			"state", state.getJSONObject()
		);

		return add(projectEntity, jsonObject);
	}

	public Set<BuildEntity> getAll(ProjectEntity projectEntity) {
		Set<BuildEntity> buildEntities = new HashSet<>(
			_projectToBuildsEntityRelationshipDALO.getChildEntities(
				projectEntity));

		for (BuildEntity buildEntity : buildEntities) {
			buildEntity.setProjectEntity(projectEntity);
		}

		return addAll(buildEntities);
	}

	@Override
	public EntityDALO<BuildEntity> getEntityDALO() {
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

		for (BuildEntity buildEntity : getAll()) {
			ProjectEntity projectEntity = null;

			long projectId = buildEntity.getProjectEntityId();

			if (projectId != 0) {
				projectEntity = _projectEntityRepository.getById(projectId);
			}

			buildEntity.setProjectEntity(projectEntity);

			buildEntity.addBuildParameterEntities(
				_buildParameterEntityRepository.getAll(buildEntity));

			buildEntity.addBuildRunEntities(
				_buildRunEntityRepository.getAll(buildEntity));
		}

		_initializedRelationships = true;
	}

	public void setBuildParameterEntityRepository(
		BuildParameterEntityRepository buildParameterEntityRepository) {

		_buildParameterEntityRepository = buildParameterEntityRepository;
	}

	public void setBuildRunEntityRepository(
		BuildRunEntityRepository buildRunEntityRepository) {

		_buildRunEntityRepository = buildRunEntityRepository;
	}

	public void setProjectEntityRepository(
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