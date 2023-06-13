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

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.build.repository.BuildRunRepository;
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJenkinsEventHandler extends BaseEventHandler {

	protected BaseJenkinsEventHandler(
		EventHandlerHelper eventHandlerHelper, JSONObject messageJSONObject) {

		super(eventHandlerHelper, messageJSONObject);
	}

	protected long getBuildDuration() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (!buildJSONObject.has("duration")) {
			throw new Exception("Missing 'duration' from Build JSON object");
		}

		return buildJSONObject.getLong("duration");
	}

	protected JSONObject getBuildJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject buildJSONObject = messageJSONObject.optJSONObject("build");

		if (buildJSONObject == null) {
			throw new Exception("Missing 'build' JSON object");
		}

		return buildJSONObject;
	}

	protected long getBuildNumber() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (!buildJSONObject.has("number")) {
			throw new Exception("Missing 'number' from Build JSON object");
		}

		return buildJSONObject.optLong("number");
	}

	protected BuildRun getBuildRun() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (buildJSONObject == null) {
			throw new Exception("Invalid Build JSON object");
		}

		JSONObject parmetersJSONObject = buildJSONObject.optJSONObject(
			"parameters");

		if (parmetersJSONObject == null) {
			throw new Exception("Missing Build 'parameters'");
		}

		String buildRunID = parmetersJSONObject.optString("BUILD_RUN_ID");

		if ((buildRunID == null) || !buildRunID.matches("\\d+")) {
			return null;
		}

		BuildRunRepository buildRunRepository = getBuildRunRepository();

		return buildRunRepository.getById(Long.valueOf(buildRunID));
	}

	protected BuildRun.Result getBuildRunResult() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (!buildJSONObject.has("result")) {
			throw new Exception("Missing 'result' from Build JSON object");
		}

		String result = buildJSONObject.getString("result");

		if (result.equals("SUCCESS")) {
			return BuildRun.Result.PASSED;
		}

		return BuildRun.Result.FAILED;
	}

	protected URL getBuildURL() throws Exception {
		return StringUtil.toURL(
			StringUtil.combine(
				getJenkinsURL(), "job/", getJobName(), "/", getBuildNumber()));
	}

	protected JSONObject getJenkinsJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject jenkinsJSONObject = messageJSONObject.optJSONObject(
			"jenkins");

		if (jenkinsJSONObject == null) {
			throw new Exception("Missing 'jenkins' JSON object");
		}

		return jenkinsJSONObject;
	}

	protected URL getJenkinsURL() throws Exception {
		JSONObject jenkinsJSONObject = getJenkinsJSONObject();

		if (!jenkinsJSONObject.has("url")) {
			throw new Exception("Missing 'url' from Jenkins JSON object");
		}

		return StringUtil.toURL(jenkinsJSONObject.optString("url"));
	}

	protected JSONObject getJobJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject jobJSONObject = messageJSONObject.optJSONObject("job");

		if (jobJSONObject == null) {
			throw new Exception("Missing 'job' JSON object");
		}

		return jobJSONObject;
	}

	protected String getJobName() throws Exception {
		JSONObject jobJSONObject = getJobJSONObject();

		if (!jobJSONObject.has("name")) {
			throw new Exception("Missing 'name' from Job JSON object");
		}

		return jobJSONObject.optString("name");
	}

}