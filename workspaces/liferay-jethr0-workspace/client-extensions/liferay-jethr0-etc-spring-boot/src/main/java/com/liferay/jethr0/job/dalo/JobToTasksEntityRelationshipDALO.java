/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.JobEntityFactory;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.task.TaskEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JobToTasksEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<JobEntity, TaskEntity> {

	@Override
	public EntityFactory<TaskEntity> getChildEntityFactory() {
		return _taskEntityFactory;
	}

	@Override
	public EntityFactory<JobEntity> getParentEntityFactory() {
		return _jobEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "jobToTasks";
	}

	@Autowired
	private JobEntityFactory _jobEntityFactory;

	@Autowired
	private TaskEntityFactory _taskEntityFactory;

}