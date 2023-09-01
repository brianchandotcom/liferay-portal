/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.task.repository;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.dalo.BuildToTasksEntityRelationshipDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.dalo.JobToTasksEntityRelationshipDALO;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.task.dalo.TaskEntityDALO;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TaskEntityRepository extends BaseEntityRepository<TaskEntity> {

	public Set<TaskEntity> getAll(BuildEntity buildEntity) {
		Set<TaskEntity> buildTaskEntities = new HashSet<>();

		Set<Long> taskIds =
			_buildToTasksEntityRelationshipDALO.getChildEntityIds(buildEntity);

		for (TaskEntity taskEntity : getAll()) {
			if (!taskIds.contains(taskEntity.getId())) {
				continue;
			}

			taskEntity.setBuildEntity(buildEntity);

			buildEntity.addTaskEntity(taskEntity);

			buildTaskEntities.add(taskEntity);
		}

		return buildTaskEntities;
	}

	public Set<TaskEntity> getAll(JobEntity jobEntity) {
		Set<TaskEntity> taskEntities = new HashSet<>();

		Set<Long> taskIds = _jobToTasksEntityRelationshipDALO.getChildEntityIds(
			jobEntity);

		for (TaskEntity taskEntity : getAll()) {
			if (!taskIds.contains(taskEntity.getId())) {
				continue;
			}

			taskEntity.setJobEntity(jobEntity);

			jobEntity.addTaskEntity(taskEntity);

			taskEntities.add(taskEntity);
		}

		return taskEntities;
	}

	@Override
	public TaskEntityDALO getEntityDALO() {
		return _taskEntityDALO;
	}

	@Autowired
	private BuildToTasksEntityRelationshipDALO
		_buildToTasksEntityRelationshipDALO;

	@Autowired
	private JobToTasksEntityRelationshipDALO _jobToTasksEntityRelationshipDALO;

	@Autowired
	private TaskEntityDALO _taskEntityDALO;

}