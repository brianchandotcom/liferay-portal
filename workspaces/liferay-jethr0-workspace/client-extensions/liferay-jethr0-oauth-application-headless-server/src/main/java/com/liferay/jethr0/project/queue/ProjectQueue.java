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

import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.comparator.ProjectComparator;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
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

	public void setProjectPrioritizer(ProjectPrioritizer projectPrioritizer) {
		_projectPrioritizer = projectPrioritizer;

		sort();
	}

	public void sort() {
		if (_projectPrioritizer == null) {
			return;
		}

		_projects.removeAll(Collections.singleton(null));

		Collections.sort(
			_projectPrioritizer.getProjectComparators(),
			Comparator.comparingInt(ProjectComparator::getPosition));

		_projects.sort(new PrioritizedProjectComparator());
	}

	private ProjectPrioritizer _projectPrioritizer;
	private final List<Project> _projects = new ArrayList<>();

	private class PrioritizedProjectComparator implements Comparator<Project> {

		@Override
		public int compare(Project project1, Project project2) {
			for (ProjectComparator projectComparator :
					_projectPrioritizer.getProjectComparators()) {

				int result = projectComparator.compare(project1, project2);

				if (result != 0) {
					return result;
				}
			}

			return 0;
		}

	}

}