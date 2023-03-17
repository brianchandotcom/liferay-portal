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

import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.task.run.TaskRun;
import com.liferay.jethr0.task.run.TaskRunFactory;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TaskRunDALO extends BaseDALO {

	public TaskRun createTaskRun(
		Task task, long duration, TaskRun.Result result) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"duration", duration
		).put(
			"r_taskToTaskRuns_c_taskId", task.getId()
		).put(
			"result", result.getJSONObject()
		);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return TaskRunFactory.newTaskRun(task, responseJSONObject);
	}

	public void deleteTaskRun(TaskRun taskRun) {
		if (taskRun == null) {
			return;
		}

		Task task = taskRun.getTask();

		task.removeTaskRun(taskRun);

		delete(taskRun.getId());

		TaskRunFactory.removeTaskRun(taskRun);
	}

	public List<TaskRun> retrieveTaskRuns(Task task) {
		List<TaskRun> taskRuns = new ArrayList<>();

		for (JSONObject responseJSONObject : retrieve()) {
			taskRuns.add(TaskRunFactory.newTaskRun(task, responseJSONObject));
		}

		return taskRuns;
	}

	public TaskRun updateTaskRun(TaskRun taskRun) {
		JSONObject responseJSONObject = update(taskRun.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return taskRun;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Task Run";
	}

}