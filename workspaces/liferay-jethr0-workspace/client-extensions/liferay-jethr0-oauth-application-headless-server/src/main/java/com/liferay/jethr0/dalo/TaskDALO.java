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

package com.liferay.jethr0.dalo;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.task.TaskFactory;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TaskDALO extends BaseDALO {

	public Task createTask(Build build, String name) {
		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("name", name);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return TaskFactory.newTask(build, responseJSONObject);
	}

	public void deleteTask(Task task) {
		if (task == null) {
			return;
		}

		Build build = task.getBuild();

		build.removeTask(task);

		delete(task.getId());

		TaskFactory.removeTask(task);
	}

	public List<Task> retrieveTasks(Build build) {
		List<Task> tasks = new ArrayList<>();

		for (JSONObject responseJSONObject : retrieve()) {
			Task task = TaskFactory.newTask(build, responseJSONObject);

			task.addTaskRuns(_taskToTaskRunsDALO.retrieveTaskRuns(task));

			tasks.add(task);
		}

		return tasks;
	}

	public Task updateTask(Task task) {
		_taskToTaskRunsDALO.updateRelationships(task);

		JSONObject responseJSONObject = update(task.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return task;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Task";
	}

	@Autowired
	private TaskToTaskRunsDALO _taskToTaskRunsDALO;

}