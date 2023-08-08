/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.repository;

import com.liferay.jethr0.bui1d.repository.BuildRepository;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.dalo.ProjectDALO;
import com.liferay.jethr0.project.dalo.ProjectToBuildsDALO;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectRepository extends BaseEntityRepository<Project> {

	public Project add(
		String name, int position, int priority, Date startDate,
		Project.State state, Project.Type type) {

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
	public ProjectDALO getEntityDALO() {
		return _projectDALO;
	}

	@Override
	public void initialize() {
		addAll(
			_projectDALO.getProjectsByState(
				Project.State.QUEUED, Project.State.RUNNING));
	}

	@Override
	public synchronized void initializeRelationships() {
		if (_initializedRelationships) {
			return;
		}

		for (Project project : getAll()) {
			project.addBuilds(_buildRepository.getAll(project));
		}

		_initializedRelationships = true;
	}

	public void setBuildRepository(BuildRepository buildRepository) {
		_buildRepository = buildRepository;
	}

	private BuildRepository _buildRepository;
	private boolean _initializedRelationships;

	@Autowired
	private ProjectDALO _projectDALO;

	@Autowired
	private ProjectToBuildsDALO _projectToBuildsDALO;

}