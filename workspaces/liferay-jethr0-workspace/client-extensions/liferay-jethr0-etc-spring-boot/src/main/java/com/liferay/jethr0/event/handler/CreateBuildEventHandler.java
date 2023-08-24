/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.parameter.BuildParameter;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildParameterEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.repository.ProjectEntityRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CreateBuildEventHandler extends BaseObjectEventHandler {

	@Override
	public String process() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		Project project = getProject(
			messageJSONObject.optJSONObject("project"));

		BuildEntityRepository buildEntityRepository = getBuildRepository();

		JSONObject buildJSONObject = validateBuildJSONObject(
			messageJSONObject.optJSONObject("build"));

		Build build = buildEntityRepository.add(project, buildJSONObject);

		JSONObject parametersJSONObject = buildJSONObject.optJSONObject(
			"parameters");

		if ((parametersJSONObject != null) && !parametersJSONObject.isEmpty()) {
			BuildParameterEntityRepository buildParameterEntityRepository =
				getBuildParameterRepository();

			for (String key : parametersJSONObject.keySet()) {
				BuildParameter buildParameter =
					buildParameterEntityRepository.add(
						build, key, parametersJSONObject.getString(key));

				build.addBuildParameter(buildParameter);

				buildParameter.setBuild(build);
			}
		}

		if (project.getState() == Project.State.COMPLETED) {
			project.setState(Project.State.QUEUED);

			ProjectEntityRepository projectEntityRepository =
				getProjectRepository();

			projectEntityRepository.update(project);
		}

		BuildQueue buildQueue = getBuildQueue();

		buildQueue.addProject(project);

		JenkinsQueue jenkinsQueue = getJenkinsQueue();

		jenkinsQueue.invoke();

		return project.toString();
	}

	protected CreateBuildEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

}