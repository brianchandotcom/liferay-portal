/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseObjectEventHandler extends BaseEventHandler {

	protected BaseObjectEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	protected JobEntity getJobEntity(JSONObject jobJSONObject)
		throws Exception {

		if (jobJSONObject == null) {
			throw new Exception("Missing job");
		}

		long jobEntityId = jobJSONObject.optLong("id");

		if (jobEntityId <= 0) {
			throw new Exception("Missing ID from job");
		}

		JobEntityRepository jobEntityRepository = getJobEntityRepository();

		JobEntity jobEntity = jobEntityRepository.getById(jobEntityId);

		BuildEntityRepository buildEntityRepository = getBuildRepository();

		buildEntityRepository.getAll(jobEntity);

		return jobEntity;
	}

	protected JSONObject validateBuildJSONObject(JSONObject buildJSONObject)
		throws Exception {

		if (buildJSONObject == null) {
			throw new Exception("Missing build");
		}

		String jenkinsJobName = buildJSONObject.optString("jenkinsJobName");

		if (jenkinsJobName.isEmpty()) {
			throw new Exception("Missing jenkins job name from build");
		}

		String name = buildJSONObject.optString("name");

		if (name.isEmpty()) {
			throw new Exception("Missing name from build");
		}

		BuildEntity.State state = BuildEntity.State.getByKey(
			buildJSONObject.optString("state"));

		if (state == null) {
			state = BuildEntity.State.OPENED;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"jenkinsJobName", jenkinsJobName
		).put(
			"name", name
		).put(
			"parameters", buildJSONObject.optJSONObject("parameters")
		).put(
			"state", state.getJSONObject()
		);

		return jsonObject;
	}

	protected JSONArray validateBuildsJSONArray(JSONArray buildsJSONArray)
		throws Exception {

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
		throws Exception {

		if (jenkinsCohortJSONObject == null) {
			throw new Exception("Missing Jenkins cohort");
		}

		if (jenkinsCohortJSONObject.has("id")) {
			return jenkinsCohortJSONObject;
		}

		String name = jenkinsCohortJSONObject.optString("name");

		if (StringUtil.isNullOrEmpty(name)) {
			throw new Exception("Missing name from Jenkins cohort");
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
		throws Exception {

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
		throws Exception {

		if (jenkinsServerJSONObject == null) {
			throw new Exception("Missing Jenkins server");
		}

		String jenkinsUserName = jenkinsServerJSONObject.optString(
			"jenkinsUserName");

		if (StringUtil.isNullOrEmpty(jenkinsUserName)) {
			throw new Exception(
				"Missing Jenkins user name from Jenkins server");
		}

		String jenkinsUserPassword = jenkinsServerJSONObject.optString(
			"jenkinsUserPassword");

		if (StringUtil.isNullOrEmpty(jenkinsUserPassword)) {
			throw new Exception(
				"Missing Jenkins user password from Jenkins server");
		}

		URL url = StringUtil.toURL(jenkinsServerJSONObject.optString("url"));

		if (url == null) {
			throw new Exception("Missing url from Jenkins server");
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
		throws Exception {

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
		throws Exception {

		if (jobJSONObject == null) {
			throw new Exception("Missing job");
		}

		if (jobJSONObject.has("id")) {
			return jobJSONObject;
		}

		String name = jobJSONObject.optString("name");

		if (name.isEmpty()) {
			throw new Exception("Missing name from job");
		}

		int priority = jobJSONObject.optInt("priority");

		if (priority <= 0) {
			throw new Exception("Missing priority from job");
		}

		JobEntity.State state = JobEntity.State.getByKey(
			jobJSONObject.optString("state"));

		if (state == null) {
			state = JobEntity.State.OPENED;
		}

		JobEntity.Type type = JobEntity.Type.getByKey(
			jobJSONObject.optString("type"));

		if (type == null) {
			throw new Exception(
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