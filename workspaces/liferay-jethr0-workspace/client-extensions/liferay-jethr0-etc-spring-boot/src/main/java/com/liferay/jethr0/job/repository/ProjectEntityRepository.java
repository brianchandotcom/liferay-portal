/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.repository;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.ProjectEntity;
import com.liferay.jethr0.job.dalo.ProjectEntityDALO;
import com.liferay.jethr0.job.dalo.ProjectToBuildsEntityRelationshipDALO;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectEntityRepository
	extends BaseEntityRepository<ProjectEntity> {

	public ProjectEntity add(
		String name, int position, int priority, Date startDate,
		ProjectEntity.State state, ProjectEntity.Type type) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"name", name
		).put(
			"position", position
		).put(
			"priority", priority
		).put(
			"startDate", StringUtil.toString(startDate)
		).put(
			"state", state.getJSONObject()
		).put(
			"type", type.getJSONObject()
		);

		return add(jsonObject);
	}

	@Override
	public ProjectEntity getById(long id) {
		if (hasEntity(id)) {
			return super.getById(id);
		}

		ProjectEntity projectEntity = _projectEntityDALO.get(id);

		projectEntity.addBuildEntities(
			_buildEntityRepository.getAll(projectEntity));

		for (BuildEntity buildEntity : projectEntity.getBuildEntities()) {
			buildEntity.setProjectEntity(projectEntity);
		}

		return add(projectEntity);
	}

	@Override
	public ProjectEntityDALO getEntityDALO() {
		return _projectEntityDALO;
	}

	@Override
	public void initialize() {
		addAll(
			_projectEntityDALO.getProjectsByState(
				ProjectEntity.State.QUEUED, ProjectEntity.State.RUNNING));
	}

	@Override
	public synchronized void initializeRelationships() {
		if (_initializedRelationships) {
			return;
		}

		for (ProjectEntity projectEntity : getAll()) {
			projectEntity.addBuildEntities(
				_buildEntityRepository.getAll(projectEntity));
		}

		_initializedRelationships = true;
	}

	public void setBuildEntityRepository(
		BuildEntityRepository buildEntityRepository) {

		_buildEntityRepository = buildEntityRepository;
	}

	private BuildEntityRepository _buildEntityRepository;
	private boolean _initializedRelationships;

	@Autowired
	private ProjectEntityDALO _projectEntityDALO;

	@Autowired
	private ProjectToBuildsEntityRelationshipDALO
		_projectToBuildsEntityRelationshipDALO;

}