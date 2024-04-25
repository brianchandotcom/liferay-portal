/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine.scheduler;

import com.liferay.jethr0.git.commit.GitCommitEntity;
import com.liferay.jethr0.routine.UpstreamBranchCronRoutineEntity;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;

import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Michael Hashimoto
 */
public class UpstreamGitBranchCronRoutineEntityJob
	extends BaseRoutineEntityJob {

	@Override
	public void execute(JobExecutionContext jobExecutionContext)
		throws JobExecutionException {

		JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

		Object routineEntityObject = jobDataMap.get("routineEntity");

		if (!(routineEntityObject instanceof UpstreamBranchCronRoutineEntity)) {
			return;
		}

		UpstreamBranchCronRoutineEntity upstreamBranchCronRoutineEntity =
			(UpstreamBranchCronRoutineEntity)routineEntityObject;

		String currentBranchSHA = null;

		String previousBranchSHA =
			upstreamBranchCronRoutineEntity.getPreviousBranchSHA();

		GitCommitEntity previousGitCommitEntity =
			upstreamBranchCronRoutineEntity.getPreviousGitCommitEntity();

		if (previousGitCommitEntity != null) {
			if (Objects.equals(
					previousBranchSHA, previousGitCommitEntity.getSHA())) {

				return;
			}
		}

		upstreamBranchCronRoutineEntity.setPreviousBranchSHA(currentBranchSHA);

		RoutineEntityJobFactory routineEntityJobFactory =
			getRoutineEntityJobFactory();

		RoutineEntityRepository routineEntityRepository =
			routineEntityJobFactory.getRoutineEntityRepository();

		routineEntityRepository.update(upstreamBranchCronRoutineEntity);

		invokeJobEntity(upstreamBranchCronRoutineEntity);
	}

	private static final Log _log = LogFactory.getLog(
		UpstreamGitBranchCronRoutineEntityJob.class);

}