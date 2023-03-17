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

package com.liferay.jethr0.task.run;

import com.liferay.jethr0.task.Task;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TaskRunFactory {

	public static TaskRun newTaskRun(Task task, JSONObject jsonObject) {
		long id = jsonObject.getLong("id");

		TaskRun taskRun = null;

		synchronized (_taskRuns) {
			if (_taskRuns.containsKey(id)) {
				return _taskRuns.get(id);
			}

			taskRun = new DefaultTaskRun(task, jsonObject);

			_taskRuns.put(taskRun.getId(), taskRun);
		}

		return taskRun;
	}

	public static void removeTaskRun(TaskRun taskRun) {
		if (taskRun == null) {
			return;
		}

		synchronized (_taskRuns) {
			_taskRuns.remove(taskRun.getId());
		}
	}

	private static final Map<Long, TaskRun> _taskRuns = new HashMap<>();

}