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

package com.liferay.jethr0.builds;

import com.liferay.jethr0.builds.parameter.BuildParameter;
import com.liferay.jethr0.project.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuild implements Build {

	public void addBuildParameter(BuildParameter buildParameter) {
		addBuildParameters(Arrays.asList(buildParameter));
	}

	public void addBuildParameters(List<BuildParameter> buildParameters) {
		for (BuildParameter buildParameter : buildParameters) {
			if (_buildParameters.contains(buildParameter)) {
				continue;
			}

			_buildParameters.add(buildParameter);
		}
	}

	@Override
	public String getBuildName() {
		return _buildName;
	}

	public List<BuildParameter> getBuildParameters() {
		return _buildParameters;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public String getJobName() {
		return _jobName;
	}

	@Override
	public JSONObject getJSONObject() {
		State state = getState();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"buildName", getBuildName()
		).put(
			"id", getId()
		).put(
			"jobName", getJobName()
		).put(
			"state", state.getJSONObject()
		);

		return jsonObject;
	}

	@Override
	public Project getProject() {
		return _project;
	}

	@Override
	public State getState() {
		return _state;
	}

	public void removeBuildParameter(BuildParameter buildParameter) {
		_buildParameters.remove(buildParameter);
	}

	public void removeBuildParameters(List<BuildParameter> buildParameters) {
		_buildParameters.removeAll(buildParameters);
	}

	@Override
	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	@Override
	public void setState(State state) {
		_state = state;
	}

	protected BaseBuild(Project project, JSONObject jsonObject) {
		_project = project;

		_buildName = jsonObject.getString("buildName");
		_id = jsonObject.getLong("id");
		_jobName = jsonObject.getString("jobName");
		_state = State.get(jsonObject.getJSONObject("state"));
	}

	private final String _buildName;
	private final List<BuildParameter> _buildParameters = new ArrayList<>();
	private final long _id;
	private String _jobName;
	private final Project _project;
	private State _state;

}