/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeEntityRepository;
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

	protected BuildRunEntity getBuildRun() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (buildJSONObject == null) {
			throw new Exception("Missing build");
		}

		JSONObject parmetersJSONObject = buildJSONObject.optJSONObject(
			"parameters");

		if (parmetersJSONObject == null) {
			throw new Exception("Missing parameters from build");
		}

		String buildRunID = parmetersJSONObject.optString(
			"JETHR0_BUILD_RUN_ID");

		if ((buildRunID == null) || !buildRunID.matches("\\d+")) {
			return null;
		}

		BuildRunEntityRepository buildRunEntityRepository =
			getBuildRunRepository();

		return buildRunEntityRepository.getById(Long.valueOf(buildRunID));
	}

	protected BuildRunEntity.Result getBuildRunResult() throws Exception {
		JSONObject buildJSONObject = getBuildJSONObject();

		if (!buildJSONObject.has("result")) {
			throw new Exception("Missing result from build");
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
			throw new Exception("Missing computer from message");
		}

		return computerJSONObject;
	}

	protected URL getJenkinsBuildURL() throws Exception {
		return StringUtil.toURL(
			StringUtil.combine(
				getJenkinsURL(), "job/", getJobName(), "/", getBuildNumber()));
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

	protected JenkinsNodeEntity getJenkinsNodeEntity() throws Exception {
		JenkinsServerEntity jenkinsServerEntity = getJenkinsServerEntity();

		JenkinsNodeEntityRepository jenkinsNodeEntityRepository =
			getJenkinsNodeEntityRepository();

		JSONObject computerJSONObject = getComputerJSONObject();

		String computerName = computerJSONObject.getString("name");

		for (JenkinsNodeEntity jenkinsNodeEntity :
				jenkinsNodeEntityRepository.getAll()) {

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

		return jenkinsServerEntityRepository.getByURL(getJenkinsURL());
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

	protected JenkinsNodeEntity updateJenkinsNodeEntity() throws Exception {
		JSONObject computerJSONObject = getComputerJSONObject();

		computerJSONObject.put(
			"idle", !computerJSONObject.getBoolean("busy")
		).put(
			"offline", !computerJSONObject.getBoolean("online")
		);

		JenkinsNodeEntity jenkinsNodeEntity = getJenkinsNodeEntity();

		jenkinsNodeEntity.update(computerJSONObject);

		return jenkinsNodeEntity;
	}

}