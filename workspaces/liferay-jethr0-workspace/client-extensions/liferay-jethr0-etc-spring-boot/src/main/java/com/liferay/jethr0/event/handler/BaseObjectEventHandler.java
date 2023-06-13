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

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.repository.ProjectRepository;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseObjectEventHandler extends BaseEventHandler {

	protected BaseObjectEventHandler(
		EventHandlerHelper eventHandlerHelper, JSONObject messageJSONObject) {

		super(eventHandlerHelper, messageJSONObject);
	}

	protected Project getProject(JSONObject projectJSONObject)
		throws Exception {

		if (projectJSONObject == null) {
			throw new Exception("Invalid Project JSON object");
		}

		if (!projectJSONObject.has("id")) {
			throw new Exception("Missing Project 'id'");
		}

		ProjectRepository projectRepository = getProjectRepository();

		Project project = projectRepository.getById(
			projectJSONObject.getLong("id"));

		BuildRepository buildRepository = getBuildRepository();

		buildRepository.getAll(project);

		return project;
	}

	protected JSONObject validateBuildJSONObject(JSONObject buildJSONObject)
		throws Exception {

		if (buildJSONObject == null) {
			throw new Exception("Invalid Build JSON object");
		}

		String buildName = buildJSONObject.optString("buildName");

		if (buildName.isEmpty()) {
			throw new Exception("Invalid Build 'buildName'");
		}

		String jobName = buildJSONObject.optString("jobName");

		if (jobName.isEmpty()) {
			throw new Exception("Invalid Build 'jobName'");
		}

		Build.State state = Build.State.getByKey(
			buildJSONObject.optString("state"));

		if (state == null) {
			state = Build.State.OPENED;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"buildName", buildName
		).put(
			"jobName", jobName
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

	protected JSONObject validateProjectJSONObject(JSONObject projectJSONObject)
		throws Exception {

		if (projectJSONObject == null) {
			throw new Exception("Invalid Project JSON object");
		}

		if (projectJSONObject.has("id")) {
			return projectJSONObject;
		}

		String name = projectJSONObject.optString("name");

		if (name.isEmpty()) {
			throw new Exception("Invalid Project 'name'");
		}

		int priority = projectJSONObject.optInt("priority");

		if (priority <= 0) {
			throw new Exception("Invalid Project 'priority'");
		}

		Project.State state = Project.State.getByKey(
			projectJSONObject.optString("state"));

		if (state == null) {
			state = Project.State.OPENED;
		}

		Project.Type type = Project.Type.getByKey(
			projectJSONObject.optString("type"));

		if (type == null) {
			throw new Exception(
				"Project 'type' key does not match: " + Project.Type.getKeys());
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"builds",
			validateBuildsJSONArray(projectJSONObject.optJSONArray("builds"))
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