/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.parameter.BuildParameterEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildParameterEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CreateBuildEventHandler extends BaseObjectEventHandler {

	@Override
	public String process() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JobEntity jobEntity = getJobEntity(
			messageJSONObject.optJSONObject("job"));

		BuildEntityRepository buildEntityRepository = getBuildRepository();

		JSONObject buildJSONObject = validateBuildJSONObject(
			messageJSONObject.optJSONObject("build"));

		BuildEntity buildEntity = buildEntityRepository.create(
			jobEntity, buildJSONObject);

		JSONObject parametersJSONObject = buildJSONObject.optJSONObject(
			"parameters");

		if ((parametersJSONObject != null) && !parametersJSONObject.isEmpty()) {
			BuildParameterEntityRepository buildParameterEntityRepository =
				getBuildParameterRepository();

			for (String key : parametersJSONObject.keySet()) {
				BuildParameterEntity buildParameterEntity =
					buildParameterEntityRepository.create(
						buildEntity, key, parametersJSONObject.getString(key));

				buildEntity.addBuildParameterEntity(buildParameterEntity);

				buildParameterEntity.setBuildEntity(buildEntity);
			}
		}

		if (jobEntity.getState() == JobEntity.State.COMPLETED) {
			jobEntity.setState(JobEntity.State.QUEUED);

			JobEntityRepository jobEntityRepository = getJobEntityRepository();

			jobEntityRepository.update(jobEntity);
		}

		BuildQueue buildQueue = getBuildQueue();

		buildQueue.addJobEntity(jobEntity);

		JenkinsQueue jenkinsQueue = getJenkinsQueue();

		jenkinsQueue.invoke();

		return jobEntity.toString();
	}

	protected CreateBuildEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

}