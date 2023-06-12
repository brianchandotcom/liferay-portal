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
import com.liferay.jethr0.build.repository.BuildParameterRepository;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CreateProjectEventHandler extends BaseEventHandler {

	@Override
	public String process() throws Exception {
		EventHandlerHelper eventHandlerHelper = getEventHandlerHelper();

		BuildParameterRepository buildParameterRepository =
			eventHandlerHelper.getBuildParameterRepository();
		BuildRepository buildRepository =
			eventHandlerHelper.getBuildRepository();
		ProjectRepository projectRepository =
			eventHandlerHelper.getProjectRepository();

		JSONObject projectJSONObject = _getProjectJSONObject();

		Project project = projectRepository.add(projectJSONObject);

		JSONArray buildsJSONArray = projectJSONObject.getJSONArray("builds");

		for (int i = 0; i < buildsJSONArray.length(); i++) {
			JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

			Build build = buildRepository.add(project, buildJSONObject);

			JSONObject parametersJSONObject = buildJSONObject.optJSONObject(
				"parameters");

			if (parametersJSONObject != null) {
				for (String key : parametersJSONObject.keySet()) {
					buildParameterRepository.add(
						build, key, parametersJSONObject.getString(key));
				}
			}
		}

		return project.toString();
	}

	protected CreateProjectEventHandler(
		EventHandlerHelper eventHandlerHelper, JSONObject jsonObject) {

		super(eventHandlerHelper, jsonObject);
	}

	private JSONObject _getProjectJSONObject() throws Exception {
		JSONObject jsonObject = getJSONObject();

		JSONObject projectJSONObject = jsonObject.optJSONObject("project");

		if (projectJSONObject == null) {
			throw new Exception("Missing 'project' JSON object");
		}

		String name = projectJSONObject.optString("name");

		if (name.isEmpty()) {
			throw new Exception("Invalid project 'name'");
		}

		int priority = projectJSONObject.optInt("priority");

		if (priority <= 0) {
			throw new Exception("Invalid project 'priority'");
		}

		Project.State projectState = Project.State.getByKey(
			projectJSONObject.optString("state"));

		if (projectState == null) {
			projectState = Project.State.OPENED;
		}

		projectJSONObject.remove("state");

		Project.Type type = Project.Type.getByKey(
			projectJSONObject.optString("type"));

		if (type == null) {
			throw new Exception(
				"Project 'type' key does not match: " + Project.Type.getKeys());
		}

		projectJSONObject.remove("type");

		projectJSONObject.put(
			"name", name
		).put(
			"priority", priority
		).put(
			"state", projectState.getJSONObject()
		).put(
			"type", type.getJSONObject()
		);

		JSONArray buildJSONArray = projectJSONObject.optJSONArray("builds");

		if (buildJSONArray == null) {
			return projectJSONObject;
		}

		List<JSONObject> buildsJSONObjects = new ArrayList<>();

		for (int i = 0; i < buildJSONArray.length(); i++) {
			JSONObject buildJSONObject = buildJSONArray.getJSONObject(i);

			if (buildJSONObject == null) {
				continue;
			}

			String buildName = buildJSONObject.optString("buildName");

			if (buildName.isEmpty()) {
				throw new Exception("Invalid build 'buildName'");
			}

			String jobName = buildJSONObject.optString("jobName");

			if (jobName.isEmpty()) {
				throw new Exception("Invalid build 'jobName'");
			}

			Build.State buildState = Build.State.getByKey(
				buildJSONObject.optString("state"));

			if (buildState == null) {
				buildState = Build.State.OPENED;
			}

			buildJSONObject.put(
				"buildName", buildName
			).put(
				"jobName", jobName
			).put(
				"parameters", buildJSONObject.optJSONObject("parameters")
			).put(
				"state", buildState.getJSONObject()
			);

			buildsJSONObjects.add(buildJSONObject);
		}

		projectJSONObject.put("builds", buildsJSONObjects);

		return projectJSONObject;
	}

}