/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine.scheduler;

import com.liferay.jethr0.routine.RoutineEntity;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Michael Hashimoto
 */
public class CronRoutineEntityJob extends BaseRoutineEntityJob {

	@Override
	public void execute(JobExecutionContext jobExecutionContext)
		throws JobExecutionException {

		JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

		Object routineEntityObject = jobDataMap.get("routineEntity");

		if (!(routineEntityObject instanceof RoutineEntity)) {
			return;
		}

		invokeJobEntity((RoutineEntity)routineEntityObject);
	}

}