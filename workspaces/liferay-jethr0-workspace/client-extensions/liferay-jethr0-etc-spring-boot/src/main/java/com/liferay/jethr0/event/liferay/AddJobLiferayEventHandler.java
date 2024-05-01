/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.liferay;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.commit.GitCommitEntity;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.UpstreamBranchCronRoutineEntity;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;
import com.liferay.jethr0.util.JobUtil;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AddJobLiferayEventHandler extends BaseJobLiferayEventHandler {

	@Override
	public String process() {
		JobEntityRepository jobEntityRepository = getJobEntityRepository();
		BuildEntityRepository buildEntityRepository = getBuildRepository();

		JobEntity jobEntity = jobEntityRepository.add(getJobJSONObject());

		boolean jobEntityUpdated = false;

		RoutineEntity routineEntity = jobEntity.getRoutineEntity();

		if (routineEntity != null) {
			UpstreamBranchCronRoutineEntity upstreamBranchCronRoutineEntity =
				(UpstreamBranchCronRoutineEntity)routineEntity;

			GitBranchEntity gitBranchEntity =
				upstreamBranchCronRoutineEntity.getGitBranchEntity();

			GitCommitEntity latestGitCommitEntity =
				gitBranchEntity.getLatestGitCommitEntity();

			if (latestGitCommitEntity != null) {
				jobEntity.setGitCommitEntity(latestGitCommitEntity);

				jobEntityUpdated = true;

				GitCommitEntity previousGitCommitEntity =
					upstreamBranchCronRoutineEntity.
						getPreviousGitCommitEntity();

				if (previousGitCommitEntity == null) {
					RoutineEntityRepository routineEntityRepository =
						getRoutineEntityRepository();

					upstreamBranchCronRoutineEntity.setPreviousGitCommitEntity(
						latestGitCommitEntity);

					routineEntityRepository.update(
						upstreamBranchCronRoutineEntity);
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
			BuildQueue buildQueue = getBuildQueue();

			buildQueue.addJobEntity(jobEntity);

			JenkinsQueue jenkinsQueue = getJenkinsQueue();

			jenkinsQueue.invoke();
		}

		return jobEntity.toString();
	}

	protected AddJobLiferayEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject jsonObject) {

		super(eventHandlerContext, jsonObject);
	}

}