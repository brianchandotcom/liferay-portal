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

import com.liferay.jethr0.dalo.ProjectDALO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Michael Hashimoto
 */
public class ProjectRepository {

	public void add(List<Project> projects) {
		if (projects == null) {
			return;
		}

		projects.removeAll(Collections.singleton(null));

		for (Project project : projects) {
			if (project.getId() == 0) {
				project = _projectDALO.createProject(project);
			}

			_projects.put(project.getId(), project);
		}
	}

	public void add(Project project) {
		add(Collections.singletonList(project));
	}

	public List<Project> get() {
		return new ArrayList<>(_projects.values());
	}

	public Project getById(long id) {
		return _projects.get(id);
	}

	public List<Project> getByState(Project.State state) {
		List<Project> projects = new ArrayList<>();

		for (Project project : _projects.values()) {
			if (project.getState() == state) {
				projects.add(project);
			}
		}

		return projects;
	}

	public void remove(List<Project> projects) {
		if (projects == null) {
			return;
		}

		projects.removeAll(Collections.singleton(null));

		for (Project project : projects) {
			_projects.remove(project.getId());

			_projectDALO.deleteProject(project);
		}
	}

	public void remove(Project project) {
		remove(Collections.singletonList(project));
	}

	public Project update(Project project) {
		if (project.getId() == 0) {
			project = _projectDALO.createProject(project);

			add(project);
		}

		return _projectDALO.updateProject(project);
	}

	@Autowired
	private ProjectDALO _projectDALO;

	private final Map<Long, Project> _projects = new HashMap<>();

}