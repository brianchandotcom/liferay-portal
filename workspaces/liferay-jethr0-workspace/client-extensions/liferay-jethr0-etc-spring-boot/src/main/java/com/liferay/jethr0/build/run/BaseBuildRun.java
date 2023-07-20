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

package com.liferay.jethr0.build.run;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.parameter.BuildParameter;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.time.Instant;

import java.util.Date;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRun extends BaseEntity implements BuildRun {

	@Override
	public Build getBuild() {
		return _build;
	}

	@Override
	public URL getBuildURL() {
		return _buildURL;
	}

	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public JSONObject getInvokeJSONObject() {
		JSONObject invokeJSONObject = new JSONObject();

		Build build = getBuild();

		invokeJSONObject.put("jobName", build.getJobName());

		JSONObject jobParametersJSONObject = new JSONObject();

		for (BuildParameter buildParameter : build.getBuildParameters()) {
			jobParametersJSONObject.put(
				buildParameter.getName(), buildParameter.getValue());
		}

		jobParametersJSONObject.put(
			"BUILD_ID", String.valueOf(build.getId())
		).put(
			"BUILD_RUN_ID", String.valueOf(getId())
		);

		Project project = build.getProject();

		if (project != null) {
			jobParametersJSONObject.put(
				"PROJECT_ID", String.valueOf(project.getId()));
		}

		invokeJSONObject.put("jobParameters", jobParametersJSONObject);

		return invokeJSONObject;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		Result result = getResult();
		State state = getState();

		jsonObject.put(
			"buildURL", getBuildURL()
		).put(
			"duration", getDuration()
		);

		if (result != null) {
			jsonObject.put("result", result.getJSONObject());
		}

		jsonObject.put("state", state.getJSONObject());

		return jsonObject;
	}

	@Override
	public Result getResult() {
		return _result;
	}

	@Override
	public State getState() {
		return _state;
	}

	@Override
	public boolean isBlocked() {
		if (getState() != State.QUEUED) {
			return false;
		}

		Instant instant = Instant.now();

		Date currentDate = new Date(instant.toEpochMilli());

		Date modifiedDate = getModifiedDate();

		long durationInQueue = Math.abs(
			currentDate.getTime() - modifiedDate.getTime());

		if (durationInQueue < _MAX_DURATION_IN_QUEUE) {
			return false;
		}

		return true;
	}

	@Override
	public void setBuild(Build build) {
		_build = build;
	}

	@Override
	public void setBuildURL(URL buildURL) {
		_buildURL = buildURL;
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
	public void setState(State state) {
		_state = state;
	}

	protected BaseBuildRun(JSONObject jsonObject) {
		super(jsonObject);

		String buildURL = jsonObject.optString("buildURL", "");

		if (!buildURL.isEmpty()) {
			_buildURL = StringUtil.toURL(jsonObject.optString("buildURL"));
		}

		_duration = jsonObject.optLong("duration");

		JSONObject resultJSONObject = jsonObject.optJSONObject("result");

		if (resultJSONObject != null) {
			_result = Result.get(resultJSONObject);
		}

		_state = State.get(jsonObject.getJSONObject("state"));
	}

	private static final long _MAX_DURATION_IN_QUEUE = 1000 * 60 * 2;

	private Build _build;
	private URL _buildURL;
	private long _duration;
	private Result _result;
	private State _state;

}