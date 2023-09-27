/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.run;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.parameter.BuildParameterEntity;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.time.Instant;

import java.util.Date;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRunEntity
	extends BaseEntity implements BuildRunEntity {

	@Override
	public BuildEntity getBuildEntity() {
		return _buildEntity;
	}

	@Override
	public long getBuildEntityId() {
		return _buildEntityId;
	}

	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public JSONObject getInvokeJSONObject(JenkinsNodeEntity jenkinsNodeEntity) {
		JSONObject invokeJSONObject = new JSONObject();

		BuildEntity buildEntity = getBuildEntity();

		invokeJSONObject.put("jobName", buildEntity.getJenkinsJobName());

		JSONObject jobParametersJSONObject = new JSONObject();

		for (BuildParameterEntity buildParameterEntity :
				buildEntity.getBuildParameterEntities()) {

			jobParametersJSONObject.put(
				buildParameterEntity.getName(),
				buildParameterEntity.getValue());
		}

		jobParametersJSONObject.put(
			"JETHR0_BUILD_ID", String.valueOf(buildEntity.getId())
		).put(
			"JETHR0_BUILD_RUN_ID", String.valueOf(getId())
		);

		JobEntity jobEntity = buildEntity.getJobEntity();

		if (jobEntity != null) {
			jobParametersJSONObject.put(
				"JETHR0_JOB_ID", String.valueOf(jobEntity.getId()));
		}

		invokeJSONObject.put("jobParameters", jobParametersJSONObject);

		if (jenkinsNodeEntity != null) {
			invokeJSONObject.put(
				"jenkinsNode", jenkinsNodeEntity.getJSONObject());
		}

		return invokeJSONObject;
	}

	@Override
	public URL getJenkinsBuildURL() {
		return _jenkinsBuildURL;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		Result result = getResult();
		State state = getState();

		jsonObject.put(
			"duration", getDuration()
		).put(
			"jenkinsBuildURL", getJenkinsBuildURL()
		).put(
			"r_buildToBuildRuns_c_buildId", getBuildEntityId()
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
	public void setBuildEntity(BuildEntity buildEntity) {
		_buildEntity = buildEntity;

		if (_buildEntity != null) {
			_buildEntityId = buildEntity.getId();
		}
		else {
			_buildEntityId = 0;
		}
	}

	@Override
	public void setDuration(long duration) {
		_duration = duration;
	}

	@Override
	public void setJenkinsBuildURL(URL jenkinsBuildURL) {
		_jenkinsBuildURL = jenkinsBuildURL;
	}

	@Override
	public void setResult(Result result) {
		_result = result;
	}

	@Override
	public void setState(State state) {
		_state = state;
	}

	protected BaseBuildRunEntity(JSONObject jsonObject) {
		super(jsonObject);

		_buildEntityId = jsonObject.optLong("r_buildToBuildRuns_c_buildId");

		_duration = jsonObject.optLong("duration");

		String jenkinsBuildURL = jsonObject.optString("jenkinsBuildURL", "");

		if (!jenkinsBuildURL.isEmpty()) {
			_jenkinsBuildURL = StringUtil.toURL(
				jsonObject.optString("jenkinsBuildURL"));
		}

		JSONObject resultJSONObject = jsonObject.optJSONObject("result");

		if (resultJSONObject != null) {
			_result = Result.get(resultJSONObject);
		}

		_state = State.get(jsonObject.getJSONObject("state"));
	}

	private static final long _MAX_DURATION_IN_QUEUE = 1000 * 60 * 2;

	private BuildEntity _buildEntity;
	private long _buildEntityId;
	private long _duration;
	private URL _jenkinsBuildURL;
	private Result _result;
	private State _state;

}