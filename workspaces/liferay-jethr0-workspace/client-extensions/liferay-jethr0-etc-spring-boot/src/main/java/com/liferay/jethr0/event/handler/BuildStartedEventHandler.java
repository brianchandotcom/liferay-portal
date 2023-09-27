/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import java.util.Date;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BuildStartedEventHandler extends BaseJenkinsEventHandler {

	@Override
	public String process() throws Exception {
		BuildRunEntity buildRunEntity = getBuildRun();

		buildRunEntity.setJenkinsBuildURL(getJenkinsBuildURL());
		buildRunEntity.setState(BuildRunEntity.State.RUNNING);

		BuildEntity buildEntity = buildRunEntity.getBuildEntity();

		buildEntity.setState(BuildEntity.State.RUNNING);

		JobEntity jobEntity = buildEntity.getJobEntity();

		if (jobEntity.getState() != JobEntity.State.RUNNING) {
			jobEntity.setStartDate(new Date());
			jobEntity.setState(JobEntity.State.RUNNING);

			JobEntityRepository jobEntityRepository = getJobEntityRepository();

			jobEntityRepository.update(jobEntity);

			BuildQueue buildQueue = getBuildQueue();

			buildQueue.sort();
		}

		BuildEntityRepository buildEntityRepository = getBuildRepository();

		buildEntityRepository.update(buildEntity);

		BuildRunEntityRepository buildRunEntityRepository =
			getBuildRunRepository();

		buildRunEntityRepository.update(buildRunEntity);

		return buildRunEntity.toString();
	}

	protected BuildStartedEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject jsonObject) {

		super(eventHandlerContext, jsonObject);
	}

}