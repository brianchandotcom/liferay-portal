/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jrp;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CreateBuildRunEventHandler extends BaseJRPEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		BuildEntityRepository buildEntityRepository =
			Jethr0ContextUtil.getBuildEntityRepository();

		JSONObject buildJSONObject = getBuildJSONObject();

		BuildEntity buildEntity = buildEntityRepository.getById(
			buildJSONObject.getLong("id"));

		JobEntity jobEntity = buildEntity.getJobEntity();

		BuildRunEntityRepository buildRunEntityRepository =
			Jethr0ContextUtil.getBuildRunEntityRepository();

		BuildRunEntity buildRunEntity = buildRunEntityRepository.create(
			buildEntity, BuildRunEntity.State.OPENED);

		if (buildEntity.getState() == BuildEntity.State.COMPLETED) {
			buildEntity.setState(BuildEntity.State.OPENED);

			buildEntityRepository.update(buildEntity);
		}

		if (jobEntity.getState() == JobEntity.State.COMPLETED) {
			jobEntity.setState(JobEntity.State.QUEUED);

			JobEntityRepository jobEntityRepository =
				Jethr0ContextUtil.getJobEntityRepository();

			jobEntityRepository.update(jobEntity);
		}

		BuildQueue buildQueue = Jethr0ContextUtil.getBuildQueue();

		buildQueue.addBuildEntity(buildEntity);

		JenkinsQueue jenkinsQueue = Jethr0ContextUtil.getJenkinsQueue();

		jenkinsQueue.invoke();

		updateJRPStatus(buildRunEntity, buildEntity, jobEntity, "queued");

		return buildRunEntity.toString();
	}

	protected CreateBuildRunEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

}