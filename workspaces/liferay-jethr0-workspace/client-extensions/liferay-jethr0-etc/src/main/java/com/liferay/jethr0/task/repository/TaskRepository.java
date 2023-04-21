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

package com.liferay.jethr0.task.repository;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.dalo.BuildToTasksDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.dalo.ProjectToTasksDALO;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.task.dalo.TaskDALO;
import com.liferay.jethr0.task.dalo.TaskToEnvironmentsDALO;

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

		Set<Long> taskIds = _buildToTasksDALO.getChildEntityIds(build);

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

		Set<Long> taskIds = _projectToTasksDALO.getChildEntityIds(project);

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

	@Override
	public Task updateEntityRelationshipsInDatabase(Task task) {
		for (Build build : _buildToTasksDALO.getParentEntities(task)) {
			task.setBuild(build);

			build.addTask(task);
		}

		for (Project project : _projectToTasksDALO.getParentEntities(task)) {
			task.setProject(project);

			project.addTask(task);
		}

		for (Environment environment :
				_taskToEnvironmentsDALO.getChildEntities(task)) {

			environment.setTask(task);

			task.addEnvironment(environment);
		}

		return task;
	}

	@Override
	protected Task updateEntityRelationshipsFromDatabase(Task task) {
		_buildToTasksDALO.updateParentEntities(task);
		_projectToTasksDALO.updateParentEntities(task);
		_taskToEnvironmentsDALO.updateChildEntities(task);

		return task;
	}

	@Autowired
	private BuildToTasksDALO _buildToTasksDALO;

	@Autowired
	private ProjectToTasksDALO _projectToTasksDALO;

	@Autowired
	private TaskDALO _taskDALO;

	@Autowired
	private TaskToEnvironmentsDALO _taskToEnvironmentsDALO;

}