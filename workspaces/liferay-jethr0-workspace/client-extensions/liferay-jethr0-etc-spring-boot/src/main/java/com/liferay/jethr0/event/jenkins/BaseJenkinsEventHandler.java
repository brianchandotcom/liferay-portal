/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jenkins;

import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.event.BaseEventHandler;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJenkinsEventHandler extends BaseEventHandler {

	protected BaseJenkinsEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	protected long getBuildDuration() throws InvalidJSONException {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (!buildJSONObject.has("duration")) {
			throw new InvalidJSONException(
				"Missing \"duration\" from build JSON");
		}

		return buildJSONObject.getLong("duration");
	}

	protected JSONObject getBuildJSONObject() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject buildJSONObject = messageJSONObject.optJSONObject("build");

		if (buildJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"build\" from message JSON");
		}

		return buildJSONObject;
	}

	protected long getBuildNumber() throws InvalidJSONException {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (!buildJSONObject.has("number")) {
			throw new InvalidJSONException(
				"Missing \"number\" from build JSON");
		}

		return buildJSONObject.optLong("number");
	}

	protected BuildRunEntity getBuildRun() throws InvalidJSONException {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (buildJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"build\" from message JSON");
		}

		JSONObject parmetersJSONObject = buildJSONObject.optJSONObject(
			"parameters");

		if (parmetersJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"parameters\" from build JSON");
		}

		String buildRunID = parmetersJSONObject.optString(
			"JETHR0_BUILD_RUN_ID");

		if (StringUtil.isNullOrEmpty(buildRunID)) {
			throw new InvalidJSONException(
				"Missing \"JETHR0_BUILD_RUN_ID\" parameter from build JSON");
		}

		if (!buildRunID.matches("\\d+")) {
			throw new InvalidJSONException(
				"Invalid \"JETHR0_BUILD_RUN_ID\" parameter from build JSON");
		}

		BuildRunEntityRepository buildRunEntityRepository =
			Jethr0ContextUtil.getBuildRunEntityRepository();

		BuildRunEntity buildRunEntity = buildRunEntityRepository.getById(
			Long.valueOf(buildRunID));

		if (buildRunEntity == null) {
			throw new InvalidJSONException(
				"Unable to find build run by ID " + buildRunID);
		}

		return buildRunEntity;
	}

	protected BuildRunEntity.Result getBuildRunResult()
		throws InvalidJSONException {

		JSONObject buildJSONObject = getBuildJSONObject();

		if (!buildJSONObject.has("result")) {
			throw new InvalidJSONException(
				"Missing \"result\" from build JSON");
		}

		String result = buildJSONObject.getString("result");

		if (result.equals("SUCCESS")) {
			return BuildRunEntity.Result.PASSED;
		}

		return BuildRunEntity.Result.FAILED;
	}

	protected JSONObject getComputerJSONObject() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject computerJSONObject = messageJSONObject.optJSONObject(
			"computer");

		if (computerJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"computer\" from message JSON");
		}

		return computerJSONObject;
	}

	protected URL getJenkinsBuildURL() throws InvalidJSONException {
		return StringUtil.toURL(
			StringUtil.combine(
				getJenkinsURL(), "job/", getJobName(), "/", getBuildNumber(),
				"/"));
	}

	protected JSONObject getJenkinsJSONObject() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject jenkinsJSONObject = messageJSONObject.optJSONObject(
			"jenkins");

		if (jenkinsJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"jenkins\" from message JSON");
		}

		return jenkinsJSONObject;
	}

	protected JenkinsNodeEntity getJenkinsNodeEntity()
		throws InvalidJSONException {

		JSONObject computerJSONObject = getComputerJSONObject();

		String computerName = computerJSONObject.optString("name");

		if (StringUtil.isNullOrEmpty(computerName)) {
			throw new InvalidJSONException(
				"Missing \"name\" from computer JSON");
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

	protected JenkinsServerEntity getJenkinsServerEntity()
		throws InvalidJSONException {

		JenkinsServerEntityRepository jenkinsServerEntityRepository =
			Jethr0ContextUtil.getJenkinsServerEntityRepository();

		URL jenkinsURL = getJenkinsURL();

		JenkinsServerEntity jenkinsServerEntity =
			jenkinsServerEntityRepository.createByURL(jenkinsURL);

		if (jenkinsServerEntity == null) {
			throw new InvalidJSONException(
				"Unable to find Jenkins server by URL " + jenkinsURL);
		}

		return jenkinsServerEntity;
	}

	protected URL getJenkinsURL() throws InvalidJSONException {
		JSONObject jenkinsJSONObject = getJenkinsJSONObject();

		String jenkinsURLString = jenkinsJSONObject.optString("url");

		if (StringUtil.isNullOrEmpty(jenkinsURLString)) {
			throw new InvalidJSONException("Missing \"url\" from Jenkins JSON");
		}

		try {
			return StringUtil.toURL(jenkinsURLString);
		}
		catch (Exception exception) {
			throw new InvalidJSONException(
				"Invalid \"url\" from Jenkins JSON", exception);
		}
	}

	protected JSONObject getJobJSONObject() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject jobJSONObject = messageJSONObject.optJSONObject("job");

		if (jobJSONObject == null) {
			throw new InvalidJSONException("Missing \"job\" from message JSON");
		}

		return jobJSONObject;
	}

	protected String getJobName() throws InvalidJSONException {
		JSONObject jobJSONObject = getJobJSONObject();

		if (!jobJSONObject.has("name")) {
			throw new InvalidJSONException("Missing \"name\" from job JSON");
		}

		return jobJSONObject.optString("name");
	}

	protected JenkinsNodeEntity updateJenkinsNodeEntity()
		throws InvalidJSONException {

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