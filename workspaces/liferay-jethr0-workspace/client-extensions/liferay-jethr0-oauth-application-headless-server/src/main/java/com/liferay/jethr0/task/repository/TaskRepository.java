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

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.dalo.ProjectToTasksDALO;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.task.dalo.TaskDALO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TaskRepository extends BaseEntityRepository<Task> {

	@Override
	public TaskDALO getEntityDALO() {
		return _taskDALO;
	}

	@Override
	public Task updateEntityRelationshipsInDatabase(Task task) {
		return task;
	}

	@Override
	protected Task updateEntityRelationshipsFromDatabase(Task task) {
		return task;
	}

	@Autowired
	private ProjectToTasksDALO _projectToTasksDALO;

	@Autowired
	private TaskDALO _taskDALO;

}