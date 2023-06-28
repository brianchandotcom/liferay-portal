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
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeRepository;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJenkinsEventHandler extends BaseEventHandler {

	protected BaseJenkinsEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	protected long getBuildDuration() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (!buildJSONObject.has("duration")) {
			throw new Exception("Missing duration from build");
		}

		return buildJSONObject.getLong("duration");
	}

	protected JSONObject getBuildJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject buildJSONObject = messageJSONObject.optJSONObject("build");

		if (buildJSONObject == null) {
			throw new Exception("Missing build from message");
		}

		return buildJSONObject;
	}

	protected long getBuildNumber() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (!buildJSONObject.has("number")) {
			throw new Exception("Missing number from build");
		}

		return buildJSONObject.optLong("number");
	}

	protected BuildRun getBuildRun() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (buildJSONObject == null) {
			throw new Exception("Missing build");
		}

		JSONObject parmetersJSONObject = buildJSONObject.optJSONObject(
			"parameters");

		if (parmetersJSONObject == null) {
			throw new Exception("Missing parameters from build");
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
			throw new Exception("Missing result from build");
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

	protected JSONObject getComputerJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject computerJSONObject = messageJSONObject.optJSONObject(
			"computer");

		if (computerJSONObject == null) {
			throw new Exception("Missing computer from message");
		}

		return computerJSONObject;
	}

	protected JSONObject getJenkinsJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject jenkinsJSONObject = messageJSONObject.optJSONObject(
			"jenkins");

		if (jenkinsJSONObject == null) {
			throw new Exception("Missing Jenkins from message");
		}

		return jenkinsJSONObject;
	}

	protected JenkinsNode getJenkinsNode() throws Exception {
		JSONObject computerJSONObject = getComputerJSONObject();

		JenkinsNodeRepository jenkinsNodeRepository =
			getJenkinsNodeRepository();

		return jenkinsNodeRepository.get(computerJSONObject.getString("name"));
	}

	protected URL getJenkinsURL() throws Exception {
		JSONObject jenkinsJSONObject = getJenkinsJSONObject();

		if (!jenkinsJSONObject.has("url")) {
			throw new Exception("Missing url from Jenkins");
		}

		return StringUtil.toURL(jenkinsJSONObject.optString("url"));
	}

	protected JSONObject getJobJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject jobJSONObject = messageJSONObject.optJSONObject("job");

		if (jobJSONObject == null) {
			throw new Exception("Missing job from message");
		}

		return jobJSONObject;
	}

	protected String getJobName() throws Exception {
		JSONObject jobJSONObject = getJobJSONObject();

		if (!jobJSONObject.has("name")) {
			throw new Exception("Missing name from job");
		}

		return jobJSONObject.optString("name");
	}

	protected JenkinsNode updateJenkinsNode() throws Exception {
		JSONObject computerJSONObject = getComputerJSONObject();

		computerJSONObject.put(
			"idle", !computerJSONObject.getBoolean("busy")
		).put(
			"offline", !computerJSONObject.getBoolean("online")
		);

		JenkinsNode jenkinsNode = getJenkinsNode();

		jenkinsNode.update(computerJSONObject);

		return jenkinsNode;
	}

}