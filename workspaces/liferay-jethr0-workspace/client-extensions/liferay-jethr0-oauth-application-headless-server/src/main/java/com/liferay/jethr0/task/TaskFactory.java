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

package com.liferay.jethr0.task;

import com.liferay.jethr0.build.Build;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TaskFactory {

	public static Task newTask(Build build, JSONObject jsonObject) {
		long id = jsonObject.getLong("id");

		Task task = null;

		synchronized (_tasks) {
			if (_tasks.containsKey(id)) {
				return _tasks.get(id);
			}

			task = new DefaultTask(build, jsonObject);

			_tasks.put(task.getId(), task);
		}

		return task;
	}

	public static void removeTask(Task task) {
		if (task == null) {
			return;
		}

		synchronized (_tasks) {
			_tasks.remove(task.getId());
		}
	}

	private static final Map<Long, Task> _tasks = new HashMap<>();

}