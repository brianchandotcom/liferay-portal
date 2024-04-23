/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine.scheduler;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.util.JobUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseRoutineEntityJob implements RoutineEntityJob {

	@Override
	public void execute(JobExecutionContext jobExecutionContext)
		throws JobExecutionException {

		JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

		Object routineEntityObject = jobDataMap.get("routineEntity");

		if (!(routineEntityObject instanceof RoutineEntity)) {
			return;
		}

		RoutineEntity routineEntity = (RoutineEntity)routineEntityObject;

		BuildEntityRepository buildEntityRepository =
			_routineEntityJobFactory.getBuildEntityRepository();

		JobEntityRepository jobEntityRepository =
			_routineEntityJobFactory.getJobEntityRepository();

		JobEntity jobEntity = jobEntityRepository.create(
			routineEntity,
			JobUtil.getUpdateJobEntityName(routineEntity.getJobName()),
			routineEntity.getJobParameters(), routineEntity.getJobPriority(),
			null, JobEntity.State.QUEUED, routineEntity.getJobType());

		try {
			for (JSONObject initialBuildJSONObject :
					jobEntity.getInitialBuildJSONObjects()) {

				BuildEntity buildEntity = buildEntityRepository.create(
					jobEntity, initialBuildJSONObject);

				buildEntity.setJobEntity(jobEntity);

				jobEntity.addBuildEntity(buildEntity);
			}

			if (jobEntity.getState() == JobEntity.State.QUEUED) {
				BuildQueue buildQueue =
					_routineEntityJobFactory.getBuildQueue();

				buildQueue.addJobEntity(jobEntity);

				JenkinsQueue jenkinsQueue =
					_routineEntityJobFactory.getJenkinsQueue();

				jenkinsQueue.invoke();
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	@Override
	public RoutineEntityJobFactory getRoutineEntityJobFactory() {
		return _routineEntityJobFactory;
	}

	@Override
	public void setRoutineEntityJobFactory(
		RoutineEntityJobFactory routineEntityJobFactory) {

		_routineEntityJobFactory = routineEntityJobFactory;
	}

	private static final Log _log = LogFactory.getLog(
		BaseRoutineEntityJob.class);

	private RoutineEntityJobFactory _routineEntityJobFactory;

}