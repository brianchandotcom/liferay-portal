/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.queue;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildParameterEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.environment.repository.EnvironmentEntityRepository;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.dalo.ProjectToBuildsEntityRelationshipDALO;
import com.liferay.jethr0.project.queue.ProjectQueue;
import com.liferay.jethr0.project.repository.ProjectEntityRepository;
import com.liferay.jethr0.task.repository.TaskEntityRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

		sort();
	}

	public void addBuilds(Set<Build> builds) {
		if (builds == null) {
			return;
		}

		builds.removeAll(Collections.singleton(null));

		if (builds.isEmpty()) {
			return;
		}

		_sortedBuilds.addAll(builds);

		sort();
	}

	public void addProject(Project project) {
		addProjects(Collections.singleton(project));
	}

	public void addProjects(Set<Project> projects) {
		for (Project project : projects) {
			_projectQueue.addProject(project);
		}

		sort();
	}

	public List<Build> getBuilds() {
		synchronized (_sortedBuilds) {
			return _sortedBuilds;
		}
	}

	public ProjectQueue getProjectQueue() {
		return _projectQueue;
	}

	public void initialize() {
		for (Project project : _projectQueue.getProjects()) {
			for (Build build : project.getBuilds()) {
				_buildRunEntityRepository.getAll(build);
				_buildParameterEntityRepository.getAll(build);
				_environmentEntityRepository.getAll(build);
				_taskEntityRepository.getAll(build);
			}
		}

		sort();

		for (Build build : getBuilds()) {
			System.out.println(build);
		}
	}

	public Build nextBuild(JenkinsNodeEntity jenkinsNodeEntity) {
		synchronized (_sortedBuilds) {
			Build nextBuild = null;

			for (Build build : _sortedBuilds) {
				if (!jenkinsNodeEntity.isCompatible(build)) {
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

		sort();
	}

	public void sort() {
		synchronized (_sortedBuilds) {
			_sortedBuilds.clear();

			_projectQueue.sort();

			for (Project project : _projectQueue.getProjects()) {
				List<Build> builds = new ArrayList<>(project.getBuilds());

				builds.removeAll(Collections.singleton(null));

				Collections.sort(builds, new ParentBuildComparator());

				for (Build build : builds) {
					if ((build.getState() == Build.State.BLOCKED) ||
						(build.getState() == Build.State.OPENED)) {

						_sortedBuilds.add(build);
					}
				}
			}
		}
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

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildParameterEntityRepository _buildParameterEntityRepository;

	@Autowired
	private BuildRunEntityRepository _buildRunEntityRepository;

	@Autowired
	private EnvironmentEntityRepository _environmentEntityRepository;

	@Autowired
	private ProjectEntityRepository _projectEntityRepository;

	@Autowired
	private ProjectQueue _projectQueue;

	@Autowired
	private ProjectToBuildsEntityRelationshipDALO
		_projectToBuildsEntityRelationshipDALO;

	private final List<Build> _sortedBuilds = new ArrayList<>();

	@Autowired
	private TaskEntityRepository _taskEntityRepository;

}