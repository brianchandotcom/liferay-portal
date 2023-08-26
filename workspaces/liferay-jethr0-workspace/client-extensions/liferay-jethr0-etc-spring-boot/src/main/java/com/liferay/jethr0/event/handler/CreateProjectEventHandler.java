/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildParameterEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.project.ProjectEntity;
import com.liferay.jethr0.project.repository.ProjectEntityRepository;
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

		ProjectEntity projectEntity = _createProjectEntity(projectJSONObject);

		JSONArray buildsJSONArray = projectJSONObject.optJSONArray("builds");

		if ((buildsJSONArray != null) && !buildsJSONArray.isEmpty()) {
			BuildParameterEntityRepository buildParameterEntityRepository =
				getBuildParameterRepository();
			BuildEntityRepository buildEntityRepository = getBuildRepository();

			for (int i = 0; i < buildsJSONArray.length(); i++) {
				JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

				BuildEntity buildEntity = buildEntityRepository.add(
					projectEntity, buildJSONObject);

				JSONObject parametersJSONObject = buildJSONObject.optJSONObject(
					"parameters");

				if (parametersJSONObject != null) {
					for (String key : parametersJSONObject.keySet()) {
						buildParameterEntityRepository.add(
							buildEntity, key,
							parametersJSONObject.getString(key));
					}
				}
			}
		}

		JSONArray jenkinsCohortsJSONArray = projectJSONObject.optJSONArray(
			"jenkinsCohorts");

		if ((jenkinsCohortsJSONArray != null) &&
			!jenkinsCohortsJSONArray.isEmpty()) {

			JenkinsCohortEntityRepository jenkinsCohortEntityRepository =
				getJenkinsCohortEntityRepository();

			for (int i = 0; i < jenkinsCohortsJSONArray.length(); i++) {
				JSONObject jenkinsCohortJSONObject =
					jenkinsCohortsJSONArray.getJSONObject(i);

				long jenkinsCohortId = jenkinsCohortJSONObject.optLong("id");

				if (jenkinsCohortId != 0) {
					projectEntity.addJenkinsCohortEntity(
						jenkinsCohortEntityRepository.getById(jenkinsCohortId));

					continue;
				}

				String jenkinsCohortName = jenkinsCohortJSONObject.optString(
					"name");

				if (StringUtil.isNullOrEmpty(jenkinsCohortName)) {
					continue;
				}

				projectEntity.addJenkinsCohortEntity(
					jenkinsCohortEntityRepository.getByName(jenkinsCohortName));
			}
		}

		return projectEntity.toString();
	}

	protected CreateProjectEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private ProjectEntity _createProjectEntity(JSONObject projectJSONObject) {
		ProjectEntityRepository projectEntityRepository =
			getProjectEntityRepository();

		return projectEntityRepository.add(projectJSONObject);
	}

}