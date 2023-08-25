/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.queue;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildParameterEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.gitbranch.repository.GitBranchEntityRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.comparator.BaseProjectComparatorEntity;
import com.liferay.jethr0.project.comparator.ProjectComparatorEntity;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizerEntity;
import com.liferay.jethr0.project.repository.ProjectComparatorEntityRepository;
import com.liferay.jethr0.project.repository.ProjectEntityRepository;
import com.liferay.jethr0.project.repository.ProjectPrioritizerEntityRepository;
import com.liferay.jethr0.task.repository.TaskEntityRepository;
import com.liferay.jethr0.testsuite.repository.TestSuiteEntityRepository;

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

	public ProjectPrioritizerEntity getProjectPrioritizerEntity() {
		return _projectPrioritizerEntity;
	}

	public List<Project> getProjects() {
		synchronized (_projects) {
			return _projects;
		}
	}

	public void initialize() {
		_projectComparatorEntityRepository.initialize();
		_projectPrioritizerEntityRepository.initialize();

		_projectComparatorEntityRepository.
			setProjectPrioritizerEntityRepository(
				_projectPrioritizerEntityRepository);

		_projectPrioritizerEntityRepository.
			setProjectComparatorEntityRepository(
				_projectComparatorEntityRepository);

		_projectComparatorEntityRepository.initializeRelationships();
		_projectPrioritizerEntityRepository.initializeRelationships();

		_buildParameterEntityRepository.initialize();
		_buildEntityRepository.initialize();
		_buildRunEntityRepository.initialize();
		_projectEntityRepository.initialize();

		_buildEntityRepository.setBuildParameterRepository(
			_buildParameterEntityRepository);
		_buildEntityRepository.setBuildRunRepository(_buildRunEntityRepository);
		_buildEntityRepository.setProjectRepository(_projectEntityRepository);

		_buildRunEntityRepository.setBuildRepository(_buildEntityRepository);

		_buildParameterEntityRepository.setBuildRepository(
			_buildEntityRepository);

		_projectEntityRepository.setBuildRepository(_buildEntityRepository);

		_buildParameterEntityRepository.initializeRelationships();
		_buildEntityRepository.initializeRelationships();
		_buildRunEntityRepository.initializeRelationships();
		_projectEntityRepository.initializeRelationships();

		setProjectPrioritizerEntity(_getDefaultProjectPrioritizerEntity());

		addProjects(_projectEntityRepository.getAll());

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

	public void setProjectPrioritizerEntity(
		ProjectPrioritizerEntity projectPrioritizerEntity) {

		_projectPrioritizerEntity = projectPrioritizerEntity;

		sort();
	}

	public void sort() {
		if (_projectPrioritizerEntity == null) {
			return;
		}

		synchronized (_projects) {
			_projects.removeAll(Collections.singleton(null));

			for (Project project : new ArrayList<>(_projects)) {
				boolean keepProject = false;

				for (BuildEntity buildEntity : project.getBuildEntities()) {
					if (buildEntity.getState() != BuildEntity.State.COMPLETED) {
						keepProject = true;

						break;
					}
				}

				if (!keepProject) {
					_projects.remove(project);
				}
			}

			_sortedProjectComparatorEntities.clear();

			_sortedProjectComparatorEntities.addAll(
				_projectPrioritizerEntity.getProjectComparatorEntities());

			Collections.sort(
				_sortedProjectComparatorEntities,
				Comparator.comparingInt(ProjectComparatorEntity::getPosition));

			_projects.sort(new PrioritizedProjectComparator());

			for (int i = 0; i < _projects.size(); i++) {
				Project project = _projects.get(i);

				project.setPosition(i + 1);

				_projectEntityRepository.update(project);
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

				for (BuildEntity buildEntity : project.getBuildEntities()) {
					if (buildEntity.getState() == BuildEntity.State.COMPLETED) {
						continue;
					}

					Set<BuildRunEntity> buildRunEntities =
						_buildRunEntityRepository.getAll(buildEntity);

					boolean blocked = false;

					for (BuildRunEntity buildRunEntity : buildRunEntities) {
						if (buildRunEntity.isBlocked()) {
							blocked = true;

							break;
						}
					}

					if (blocked) {
						buildEntity.setState(BuildEntity.State.BLOCKED);

						_buildEntityRepository.update(buildEntity);
					}

					System.out.println("> " + buildEntity);
				}
			}

			removeProjects(completedProjects);
		}
	}

	private ProjectPrioritizerEntity _getDefaultProjectPrioritizerEntity() {
		ProjectPrioritizerEntity projectPrioritizerEntity =
			_projectPrioritizerEntityRepository.getByName(
				_liferayProjectPrioritizer);

		if (projectPrioritizerEntity != null) {
			return projectPrioritizerEntity;
		}

		projectPrioritizerEntity = _projectPrioritizerEntityRepository.add(
			_liferayProjectPrioritizer);

		_projectComparatorEntityRepository.add(
			projectPrioritizerEntity, 1,
			ProjectComparatorEntity.Type.PROJECT_START_DATE, null);
		_projectComparatorEntityRepository.add(
			projectPrioritizerEntity, 2,
			ProjectComparatorEntity.Type.PROJECT_PRIORITY, null);
		_projectComparatorEntityRepository.add(
			projectPrioritizerEntity, 3, ProjectComparatorEntity.Type.FIFO,
			null);

		return projectPrioritizerEntity;
	}

	private static final Log _log = LogFactory.getLog(ProjectQueue.class);

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildParameterEntityRepository _buildParameterEntityRepository;

	@Autowired
	private BuildRunEntityRepository _buildRunEntityRepository;

	@Autowired
	private GitBranchEntityRepository _gitBranchEntityRepository;

	@Value("${liferay.jethr0.project.prioritizer}")
	private String _liferayProjectPrioritizer;

	@Autowired
	private ProjectComparatorEntityRepository
		_projectComparatorEntityRepository;

	@Autowired
	private ProjectEntityRepository _projectEntityRepository;

	private ProjectPrioritizerEntity _projectPrioritizerEntity;

	@Autowired
	private ProjectPrioritizerEntityRepository
		_projectPrioritizerEntityRepository;

	private final List<Project> _projects = new ArrayList<>();
	private final List<ProjectComparatorEntity>
		_sortedProjectComparatorEntities = new ArrayList<>();

	@Autowired
	private TaskEntityRepository _taskEntityRepository;

	@Autowired
	private TestSuiteEntityRepository _testSuiteEntityRepository;

	private class PrioritizedProjectComparator implements Comparator<Project> {

		@Override
		public int compare(Project project1, Project project2) {
			for (ProjectComparatorEntity projectComparatorEntity :
					_sortedProjectComparatorEntities) {

				if (!(projectComparatorEntity instanceof
						BaseProjectComparatorEntity)) {

					continue;
				}

				BaseProjectComparatorEntity baseProjectComparator =
					(BaseProjectComparatorEntity)projectComparatorEntity;

				int result = baseProjectComparator.compare(project1, project2);

				if (result != 0) {
					return result;
				}
			}

			return 0;
		}

	}

}