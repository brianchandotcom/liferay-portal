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

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.task.Task;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTaskRun extends BaseEntity implements TaskRun {

	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		Result result = getResult();
		Task task = getTask();

		jsonObject.put(
			"duration", getDuration()
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
	public void setTask(Task task) {
		_task = task;
	}

	protected BaseTaskRun(JSONObject jsonObject) {
		super(jsonObject);

		_duration = jsonObject.getLong("duration");
		_result = Result.get(jsonObject.getJSONObject("result"));
	}

	private long _duration;
	private Result _result;
	private Task _task;

}