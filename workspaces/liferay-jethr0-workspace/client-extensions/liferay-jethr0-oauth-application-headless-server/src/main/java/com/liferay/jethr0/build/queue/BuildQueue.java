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

package com.liferay.jethr0.build.queue;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.queue.ProjectQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildQueue {

	public void addBuild(Build build) {
		if (build == null) {
			return;
		}

		_sortedBuilds.add(build);

		_sort();
	}

	public void addBuilds(List<Build> builds) {
		if (builds == null) {
			return;
		}

		builds.removeAll(Collections.singleton(null));

		if (builds.isEmpty()) {
			return;
		}

		_sortedBuilds.addAll(builds);

		_sort();
	}

	public void addProject(Project project) {
		addProjects(Arrays.asList(project));
	}

	public void addProjects(List<Project> projects) {
		for (Project project : projects) {
			_projectQueue.addProject(project);
		}

		_sort();
	}

	public List<Build> getBuilds() {
		return _sortedBuilds;
	}

	public ProjectQueue getProjectQueue() {
		return _projectQueue;
	}

	public void initialize() {
		_sort();
	}

	public Build nextBuild(JenkinsNode jenkinsNode) {
		synchronized (_sortedBuilds) {
			Build nextBuild = null;

			for (Build build : _sortedBuilds) {
				if (!jenkinsNode.isCompatible(build)) {
					continue;
				}

				nextBuild = build;

				break;
			}

			_sortedBuilds.remove(nextBuild);

			return nextBuild;
		}
	}

	public void setProjectQueue(ProjectQueue projectQueue) {
		_projectQueue = projectQueue;

		_sort();
	}

	public static class ParentBuildComparator implements Comparator<Build> {

		@Override
		public int compare(Build build1, Build build2) {
			if (build1.isParentBuild(build2)) {
				return -1;
			}

			if (build2.isParentBuild(build1)) {
				return 1;
			}

			return 0;
		}

	}

	private void _sort() {
		_sortedBuilds.clear();

		_projectQueue.sort();

		for (Project project : _projectQueue.getProjects()) {
			List<Build> builds = new ArrayList<>(project.getBuilds());

			builds.removeAll(Collections.singleton(null));

			Collections.sort(builds, new ParentBuildComparator());

			for (Build build : builds) {
				if (build.getState() != Build.State.OPENED) {
					continue;
				}

				_sortedBuilds.add(build);
			}
		}
	}

	@Autowired
	private ProjectQueue _projectQueue;

	private final List<Build> _sortedBuilds = new ArrayList<>();

}