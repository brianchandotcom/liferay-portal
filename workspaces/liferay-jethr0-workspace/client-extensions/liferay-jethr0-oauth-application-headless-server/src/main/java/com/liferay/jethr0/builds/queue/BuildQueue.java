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

package com.liferay.jethr0.builds.queue;

import com.liferay.jethr0.builds.Build;
import com.liferay.jethr0.jenkins.master.JenkinsMaster;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.queue.ProjectQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

		_builds.add(build);

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

		_builds.addAll(builds);

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
		return _builds;
	}

	public ProjectQueue getProjectQueue() {
		return _projectQueue;
	}

	public Build nextBuild(JenkinsMaster jenkinsMaster) {
		synchronized (_builds) {
			Build nextBuild = null;

			for (Build build : _builds) {
				if (!jenkinsMaster.isCompatible(build)) {
					continue;
				}

				nextBuild = build;
			}

			_builds.remove(nextBuild);

			return nextBuild;
		}
	}

	public void setProjectQueue(ProjectQueue projectQueue) {
		_projectQueue = projectQueue;
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
		_builds.clear();

		_projectQueue.sort();

		for (Project project : _projectQueue.getProjects()) {
			List<Build> builds = project.getBuilds();

			builds.removeAll(Collections.singleton(null));

			Collections.sort(builds, new ParentBuildComparator());

			for (Build build : builds) {
				if (build.getState() != Build.State.OPENED) {
					continue;
				}

				_builds.add(build);
			}
		}
	}

	private final List<Build> _builds = new ArrayList<>();
	private ProjectQueue _projectQueue;

}