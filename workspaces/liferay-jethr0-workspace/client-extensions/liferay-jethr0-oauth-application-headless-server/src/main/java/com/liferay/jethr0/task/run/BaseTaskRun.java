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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTaskRun implements TaskRun {

	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public JSONObject getJSONObject() {
		Result result = getResult();
		Task task = getTask();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"duration", getDuration()
		).put(
			"id", getId()
		).put(
			"r_taskToTaskRuns_c_taskId", task.getId()
		).put(
			"result", result.getJSONObject()
		);

		return jsonObject;
	}

	@Override
	public Result getResult() {
		return _result;
	}

	@Override
	public Task getTask() {
		return _task;
	}

	@Override
	public void setDuration(long duration) {
		_duration = duration;
	}

	@Override
	public void setResult(Result result) {
		_result = result;
	}

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseTaskRun(Task task, JSONObject jsonObject) {
		_task = task;

		_duration = jsonObject.getLong("duration");
		_result = Result.get(jsonObject.getJSONObject("result"));
		_id = jsonObject.getLong("id");
	}

	private long _duration;
	private final long _id;
	private Result _result;
	private final Task _task;

}