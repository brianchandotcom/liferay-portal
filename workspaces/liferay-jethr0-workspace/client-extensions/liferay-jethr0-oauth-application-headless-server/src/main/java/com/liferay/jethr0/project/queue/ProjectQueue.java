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
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.comparator.BaseProjectComparator;
import com.liferay.jethr0.project.comparator.ProjectComparator;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;
import com.liferay.jethr0.project.repository.ProjectComparatorRepository;
import com.liferay.jethr0.project.repository.ProjectPrioritizerRepository;
import com.liferay.jethr0.project.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectQueue {

	public void addProject(Project project) {
		if (project == null) {
			return;
		}

		_projects.add(project);

		sort();
	}

	public void addProjects(List<Project> projects) {
		if (projects == null) {
			return;
		}

		projects.removeAll(Collections.singleton(null));

		if (projects.isEmpty()) {
			return;
		}

		_projects.addAll(projects);

		sort();
	}

	public ProjectPrioritizer getProjectPrioritizer() {
		return _projectPrioritizer;
	}

	public List<Project> getProjects() {
		return _projects;
	}

	public void initialize() {
		setProjectPrioritizer(_getDefaultProjectPrioritizer());

		addProjects(
			_projectRepository.getByStates(
				Project.State.QUEUED, Project.State.RUNNING));

		for (Project project : getProjects()) {
			Project.State projectState = Project.State.COMPLETED;

			for (Build projectBuild : project.getBuilds()) {
				Build.State buildState = projectBuild.getState();

				if (buildState != Build.State.COMPLETED) {
					projectState = Project.State.RUNNING;

					break;
				}
			}

			if (projectState == Project.State.COMPLETED) {
				project.setState(projectState);

				_projectRepository.update(project);
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