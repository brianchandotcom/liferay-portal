/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Objects;

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
			throw new Exception("Missing 'duration' from build json");
		}

		return buildJSONObject.getLong("duration");
	}

	protected JSONObject getBuildJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject buildJSONObject = messageJSONObject.optJSONObject("build");

		if (buildJSONObject == null) {
			throw new Exception("Missing 'build' from message json");
		}

		return buildJSONObject;
	}

	protected long getBuildNumber() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (!buildJSONObject.has("number")) {
			throw new Exception("Missing 'number' from build json");
		}

		return buildJSONObject.optLong("number");
	}

	protected BuildRunEntity getBuildRun() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (buildJSONObject == null) {
			throw new Exception("Missing 'build' from message json");
		}

		JSONObject parmetersJSONObject = buildJSONObject.optJSONObject(
			"parameters");

		if (parmetersJSONObject == null) {
			throw new Exception("Missing 'parameters' from build json");
		}

		String buildRunID = parmetersJSONObject.optString(
			"JETHR0_BUILD_RUN_ID");

		if (StringUtil.isNullOrEmpty(buildRunID)) {
			throw new Exception(
				"Missing 'JETHR0_BUILD_RUN_ID' parameter from build json");
		}

		if (!buildRunID.matches("\\d+")) {
			throw new Exception(
				"Invalid 'JETHR0_BUILD_RUN_ID' parameter from build json");
		}

		BuildRunEntityRepository buildRunEntityRepository =
			getBuildRunRepository();

		BuildRunEntity buildRunEntity = buildRunEntityRepository.getById(
			Long.valueOf(buildRunID));

		if (buildRunEntity == null) {
			throw new Exception("Unable to find build run by id " + buildRunID);
		}

		return buildRunEntity;
	}

	protected BuildRunEntity.Result getBuildRunResult() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (!buildJSONObject.has("result")) {
			throw new Exception("Missing 'result' from build json");
		}

		String result = buildJSONObject.getString("result");

		if (result.equals("SUCCESS")) {
			return BuildRunEntity.Result.PASSED;
		}

		return BuildRunEntity.Result.FAILED;
	}

	protected JSONObject getComputerJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject computerJSONObject = messageJSONObject.optJSONObject(
			"computer");

		if (computerJSONObject == null) {
			throw new Exception("Missing 'computer' from message json");
		}

		return computerJSONObject;
	}

	protected URL getJenkinsBuildURL() throws Exception {
		return StringUtil.toURL(
			StringUtil.combine(
				getJenkinsURL(), "job/", getJobName(), "/", getBuildNumber(),
				"/"));
	}

	protected JSONObject getJenkinsJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject jenkinsJSONObject = messageJSONObject.optJSONObject(
			"jenkins");

		if (jenkinsJSONObject == null) {
			throw new Exception("Missing 'jenkins' from message json");
		}

		return jenkinsJSONObject;
	}

	protected JenkinsNodeEntity getJenkinsNodeEntity() throws Exception {
		JSONObject computerJSONObject = getComputerJSONObject();

		String computerName = computerJSONObject.optString("name");

		if (StringUtil.isNullOrEmpty(computerName)) {
			throw new Exception("Missing 'name' from computer json");
		}

		JenkinsServerEntity jenkinsServerEntity = getJenkinsServerEntity();

		for (JenkinsNodeEntity jenkinsNodeEntity :
				jenkinsServerEntity.getJenkinsNodeEntities()) {

			if (!Objects.equals(
					jenkinsServerEntity,
					jenkinsNodeEntity.getJenkinsServerEntity())) {

				continue;
			}

			if (Objects.equals(computerName, jenkinsNodeEntity.getName())) {
				return jenkinsNodeEntity;
			}
		}

		return null;
	}

	protected JenkinsServerEntity getJenkinsServerEntity() throws Exception {
		JenkinsServerEntityRepository jenkinsServerEntityRepository =
			getJenkinsServerEntityRepository();

		URL jenkinsURL = getJenkinsURL();

		JenkinsServerEntity jenkinsServerEntity =
			jenkinsServerEntityRepository.getByURL(jenkinsURL);

		if (jenkinsServerEntity == null) {
			throw new Exception(
				"Unable to find jenkins server by url " + jenkinsURL);
		}

		return jenkinsServerEntity;
	}

	protected URL getJenkinsURL() throws Exception {
		JSONObject jenkinsJSONObject = getJenkinsJSONObject();

		String jenkinsURLString = jenkinsJSONObject.optString("url");

		if (StringUtil.isNullOrEmpty(jenkinsURLString)) {
			throw new Exception("Missing 'url' from jenkins json");
		}

		try {
			return StringUtil.toURL(jenkinsURLString);
		}
		catch (Exception exception) {
			throw new Exception("Invalid 'url' from jenkins json", exception);
		}
	}

	protected JSONObject getJobJSONObject() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject jobJSONObject = messageJSONObject.optJSONObject("job");

		if (jobJSONObject == null) {
			throw new Exception("Missing 'job' from message json");
		}

		return jobJSONObject;
	}

	protected String getJobName() throws Exception {
		JSONObject jobJSONObject = getJobJSONObject();

		if (!jobJSONObject.has("name")) {
			throw new Exception("Missing 'name' from job json");
		}

		return jobJSONObject.optString("name");
	}

	protected JenkinsNodeEntity updateJenkinsNodeEntity() throws Exception {
		JSONObject computerJSONObject = getComputerJSONObject();

		computerJSONObject.put(
			"idle", !computerJSONObject.optBoolean("busy", true)
		).put(
			"offline", !computerJSONObject.optBoolean("online", false)
		);

		JenkinsNodeEntity jenkinsNodeEntity = getJenkinsNodeEntity();

		jenkinsNodeEntity.update(computerJSONObject);

		return jenkinsNodeEntity;
	}

}