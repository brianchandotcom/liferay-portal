/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CreateBuildRunEventHandler extends BaseObjectEventHandler {

	@Override
	public String process() throws Exception {
		BuildEntityRepository buildEntityRepository = getBuildRepository();

		JSONObject buildJSONObject = getBuildJSONObject();

		BuildEntity buildEntity = buildEntityRepository.getById(
			buildJSONObject.getLong("id"));

		JobEntity jobEntity = buildEntity.getJobEntity();

		BuildRunEntityRepository buildRunEntityRepository =
			getBuildRunRepository();

		BuildRunEntity buildRunEntity = buildRunEntityRepository.create(
			buildEntity, BuildRunEntity.State.OPENED);

		if (buildEntity.getState() == BuildEntity.State.COMPLETED) {
			buildEntity.setState(BuildEntity.State.OPENED);

			buildEntityRepository.update(buildEntity);
		}

		if (jobEntity.getState() == JobEntity.State.COMPLETED) {
			jobEntity.setState(JobEntity.State.QUEUED);

			JobEntityRepository jobEntityRepository = getJobEntityRepository();

			jobEntityRepository.update(jobEntity);
		}

		BuildQueue buildQueue = getBuildQueue();

		buildQueue.addBuildEntity(buildEntity);

		JenkinsQueue jenkinsQueue = getJenkinsQueue();

		jenkinsQueue.invoke();

		updateJRPStatus(buildRunEntity, buildEntity, jobEntity, "queued");

		return buildRunEntity.toString();
	}

	protected CreateBuildRunEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

}