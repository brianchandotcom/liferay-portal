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
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRun implements BuildRun {

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
	public long getId() {
		return _id;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		Result result = getResult();
		State state = getState();

		jsonObject.put(
			"buildURL", getBuildURL()
		).put(
			"duration", getDuration()
		).put(
			"id", getId()
		).put(
			"result", result.getJSONObject()
		).put(
			"state", state.getJSONObject()
		);

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

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseBuildRun(Build build, JSONObject jsonObject) {
		_build = build;

		_buildURL = StringUtil.toURL(jsonObject.getString("buildURL"));
		_duration = jsonObject.getLong("duration");
		_id = jsonObject.getLong("id");
		_result = Result.get(jsonObject.getJSONObject("result"));
		_state = State.get(jsonObject.getJSONObject("state"));
	}

	private final Build _build;
	private URL _buildURL;
	private long _duration;
	private final long _id;
	private Result _result;
	private State _state;

}