/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.task.run;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.task.TaskEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTaskRunEntity extends BaseEntity implements TaskRunEntity {

	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		Result result = getResult();
		TaskEntity taskEntity = getTaskEntity();

		jsonObject.put(
			"duration", getDuration()
		).put(
			"r_taskToTaskRuns_c_taskId", taskEntity.getId()
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
	public TaskEntity getTaskEntity() {
		return _taskEntity;
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
	public void setTaskEntity(TaskEntity taskEntity) {
		_taskEntity = taskEntity;
	}

	protected BaseTaskRunEntity(JSONObject jsonObject) {
		super(jsonObject);

		_duration = jsonObject.getLong("duration");
		_result = Result.get(jsonObject.getJSONObject("result"));
	}

	private long _duration;
	private Result _result;
	private TaskEntity _taskEntity;

}