/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.task.repository;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.dalo.BuildToTasksEntityRelationshipDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.dalo.ProjectToTasksEntityRelationshipDALO;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.task.dalo.TaskDALO;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TaskRepository extends BaseEntityRepository<Task> {

	public Set<Task> getAll(Build build) {
		Set<Task> buildTasks = new HashSet<>();

		Set<Long> taskIds =
			_buildToTasksEntityRelationshipDALO.getChildEntityIds(build);

		for (Task task : getAll()) {
			if (!taskIds.contains(task.getId())) {
				continue;
			}

			task.setBuild(build);

			build.addTask(task);

			buildTasks.add(task);
		}

		return buildTasks;
	}

	public Set<Task> getAll(Project project) {
		Set<Task> projectTasks = new HashSet<>();

		Set<Long> taskIds =
			_projectToTasksEntityRelationshipDALO.getChildEntityIds(project);

		for (Task task : getAll()) {
			if (!taskIds.contains(task.getId())) {
				continue;
			}

			task.setProject(project);

			project.addTask(task);

			projectTasks.add(task);
		}

		return projectTasks;
	}

	@Override
	public TaskDALO getEntityDALO() {
		return _taskDALO;
	}

	@Autowired
	private BuildToTasksEntityRelationshipDALO
		_buildToTasksEntityRelationshipDALO;

	@Autowired
	private ProjectToTasksEntityRelationshipDALO
		_projectToTasksEntityRelationshipDALO;

	@Autowired
	private TaskDALO _taskDALO;

}