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

package com.liferay.jethr0.project;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.dalo.ProjectToBuildsDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectRepository extends BaseEntityRepository<Project> {

	public List<Project> getByStates(Project.State... states) {
		List<Project.State> statesList = Arrays.asList(states);

		List<Project> projects = new ArrayList<>();

		for (Project project : getAll()) {
			if (!statesList.contains(project.getState())) {
				continue;
			}

			projects.add(project);
		}

		return projects;
	}

	@Override
	public ProjectDALO getEntityDALO() {
		return _projectDALO;
	}

	@Override
	public Project updateEntityRelationshipsInDatabase(Project project) {
		_projectToBuildsDALO.updateChildEntities(project);

		return project;
	}

	@Override
	protected Project updateEntityRelationshipsFromDatabase(Project project) {
		List<Build> builds = _projectToBuildsDALO.getChildEntities(project);

		project.addBuilds(builds);

		for (Build build : builds) {
			build.setProject(project);
		}

		return project;
	}

	@Autowired
	private ProjectDALO _projectDALO;

	@Autowired
	private ProjectToBuildsDALO _projectToBuildsDALO;

}