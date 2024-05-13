/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.liferay;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.commit.GitCommitEntity;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.UpstreamBranchRoutineEntity;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.JobUtil;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AddJobLiferayEventHandler extends BaseJobLiferayEventHandler {

	@Override
	public String process() {
		if (_log.isInfoEnabled()) {
			_log.info("Adding job at " + StringUtil.toString(new Date()));
		}

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();
		BuildEntityRepository buildEntityRepository =
			Jethr0ContextUtil.getBuildEntityRepository();

		JobEntity jobEntity = jobEntityRepository.add(getJobJSONObject());

		boolean jobEntityUpdated = false;

		RoutineEntity routineEntity = jobEntity.getRoutineEntity();

		if (routineEntity instanceof UpstreamBranchRoutineEntity) {
			UpstreamBranchRoutineEntity upstreamBranchRoutineEntity =
				(UpstreamBranchRoutineEntity)routineEntity;

			GitBranchEntity gitBranchEntity =
				upstreamBranchRoutineEntity.getGitBranchEntity();

			GitCommitEntity latestGitCommitEntity =
				gitBranchEntity.getLatestGitCommitEntity();

			if (latestGitCommitEntity != null) {
				jobEntity.setGitCommitEntity(latestGitCommitEntity);

				jobEntityUpdated = true;

				GitCommitEntity previousGitCommitEntity =
					upstreamBranchRoutineEntity.getPreviousGitCommitEntity();

				if (previousGitCommitEntity == null) {
					RoutineEntityRepository routineEntityRepository =
						Jethr0ContextUtil.getRoutineEntityRepository();

					upstreamBranchRoutineEntity.setPreviousGitCommitEntity(
						latestGitCommitEntity);

					routineEntityRepository.update(upstreamBranchRoutineEntity);
				}
			}
		}

		String currentJobName = jobEntity.getName();

		String updatedJobName = JobUtil.getUpdateJobEntityName(currentJobName);

		if (!currentJobName.equals(updatedJobName)) {
			jobEntity.setName(updatedJobName);

			jobEntityUpdated = true;
		}

		if (jobEntityUpdated) {
			jobEntityRepository.update(jobEntity);
		}

		for (JSONObject initialBuildJSONObject :
				jobEntity.getInitialBuildJSONObjects()) {

			buildEntityRepository.create(jobEntity, initialBuildJSONObject);
		}

		if (jobEntity.getState() == JobEntity.State.QUEUED) {
			BuildQueue buildQueue = Jethr0ContextUtil.getBuildQueue();

			buildQueue.addJobEntity(jobEntity);

			JenkinsQueue jenkinsQueue = Jethr0ContextUtil.getJenkinsQueue();

			jenkinsQueue.invoke();
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Added job ", jobEntity.getEntityURL(), " at ",
					StringUtil.toString(new Date())));
		}

		return jobEntity.toString();
	}

	protected AddJobLiferayEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private static final Log _log = LogFactory.getLog(
		AddJobLiferayEventHandler.class);

}