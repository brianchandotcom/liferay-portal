/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jenkins;

import com.liferay.jethr0.bui1d.BuildEntity;
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
public class BuildCompletedEventHandler extends BaseJenkinsEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		BuildRunEntity buildRunEntity = getBuildRun();

		buildRunEntity.setDuration(getBuildDuration());
		buildRunEntity.setResult(getBuildRunResult());
		buildRunEntity.setState(BuildRunEntity.State.COMPLETED);

		BuildEntity buildEntity = buildRunEntity.getBuildEntity();

		buildEntity.setState(BuildEntity.State.COMPLETED);

		JobEntity jobEntity = buildEntity.getJobEntity();

		JobEntity.State jobState = JobEntity.State.COMPLETED;

		for (BuildEntity jobBuildEntity : jobEntity.getBuildEntities()) {
			BuildEntity.State buildState = jobBuildEntity.getState();

			if (buildState != BuildEntity.State.COMPLETED) {
				jobState = JobEntity.State.RUNNING;

				break;
			}
		}

		if (jobState == JobEntity.State.COMPLETED) {
			jobEntity.setState(jobState);

			JobEntityRepository jobEntityRepository =
				Jethr0ContextUtil.getJobEntityRepository();

			jobEntityRepository.update(jobEntity);
		}

		BuildEntityRepository buildEntityRepository =
			Jethr0ContextUtil.getBuildEntityRepository();

		buildEntityRepository.update(buildEntity);

		BuildRunEntityRepository buildRunEntityRepository =
			Jethr0ContextUtil.getBuildRunEntityRepository();

		buildRunEntityRepository.update(buildRunEntity);

		updateJRPStatus(buildRunEntity, buildEntity, jobEntity, "completed");

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Jenkins build ", buildRunEntity.getJenkinsBuildURL(),
					" completed at ", StringUtil.toString(new Date())));
		}

		return buildRunEntity.toString();
	}

	protected BuildCompletedEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private static final Log _log = LogFactory.getLog(
		BuildCompletedEventHandler.class);

}