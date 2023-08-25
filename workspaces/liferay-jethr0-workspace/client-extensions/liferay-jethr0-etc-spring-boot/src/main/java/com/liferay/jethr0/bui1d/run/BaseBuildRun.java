/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.run;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.parameter.BuildParameter;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
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
	public long getBuildId() {
		return _buildId;
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
	public JSONObject getInvokeJSONObject(JenkinsNodeEntity jenkinsNodeEntity) {
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

		if (jenkinsNodeEntity != null) {
			invokeJSONObject.put(
				"jenkinsNode", jenkinsNodeEntity.getJSONObject());
		}

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
		).put(
			"r_buildToBuildRuns_c_buildId", getBuildId()
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

		if (_build != null) {
			_buildId = build.getId();
		}
		else {
			_buildId = 0;
		}
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

		_buildId = jsonObject.optLong("r_buildToBuildRuns_c_buildId");

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
	private long _buildId;
	private URL _buildURL;
	private long _duration;
	private Result _result;
	private State _state;

}