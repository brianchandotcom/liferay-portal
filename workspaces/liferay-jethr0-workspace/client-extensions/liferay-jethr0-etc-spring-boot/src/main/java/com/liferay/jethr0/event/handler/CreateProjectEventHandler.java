/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.repository.BuildParameterRepository;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.repository.ProjectRepository;
import com.liferay.jethr0.util.StringUtil;

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

		Project project = _createProject(projectJSONObject);

		JSONArray buildsJSONArray = projectJSONObject.optJSONArray("builds");

		if ((buildsJSONArray != null) && !buildsJSONArray.isEmpty()) {
			BuildParameterRepository buildParameterRepository =
				getBuildParameterRepository();
			BuildRepository buildRepository = getBuildRepository();

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

		JSONArray jenkinsCohortsJSONArray = projectJSONObject.optJSONArray(
			"jenkinsCohorts");

		if ((jenkinsCohortsJSONArray != null) &&
			!jenkinsCohortsJSONArray.isEmpty()) {

			JenkinsCohortRepository jenkinsCohortRepository =
				getJenkinsCohortRepository();

			for (int i = 0; i < jenkinsCohortsJSONArray.length(); i++) {
				JSONObject jenkinsCohortJSONObject =
					jenkinsCohortsJSONArray.getJSONObject(i);

				long jenkinsCohortId = jenkinsCohortJSONObject.optLong("id");

				if (jenkinsCohortId != 0) {
					project.addJenkinsCohort(
						jenkinsCohortRepository.getById(jenkinsCohortId));

					continue;
				}

				String jenkinsCohortName = jenkinsCohortJSONObject.optString(
					"name");

				if (StringUtil.isNullOrEmpty(jenkinsCohortName)) {
					continue;
				}

				project.addJenkinsCohort(
					jenkinsCohortRepository.getByName(jenkinsCohortName));
			}
		}

		return project.toString();
	}

	protected CreateProjectEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private Project _createProject(JSONObject projectJSONObject) {
		ProjectRepository projectRepository = getProjectRepository();

		return projectRepository.add(projectJSONObject);
	}

}