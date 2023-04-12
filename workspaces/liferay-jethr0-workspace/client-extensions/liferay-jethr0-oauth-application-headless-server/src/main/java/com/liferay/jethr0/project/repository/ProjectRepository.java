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

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.gitbranch.GitBranch;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.dalo.ProjectDALO;
import com.liferay.jethr0.project.dalo.ProjectToBuildsDALO;
import com.liferay.jethr0.project.dalo.ProjectToTasksDALO;
import com.liferay.jethr0.project.dalo.ProjectsToGitBranchesDALO;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.testsuite.TestSuite;
import com.liferay.jethr0.testsuite.dalo.ProjectsToTestSuitesDALO;

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
		_projectsToGitBranchesDALO.updateChildEntities(project);
		_projectToTasksDALO.updateChildEntities(project);
		_projectsToTestSuitesDALO.updateChildEntities(project);

		return project;
	}

	@Override
	protected Project updateEntityRelationshipsFromDatabase(Project project) {
		for (Build build : _projectToBuildsDALO.getChildEntities(project)) {
			project.addBuild(build);

			build.setProject(project);
		}

		for (GitBranch gitBranch :
				_projectsToGitBranchesDALO.getChildEntities(project)) {

			project.addGitBranch(gitBranch);

			gitBranch.addProject(project);
		}

		for (Task task : _projectToTasksDALO.getChildEntities(project)) {
			project.addTask(task);

			task.setProject(project);
		}

		for (TestSuite testSuite :
				_projectsToTestSuitesDALO.getChildEntities(project)) {

			project.addTestSuite(testSuite);

			testSuite.addProject(project);
		}

		return project;
	}

	@Autowired
	private ProjectDALO _projectDALO;

	@Autowired
	private ProjectsToGitBranchesDALO _projectsToGitBranchesDALO;

	@Autowired
	private ProjectsToTestSuitesDALO _projectsToTestSuitesDALO;

	@Autowired
	private ProjectToBuildsDALO _projectToBuildsDALO;

	@Autowired
	private ProjectToTasksDALO _projectToTasksDALO;

}