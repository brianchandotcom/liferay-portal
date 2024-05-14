/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jenkins;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BuildStartedEventHandler extends BaseJenkinsEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		BuildRunEntity buildRunEntity = getBuildRun();

		buildRunEntity.setJenkinsBuildURL(getJenkinsBuildURL());
		buildRunEntity.setState(BuildRunEntity.State.RUNNING);

		BuildEntity buildEntity = buildRunEntity.getBuildEntity();

		buildEntity.setState(BuildEntity.State.RUNNING);

		JobEntity jobEntity = buildEntity.getJobEntity();

		if (jobEntity.getState() != JobEntity.State.RUNNING) {
			jobEntity.setStartDate(new Date());
			jobEntity.setState(JobEntity.State.RUNNING);

			JobEntityRepository jobEntityRepository =
				Jethr0ContextUtil.getJobEntityRepository();

			jobEntityRepository.update(jobEntity);

			BuildQueue buildQueue = Jethr0ContextUtil.getBuildQueue();

			buildQueue.sort();
		}

		BuildEntityRepository buildEntityRepository =
			Jethr0ContextUtil.getBuildEntityRepository();

		buildEntityRepository.update(buildEntity);

		BuildRunEntityRepository buildRunEntityRepository =
			Jethr0ContextUtil.getBuildRunEntityRepository();

		buildRunEntityRepository.update(buildRunEntity);

		updateJRPStatus(buildRunEntity, buildEntity, jobEntity, "running");

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Jenkins build ", buildRunEntity.getJenkinsBuildURL(),
					" started at ", StringUtil.toString(new Date())));
		}

		return buildRunEntity.toString();
	}

	protected BuildStartedEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private static final Log _log = LogFactory.getLog(
		BuildStartedEventHandler.class);

}