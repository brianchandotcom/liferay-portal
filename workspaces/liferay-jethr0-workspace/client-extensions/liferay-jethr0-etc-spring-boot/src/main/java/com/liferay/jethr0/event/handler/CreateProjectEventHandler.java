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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CreateProjectEventHandler extends BaseObjectEventHandler {

	@Override
	public String process() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject projectJSONObject = validateProjectJSONObject(
			messageJSONObject.optJSONObject("project"));

		EventHandlerContext eventHandlerContext = getEventHandlerContext();

		Project project = _createProject(projectJSONObject);

		JSONArray buildsJSONArray = projectJSONObject.optJSONArray("builds");

		if ((buildsJSONArray != null) && !buildsJSONArray.isEmpty()) {
			EventHandlerHelper eventHandlerHelper = getEventHandlerHelper();

			BuildParameterRepository buildParameterRepository =
				eventHandlerContext.getBuildParameterRepository();
			BuildRepository buildRepository =
				eventHandlerContext.getBuildRepository();

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
		}

		return project.toString();
	}

	protected CreateProjectEventHandler(
		EventHandlerHelper eventHandlerHelper, JSONObject messageJSONObject) {

		super(eventHandlerHelper, messageJSONObject);
	}

	private Project _createProject(JSONObject projectJSONObject) {
		EventHandlerContext eventHandlerContext = getEventHandlerContext();

		ProjectRepository projectRepository =
			eventHandlerContext.getProjectRepository();

		return projectRepository.add(projectJSONObject);
	}

}