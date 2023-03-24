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
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildToTasksDALO extends BaseRelationshipDALO {

	public JSONObject createRelationship(Build build, Task task) {
		return create("/o/c/builds", build.getId(), task.getId());
	}

	public void deleteRelationship(Build build, Task task) {
		delete("/o/c/builds", build.getId(), task.getId());
	}

	public List<Task> retrieveTasks(Build build) {
		List<Task> tasks = new ArrayList<>();

		for (JSONObject responseJSONObject :
				retrieve("/o/c/builds", build.getId())) {

			tasks.add(TaskFactory.newTask(build, responseJSONObject));
		}

		return tasks;
	}

	public void updateRelationships(Build build) {
		List<Task> remoteTasks = retrieveTasks(build);

		for (Task task : build.getTasks()) {
			if (remoteTasks.contains(task)) {
				remoteTasks.removeAll(Collections.singletonList(task));

				continue;
			}

			createRelationship(build, task);
		}

		for (Task remoteTask : remoteTasks) {
			deleteRelationship(build, remoteTask);
		}
	}

	@Override
	protected String getObjectRelationshipName() {
		return "buildToTasks";
	}

}