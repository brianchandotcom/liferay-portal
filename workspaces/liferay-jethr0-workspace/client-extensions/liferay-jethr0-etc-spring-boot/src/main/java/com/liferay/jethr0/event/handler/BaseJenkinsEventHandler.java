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

		EventHandlerHelper eventHandlerHelper = getEventHandlerHelper();

		BuildRunRepository buildRunRepository =
			eventHandlerHelper.getBuildRunRepository();

		return buildRunRepository.getById(Long.valueOf(buildRunID));
	}

	protected BuildRun.Result getBuildRunResult() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

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

		JSONObject buildJSONObject = messageJSONObject.optJSONObject("jenkins");

		if (buildJSONObject == null) {
			throw new Exception("Missing 'jenkins' JSON object");
		}

		return buildJSONObject;
	}

	protected URL getJenkinsURL() throws Exception {
		JSONObject jenkinsJSONObject = getJenkinsJSONObject();

		return StringUtil.toURL(jenkinsJSONObject.optString("url"));
	}

	protected JSONObject getJobJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject buildJSONObject = messageJSONObject.optJSONObject("job");

		if (buildJSONObject == null) {
			throw new Exception("Missing 'job' JSON object");
		}

		return buildJSONObject;
	}

	protected String getJobName() throws Exception {
		JSONObject jobJSONObject = getJobJSONObject();

		return jobJSONObject.optString("name");
	}

}