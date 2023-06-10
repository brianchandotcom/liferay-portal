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

package com.liferay.jethr0.project.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.dalo.ProjectDALO;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectRepository extends BaseEntityRepository<Project> {

	public Project add(
		String name, int priority, Project.State state, Project.Type type) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"name", name
		).put(
			"priority", priority
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
		ProjectDALO projectDALO = getEntityDALO();

		addAll(
			projectDALO.getProjectsByState(
				Project.State.OPENED, Project.State.QUEUED,
				Project.State.RUNNING));
	}

	@Autowired
	private ProjectDALO _projectDALO;

}