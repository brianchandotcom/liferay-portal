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

package com.liferay.jethr0.project.queue;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.gitbranch.repository.GitBranchRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.comparator.BaseProjectComparator;
import com.liferay.jethr0.project.comparator.ProjectComparator;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;
import com.liferay.jethr0.project.repository.ProjectComparatorRepository;
import com.liferay.jethr0.project.repository.ProjectPrioritizerRepository;
import com.liferay.jethr0.project.repository.ProjectRepository;
import com.liferay.jethr0.task.repository.TaskRepository;
import com.liferay.jethr0.testsuite.repository.TestSuiteRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectQueue {

	public void addProject(Project project) {
		addProjects(Collections.singleton(project));
	}

	public void addProjects(Set<Project> projects) {
		if (projects == null) {
			return;
		}

		projects.removeAll(Collections.singleton(null));

		if (projects.isEmpty()) {
			return;
		}

		boolean sort = false;

		for (Project project : projects) {
			if (_projects.contains(project)) {
				continue;
			}

			_projects.add(project);

			sort = true;
		}

		if (sort) {
			sort();
		}
	}

	public ProjectPrioritizer getProjectPrioritizer() {
		return _projectPrioritizer;
	}

	public List<Project> getProjects() {
		return _projects;
	}

	public void initialize() {
		for (ProjectPrioritizer projectPrioritizer :
				_projectPrioritizerRepository.getAll()) {

			_projectComparatorRepository.getAll(projectPrioritizer);
		}

		setProjectPrioritizer(_getDefaultProjectPrioritizer());

		Set<Project> projects = new HashSet<>();

		for (Project project : _projectRepository.getAll()) {
			_buildRepository.getAll(project);
			_gitBranchRepository.getAll(project);
			_taskRepository.getAll(project);
			_testSuiteRepository.getAll(project);

			Project.State state = project.getState();

			if ((state != Project.State.QUEUED) &&
				(state != Project.State.RUNNING)) {

				continue;
			}

			projects.add(project);
		}

		addProjects(projects);

		for (Project project : getProjects()) {
			System.out.println(project);

			for (Build build : project.getBuilds()) {
				System.out.println("> " + build);
			}
		}
	}

	public void setProjectPrioritizer(ProjectPrioritizer projectPrioritizer) {
		_projectPrioritizer = projectPrioritizer;

		sort();
	}

	public void sort() {
		if (_projectPrioritizer == null) {
			return;
		}

		_projects.removeAll(Collections.singleton(null));

		for (Project project : new ArrayList<>(_projects)) {
			boolean keepProject = false;

			for (Build build : project.getBuilds()) {
				if (build.getState() != Build.State.COMPLETED) {
					keepProject = true;

					break;
				}
			}

			if (!keepProject) {
				_projects.remove(project);
			}
		}

		_sortedProjectComparators.clear();

		_sortedProjectComparators.addAll(
			_projectPrioritizer.getProjectComparators());

		Collections.sort(
			_sortedProjectComparators,
			Comparator.comparingInt(ProjectComparator::getPosition));

		_projects.sort(new PrioritizedProjectComparator());
	}

	private ProjectPrioritizer _getDefaultProjectPrioritizer() {
		ProjectPrioritizer projectPrioritizer =
			_projectPrioritizerRepository.getByName(_liferayProjectPrioritizer);

		if (projectPrioritizer != null) {
			return projectPrioritizer;
		}

		projectPrioritizer = _projectPrioritizerRepository.add(
			_liferayProjectPrioritizer);

		_projectComparatorRepository.add(
			projectPrioritizer, 1, ProjectComparator.Type.PROJECT_PRIORITY,
			null);
		_projectComparatorRepository.add(
			projectPrioritizer, 2, ProjectComparator.Type.FIFO, null);

		return projectPrioritizer;
	}

	@Autowired
	private BuildRepository _buildRepository;

	@Autowired
	private GitBranchRepository _gitBranchRepository;

	@Value("${liferay.jethr0.project.prioritizer}")
	private String _liferayProjectPrioritizer;

	@Autowired
	private ProjectComparatorRepository _projectComparatorRepository;

	private ProjectPrioritizer _projectPrioritizer;

	@Autowired
	private ProjectPrioritizerRepository _projectPrioritizerRepository;

	@Autowired
	private ProjectRepository _projectRepository;

	private final List<Project> _projects = new ArrayList<>();
	private final List<ProjectComparator> _sortedProjectComparators =
		new ArrayList<>();

	@Autowired
	private TaskRepository _taskRepository;

	@Autowired
	private TestSuiteRepository _testSuiteRepository;

	private class PrioritizedProjectComparator implements Comparator<Project> {

		@Override
		public int compare(Project project1, Project project2) {
			for (ProjectComparator projectComparator :
					_sortedProjectComparators) {

				if (!(projectComparator instanceof BaseProjectComparator)) {
					continue;
				}

				BaseProjectComparator baseProjectComparator =
					(BaseProjectComparator)projectComparator;

				int result = baseProjectComparator.compare(project1, project2);

				if (result != 0) {
					return result;
				}
			}

			return 0;
		}

	}

}