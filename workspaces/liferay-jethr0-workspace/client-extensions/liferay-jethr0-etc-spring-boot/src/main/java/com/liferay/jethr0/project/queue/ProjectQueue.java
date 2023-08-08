/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.queue;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.repository.BuildParameterRepository;
import com.liferay.jethr0.bui1d.repository.BuildRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunRepository;
import com.liferay.jethr0.bui1d.run.BuildRun;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Michael Hashimoto
 */
@Configuration
@EnableScheduling
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
		synchronized (_projects) {
			return _projects;
		}
	}

	public void initialize() {
		_projectComparatorRepository.initialize();
		_projectPrioritizerRepository.initialize();

		_projectComparatorRepository.setProjectPrioritizerRepository(
			_projectPrioritizerRepository);

		_projectPrioritizerRepository.setProjectComparatorRepository(
			_projectComparatorRepository);

		_projectComparatorRepository.initializeRelationships();
		_projectPrioritizerRepository.initializeRelationships();

		_buildParameterRepository.initialize();
		_buildRepository.initialize();
		_buildRunRepository.initialize();
		_projectRepository.initialize();

		_buildRepository.setBuildParameterRepository(_buildParameterRepository);
		_buildRepository.setBuildRunRepository(_buildRunRepository);
		_buildRepository.setProjectRepository(_projectRepository);

		_buildRunRepository.setBuildRepository(_buildRepository);

		_buildParameterRepository.setBuildRepository(_buildRepository);

		_projectRepository.setBuildRepository(_buildRepository);

		_buildParameterRepository.initializeRelationships();
		_buildRepository.initializeRelationships();
		_buildRunRepository.initializeRelationships();
		_projectRepository.initializeRelationships();

		setProjectPrioritizer(_getDefaultProjectPrioritizer());

		addProjects(_projectRepository.getAll());

		update();
	}

	public void removeProjects(Set<Project> projects) {
		if (projects == null) {
			return;
		}

		projects.removeAll(Collections.singleton(null));

		if (projects.isEmpty()) {
			return;
		}

		_projects.removeAll(projects);
	}

	@Scheduled(cron = "${liferay.jethr0.project.queue.update.cron}")
	public void scheduledUpdate() {
		if (_log.isInfoEnabled()) {
			_log.info("Updating project queue");
		}

		update();
	}

	public void setProjectPrioritizer(ProjectPrioritizer projectPrioritizer) {
		_projectPrioritizer = projectPrioritizer;

		sort();
	}

	public void sort() {
		if (_projectPrioritizer == null) {
			return;
		}

		synchronized (_projects) {
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

			for (int i = 0; i < _projects.size(); i++) {
				Project project = _projects.get(i);

				project.setPosition(i + 1);

				_projectRepository.update(project);
			}
		}
	}

	public void update() {
		synchronized (_projects) {
			Set<Project> completedProjects = new HashSet<>();

			for (Project project : getProjects()) {
				if (project.getState() == Project.State.COMPLETED) {
					completedProjects.add(project);

					continue;
				}

				System.out.println(project);

				for (Build build : project.getBuilds()) {
					if (build.getState() == Build.State.COMPLETED) {
						continue;
					}

					Set<BuildRun> buildRuns = _buildRunRepository.getAll(build);

					boolean blocked = false;

					for (BuildRun buildRun : buildRuns) {
						if (buildRun.isBlocked()) {
							blocked = true;

							break;
						}
					}

					if (blocked) {
						build.setState(Build.State.BLOCKED);

						_buildRepository.update(build);
					}

					System.out.println("> " + build);
				}
			}

			removeProjects(completedProjects);
		}
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
			projectPrioritizer, 1, ProjectComparator.Type.PROJECT_START_DATE,
			null);
		_projectComparatorRepository.add(
			projectPrioritizer, 2, ProjectComparator.Type.PROJECT_PRIORITY,
			null);
		_projectComparatorRepository.add(
			projectPrioritizer, 3, ProjectComparator.Type.FIFO, null);

		return projectPrioritizer;
	}

	private static final Log _log = LogFactory.getLog(ProjectQueue.class);

	@Autowired
	private BuildParameterRepository _buildParameterRepository;

	@Autowired
	private BuildRepository _buildRepository;

	@Autowired
	private BuildRunRepository _buildRunRepository;

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