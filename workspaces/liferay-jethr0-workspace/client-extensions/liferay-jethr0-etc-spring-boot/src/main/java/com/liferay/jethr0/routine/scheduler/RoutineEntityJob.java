/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Michael Hashimoto
 */
public class RoutineEntityJob implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext)
		throws JobExecutionException {

		JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

		BuildEntityRepository buildEntityRepository = _getBuildEntityRepository(
			jobDataMap);
		BuildQueue buildQueue = _getBuildQueue(jobDataMap);
		JenkinsQueue jenkinsQueue = _getJenkinsQueue(jobDataMap);
		JobEntityRepository jobEntityRepository = _getJobEntityRepository(
			jobDataMap);
		RoutineEntity routineEntity = _getRoutineEntity(jobDataMap);

		if ((buildEntityRepository == null) || (buildQueue == null) ||
			(jenkinsQueue == null) || (jobEntityRepository == null) ||
			(routineEntity == null)) {

			return;
		}

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
				buildQueue.addJobEntity(jobEntity);

				jenkinsQueue.invoke();
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	private BuildEntityRepository _getBuildEntityRepository(
		JobDataMap jobDataMap) {

		Object object = jobDataMap.get("buildEntityRepository");

		if (!(object instanceof BuildEntityRepository)) {
			return null;
		}

		return (BuildEntityRepository)object;
	}

	private BuildQueue _getBuildQueue(JobDataMap jobDataMap) {
		Object object = jobDataMap.get("buildQueue");

		if (!(object instanceof BuildQueue)) {
			return null;
		}

		return (BuildQueue)object;
	}

	private JenkinsQueue _getJenkinsQueue(JobDataMap jobDataMap) {
		Object object = jobDataMap.get("jenkinsQueue");

		if (!(object instanceof JenkinsQueue)) {
			return null;
		}

		return (JenkinsQueue)object;
	}

	private JobEntityRepository _getJobEntityRepository(JobDataMap jobDataMap) {
		Object object = jobDataMap.get("jobEntityRepository");

		if (!(object instanceof JobEntityRepository)) {
			return null;
		}

		return (JobEntityRepository)object;
	}

	private RoutineEntity _getRoutineEntity(JobDataMap jobDataMap) {
		Object object = jobDataMap.get("routineEntity");

		if (!(object instanceof RoutineEntity)) {
			return null;
		}

		return (RoutineEntity)object;
	}

	private static final Log _log = LogFactory.getLog(RoutineEntityJob.class);

}