/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.JobEntityFactory;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.RoutineEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class RoutineToJobsEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<RoutineEntity, JobEntity> {

	@Override
	public EntityFactory<JobEntity> getChildEntityFactory() {
		return _jobEntityFactory;
	}

	@Override
	public EntityFactory<RoutineEntity> getParentEntityFactory() {
		return _routineEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "routineToJobs";
	}

	@Autowired
	private JobEntityFactory _jobEntityFactory;

	@Autowired
	private RoutineEntityFactory _routineEntityFactory;

}