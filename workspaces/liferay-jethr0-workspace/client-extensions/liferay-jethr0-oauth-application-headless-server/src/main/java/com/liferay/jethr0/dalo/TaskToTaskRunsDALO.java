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
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TaskToTaskRunsDALO extends BaseRelationshipDALO {

	public JSONObject createRelationship(Task task, TaskRun taskRun) {
		return create("/o/c/tasks", task.getId(), taskRun.getId());
	}

	public void deleteRelationship(Task task, TaskRun taskRun) {
		delete("/o/c/tasks", task.getId(), taskRun.getId());
	}

	public List<TaskRun> retrieveTaskRuns(Task task) {
		List<TaskRun> taskRuns = new ArrayList<>();

		for (JSONObject jsonObject : retrieve("/o/c/tasks", task.getId())) {
			taskRuns.add(TaskRunFactory.newTaskRun(task, jsonObject));
		}

		return taskRuns;
	}

	public void updateRelationships(Task task) {
		List<TaskRun> remoteTaskRuns = retrieveTaskRuns(task);

		for (TaskRun taskRun : task.getTaskRuns()) {
			if (remoteTaskRuns.contains(taskRun)) {
				remoteTaskRuns.removeAll(Collections.singletonList(taskRun));

				continue;
			}

			createRelationship(task, taskRun);
		}

		for (TaskRun remoteTaskRun : remoteTaskRuns) {
			deleteRelationship(task, remoteTaskRun);
		}
	}

	@Override
	protected String getObjectRelationshipName() {
		return "taskToTaskRuns";
	}

}