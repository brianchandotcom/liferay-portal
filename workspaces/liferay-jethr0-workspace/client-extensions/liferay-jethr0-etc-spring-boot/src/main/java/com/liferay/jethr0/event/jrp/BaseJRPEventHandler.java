/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jrp;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.event.BaseEventHandler;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJRPEventHandler extends BaseEventHandler {

	protected BaseJRPEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	protected JSONObject getBuildJSONObject() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject buildJSONObject = messageJSONObject.optJSONObject("build");

		if (buildJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"build\" from message JSON");
		}

		return validateBuildJSONObject(buildJSONObject);
	}

	protected JSONObject getJenkinsCohortJSONObject()
		throws InvalidJSONException {

		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject jenkinsCohortJSONObject = messageJSONObject.optJSONObject(
			"jenkinsCohort");

		if (jenkinsCohortJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"jenkinsCohort\" from message JSON");
		}

		return validateJenkinsCohortJSONObject(jenkinsCohortJSONObject);
	}

	protected JobEntity getJobEntity(JSONObject jobJSONObject)
		throws InvalidJSONException {

		if (jobJSONObject == null) {
			throw new InvalidJSONException("Missing job JSON");
		}

		long jobEntityId = jobJSONObject.optLong("id");

		if (jobEntityId <= 0) {
			throw new InvalidJSONException("Missing \"id\" from job JSON");
		}

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		return jobEntityRepository.getById(jobEntityId);
	}

	protected JSONObject getJobJSONObject() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject jobJSONObject = messageJSONObject.optJSONObject("job");

		if (jobJSONObject == null) {
			throw new InvalidJSONException("Missing \"job\" from message JSON");
		}

		return validateJobJSONObject(jobJSONObject);
	}

	protected JSONObject validateBuildJSONObject(JSONObject buildJSONObject)
		throws InvalidJSONException {

		if (buildJSONObject == null) {
			throw new InvalidJSONException("Missing build JSON");
		}

		long buildId = buildJSONObject.optLong("id");

		if (buildId > 0) {
			return buildJSONObject;
		}

		String jenkinsJobName = buildJSONObject.optString("jenkinsJobName");

		if (jenkinsJobName.isEmpty()) {
			throw new InvalidJSONException(
				"Missing \"jenkinsJobName\" from build JSON");
		}

		String name = buildJSONObject.optString("name");

		if (name.isEmpty()) {
			throw new InvalidJSONException("Missing \"name\" from build JSON");
		}

		BuildEntity.State state = BuildEntity.State.get(
			buildJSONObject.opt("state"));

		if (state == null) {
			state = BuildEntity.State.OPENED;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"jenkinsJobName", jenkinsJobName
		).put(
			"name", name
		).put(
			"parameters", buildJSONObject.optString("parameters")
		).put(
			"state", state.getJSONObject()
		);

		return jsonObject;
	}

	protected JSONArray validateBuildsJSONArray(JSONArray buildsJSONArray)
		throws InvalidJSONException {

		JSONArray jsonArray = new JSONArray();

		if ((buildsJSONArray != null) && !buildsJSONArray.isEmpty()) {
			for (int i = 0; i < buildsJSONArray.length(); i++) {
				jsonArray.put(
					validateBuildJSONObject(buildsJSONArray.optJSONObject(i)));
			}
		}

		return jsonArray;
	}

	protected JSONObject validateJenkinsCohortJSONObject(
			JSONObject jenkinsCohortJSONObject)
		throws InvalidJSONException {

		if (jenkinsCohortJSONObject == null) {
			throw new InvalidJSONException("Missing Jenkins cohort JSON");
		}

		if (jenkinsCohortJSONObject.has("id")) {
			return jenkinsCohortJSONObject;
		}

		String name = jenkinsCohortJSONObject.optString("name");

		if (StringUtil.isNullOrEmpty(name)) {
			throw new InvalidJSONException(
				"Missing \"name\" from Jenkins cohort JSON");
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"jenkinsServers",
			validateJenkinsServersJSONArray(
				jenkinsCohortJSONObject.optJSONArray("jenkinsServers"))
		).put(
			"name", name
		);

		return jsonObject;
	}

	protected JSONArray validateJenkinsCohortsJSONArray(
			JSONArray jenkinsCohortsJSONArray)
		throws InvalidJSONException {

		JSONArray jsonArray = new JSONArray();

		if ((jenkinsCohortsJSONArray != null) &&
			!jenkinsCohortsJSONArray.isEmpty()) {

			for (int i = 0; i < jenkinsCohortsJSONArray.length(); i++) {
				jsonArray.put(
					validateJenkinsCohortJSONObject(
						jenkinsCohortsJSONArray.optJSONObject(i)));
			}
		}

		return jsonArray;
	}

	protected JSONObject validateJenkinsServerJSONObject(
			JSONObject jenkinsServerJSONObject)
		throws InvalidJSONException {

		if (jenkinsServerJSONObject == null) {
			throw new InvalidJSONException("Missing Jenkins server JSON");
		}

		String jenkinsUserName = jenkinsServerJSONObject.optString(
			"jenkinsUserName");

		if (StringUtil.isNullOrEmpty(jenkinsUserName)) {
			throw new InvalidJSONException(
				"Missing \"jenkinsUserName\" from Jenkins server JSON");
		}

		String jenkinsUserPassword = jenkinsServerJSONObject.optString(
			"jenkinsUserPassword");

		if (StringUtil.isNullOrEmpty(jenkinsUserPassword)) {
			throw new InvalidJSONException(
				"Missing \"jenkinsUserPassword\" from Jenkins server JSON");
		}

		String urlString = jenkinsServerJSONObject.optString("url");

		if (StringUtil.isNullOrEmpty(urlString)) {
			throw new InvalidJSONException(
				"Missing \"url\" from Jenkins server JSON");
		}

		URL url = StringUtil.toURL(urlString);

		if (url == null) {
			throw new InvalidJSONException(
				"Invalid \"url\" from Jenkins server JSON");
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"jenkinsUserName", jenkinsUserName
		).put(
			"jenkinsUserPassword", jenkinsUserPassword
		).put(
			"name", jenkinsServerJSONObject.optString("name")
		).put(
			"url", String.valueOf(url)
		);

		return jsonObject;
	}

	protected JSONArray validateJenkinsServersJSONArray(
			JSONArray jenkinsServersJSONArray)
		throws InvalidJSONException {

		JSONArray jsonArray = new JSONArray();

		if ((jenkinsServersJSONArray != null) &&
			!jenkinsServersJSONArray.isEmpty()) {

			for (int i = 0; i < jenkinsServersJSONArray.length(); i++) {
				jsonArray.put(
					validateJenkinsServerJSONObject(
						jenkinsServersJSONArray.optJSONObject(i)));
			}
		}

		return jsonArray;
	}

	protected JSONObject validateJobJSONObject(JSONObject jobJSONObject)
		throws InvalidJSONException {

		if (jobJSONObject == null) {
			throw new InvalidJSONException("Missing job");
		}

		if (jobJSONObject.has("id")) {
			return jobJSONObject;
		}

		String name = jobJSONObject.optString("name");

		if (name.isEmpty()) {
			throw new InvalidJSONException("Missing \"name\" from job JSON");
		}

		int priority = jobJSONObject.optInt("priority");

		if (priority <= 0) {
			throw new InvalidJSONException(
				"Missing \"priority\" from job JSON");
		}

		JobEntity.State state = JobEntity.State.get(jobJSONObject.opt("state"));

		if (state == null) {
			state = JobEntity.State.OPENED;
		}

		JobEntity.Type type = JobEntity.Type.get(jobJSONObject.opt("type"));

		if (type == null) {
			throw new InvalidJSONException(
				"Job type is not one of the following: " +
					JobEntity.Type.getKeys());
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"builds",
			validateBuildsJSONArray(jobJSONObject.optJSONArray("builds"))
		).put(
			"jenkinsCohorts",
			validateJenkinsCohortsJSONArray(
				jobJSONObject.optJSONArray("jenkinsCohorts"))
		).put(
			"name", name
		).put(
			"priority", priority
		).put(
			"state", state.getJSONObject()
		).put(
			"type", type.getJSONObject()
		);

		return jsonObject;
	}

}