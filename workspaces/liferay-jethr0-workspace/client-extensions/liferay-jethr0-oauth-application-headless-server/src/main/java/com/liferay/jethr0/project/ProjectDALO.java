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
import com.liferay.jethr0.dalo.ProjectToTasksDALO;
import com.liferay.jethr0.dalo.ProjectsToGitBranchesDALO;
import com.liferay.jethr0.dalo.ProjectsToTestSuitesDALO;
import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.gitbranch.GitBranch;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.testsuite.TestSuite;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectDALO extends BaseEntityDALO<Project> {

	@Override
	public Project create(Project project) {
		project = super.create(project);

		_projectsToGitBranchesDALO.updateRelationships(project);
		_projectsToTestSuitesDALO.updateRelationships(project);
		_projectToBuildsDALO.updateRelationships(project);
		_projectToTasksDALO.updateRelationships(project);

		return project;
	}

	@Override
	public void delete(Project project) {
		for (GitBranch gitBranch : project.getGitBranches()) {
			_projectsToGitBranchesDALO.deleteRelationship(project, gitBranch);
		}

		for (TestSuite testSuite : project.getTestSuites()) {
			_projectsToTestSuitesDALO.deleteRelationship(project, testSuite);
		}

		for (Build build : project.getBuilds()) {
			_projectToBuildsDALO.deleteRelationship(project, build);
		}

		for (Task task : project.getTasks()) {
			_projectToTasksDALO.deleteRelationship(project, task);
		}

		super.delete(project);
	}

	@Override
	public List<Project> get() {
		List<Project> projects = new ArrayList<>();

		for (Project project : super.get()) {
			project.addGitBranches(
				_projectsToGitBranchesDALO.retrieveGitBranches(project));
			project.addTestSuites(
				_projectsToTestSuitesDALO.retrieveTestSuites(project));
			project.addBuilds(_projectToBuildsDALO.retrieveBuilds(project));
			project.addTasks(_projectToTasksDALO.retrieveTasks(project));

			projects.add(project);
		}

		return projects;
	}

	@Override
	public Project update(Project project) {
		project = super.update(project);

		_projectsToGitBranchesDALO.updateRelationships(project);
		_projectsToTestSuitesDALO.updateRelationships(project);
		_projectToBuildsDALO.updateRelationships(project);
		_projectToTasksDALO.updateRelationships(project);

		return project;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Project";
	}

	@Override
	protected Project newEntity(JSONObject jsonObject) {
		return ProjectFactory.newProject(jsonObject);
	}

	@Autowired
	private ProjectsToGitBranchesDALO _projectsToGitBranchesDALO;

	@Autowired
	private ProjectsToTestSuitesDALO _projectsToTestSuitesDALO;

	@Autowired
	private ProjectToBuildsDALO _projectToBuildsDALO;

	@Autowired
	private ProjectToTasksDALO _projectToTasksDALO;

}