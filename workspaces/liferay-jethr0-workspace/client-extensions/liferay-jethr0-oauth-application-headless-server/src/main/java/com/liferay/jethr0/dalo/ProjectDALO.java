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

import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.ProjectFactory;
import com.liferay.jethr0.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectDALO extends BaseDALO {

	public Project createProject(Project project) {
		JSONObject responseJSONObject = create(project.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		project.setCreatedDate(
			StringUtil.toDate(responseJSONObject.getString("dateCreated")));
		project.setId(responseJSONObject.getLong("id"));

		return project;
	}

	public void deleteProject(Project project) {
		if (project == null) {
			return;
		}

		delete(project.getId());
	}

	public List<Project> retrieveProjects() {
		List<Project> projects = new ArrayList<>();

		for (JSONObject jsonObject : retrieve()) {
			Project project = ProjectFactory.newProject(jsonObject);

			project.addGitBranches(
				_projectsToGitBranchesDALO.retrieveGitBranches(project));
			project.addTestSuites(
				_projectsToTestSuitesDALO.retrieveTestSuites(project));

			projects.add(project);
		}

		return projects;
	}

	public Project updateProject(Project project) {
		_projectToBuildsDALO.updateRelationships(project);
		_projectsToGitBranchesDALO.updateRelationships(project);
		_projectsToTestSuitesDALO.updateRelationships(project);

		JSONObject responseJSONObject = update(project.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return project;
	}

	protected String getObjectDefinitionLabel() {
		return "Project";
	}

	@Autowired
	private ProjectsToGitBranchesDALO _projectsToGitBranchesDALO;

	@Autowired
	private ProjectsToTestSuitesDALO _projectsToTestSuitesDALO;

	@Autowired
	private ProjectToBuildsDALO _projectToBuildsDALO;

}