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

	public Project createProject(
		String name, int priority, Project.State state, Project.Type type) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"name", name
		).put(
			"priority", priority
		).put(
			"state", state.getJSONObject()
		).put(
			"type", type.getJSONObject()
		);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return ProjectFactory.newProject(responseJSONObject);
	}

	public void deleteProject(Project project) {
		if (project == null) {
			return;
		}

		delete(project.getId());

		ProjectFactory.removeProject(project);
	}

	public List<Project> retrieveProjects() {
		List<Project> projects = new ArrayList<>();

		for (JSONObject jsonObject : retrieve()) {
			Project project = ProjectFactory.newProject(jsonObject);

			project.addGitBranches(
				_projectGitBranchDALO.retrieveGitBranches(project));
			project.addTestSuites(
				_projectTestSuiteDALO.retrieveTestSuites(project));

			projects.add(project);
		}

		return projects;
	}

	public Project updateProject(Project project) {
		_projectGitBranchDALO.updateRelationships(project);
		_projectTestSuiteDALO.updateRelationships(project);

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
	private ProjectBuildDALO _projectBuildDALO;

	@Autowired
	private ProjectGitBranchDALO _projectGitBranchDALO;

	@Autowired
	private ProjectTestSuiteDALO _projectTestSuiteDALO;

}